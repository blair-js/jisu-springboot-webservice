package com.jisu.book.springboot.config.auth;

import com.jisu.book.springboot.config.auth.dto.OAuthAttributes;
import com.jisu.book.springboot.config.auth.dto.SessionUser;
import com.jisu.book.springboot.domain.user.User;
import com.jisu.book.springboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User>
                delegate = new DefaultOAuth2UserService();

        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        /*현재 로그인 진행 중인 서비스를 구분하는 코드이다.*/
        /*지금은 구글만 사용하는 불필요한 값이지만, 이후 네이버 로그인 연동 시에 네이버 로그인인지 구글 로그인인지 구분하기 위함이다.*/
        String registerationId = userRequest.getClientRegistration().getRegistrationId();

        /*OAuth 2 로그인 진행 시 키가 되는 필드값을 의미한다. pk와 같은 의미이다.*/
        /*구글의 경우 기본적으로 기본코드(Sub)를 지원하지만, 네이버/카카오 등은 기본 지원하지 않는다.*/
        String userNameAttributeName = userRequest.
                                                    getClientRegistration().getProviderDetails()
                                                    .getUserInfoEndpoint().
                                                            getUserNameAttributeName();

        /*OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스이다.*/
        OAuthAttributes attributes = OAuthAttributes.
                                                     of(registerationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);

        /*SessionUser:세션에 사용자 정보를 저장하기 위한 Dto 클래스이다.*/
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(Collections.singleton(
                                                            new SimpleGrantedAuthority(user.getRoleKey())),
                                                            attributes.getAttributes(),
                                                            attributes.getNameAttributeKey()
                                    );
    }

    /*구글 사용자 정보가 업데이트 되었을 때를 대비하여 update 기능을 수행하는 메소드이다.*/
    /*사용자의 이름이나 프로필 사진이 변경되면 User 엔티티에도 반영된다.*/
    private User saveOrUpdate(OAuthAttributes attributes){
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}
