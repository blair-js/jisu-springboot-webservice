package com.jisu.book.springboot.config.auth;

import com.jisu.book.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity /*스프링 시큐리티 설정들을 활성화 시켜준다.*/
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .headers().frameOptions().disable() /*h2-console 화면을 사용하기 위해 해당 옵션들을 disable 한다.*/
                .and()
                    .authorizeRequests() /*URL별 권한 관리를 설정하는 옵션의 시작점이다.*/
                    .antMatchers("/", "/css/**", "/images/**", /*권한 관리 대상을 지정하는 옵션이다.*/
                            "/js/**", "/h2-console/**").permitAll() /*지정 URL들은 전체 열람 권한을 준다.*/
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name()) /*USER 권한을 가진 사람만 가능하도록 권한을 준다.*/
                    .anyRequest().authenticated() /*설정된 값 이외 나머지 URL들은 인증된 사용자(로그인한 사용자)만 허용하게 한다.*/
                .and()
                    .logout()
                        .logoutSuccessUrl("/") /*로그아웃 기능에 대한 여러 설정의 진입점이다.*/
                .and()
                    .oauth2Login() /*OAuth 2 로그인 기능에 대한 여러 설정의 진입점이다.*/
                        .userInfoEndpoint() /*OAuth 2 로그인 성공 후 사용자 정보를 가져올 때의 설정을 담당한다.*/
                            .userService(customOAuth2UserService); /*소셜 로그인 성공시 후속 조치를 진행할 UserService 인터페이스의 구현체를 등록한다.*/
                            /*리소스 서버(소셜 서비스)엥서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능을 명시할 수 있다.*/
    }
}
