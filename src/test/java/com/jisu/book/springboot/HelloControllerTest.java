package com.jisu.book.springboot;

import com.jisu.book.springboot.web.HelloController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


//@RunWith : 테스트 진행시 JUnit 이라는 내장된 실행자 외에 다른 실행자를 실행시킨다.
//여기서는 SpringRunner 라는 스프링 실행자를 사용한다.
//즉, 스프링 부트 테스트와 JUnit 사이에 연결자 역할
@RunWith(SpringRunner.class)
//@WebMvcTest 어노테이션은 Controller Layer를 테스트할 때 사용된다.
//해당 어노테이션을 사용하면 Spring MVC 컨트롤러를 이용한 요청과 응답에 필요한 Bean들만 로딩하여 가벼운 테스트 환경을 제공한다.
@WebMvcTest(controllers = HelloController.class)
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc; //웹 API를 테스트할 때 사용되는 객체로 스프링 MVC 테스트의 시작점이다.

    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        //mvc 객체를 통해 /hello 주소로 HTTP get 요청을 한다.
        mvc.perform(get("/hello"))
                //mvc.perform의 결과를 검증한다.
                //HTTP Header의 Status를 검증한다. => 200, 404, 500 등
                //여기서는 Ok 즉, 200인지 아닌지를 검증한다.
                .andExpect(status().isOk())
                //mvc.perform의 결과를 검증한다.
                //응답 본문의 내용을 검증한다.
                //본문의 내용이 hello가 맞는지 검증한다.
                .andExpect(content().string(hello));
    }

    @Test
    public void helloDto가_리턴된다() throws Exception{
        String name = "hello";
        int amount = 1000;

        //param : API 테스트할 때 사용될 요청 파라미터를 설정한다.
        //단, 값은 String만 허용되므로 int형의 amount를 String으로 변환한다.
        mvc.perform(get("/hello/dto")
                .param("name", name)
                .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                //jsonPath : JSON 응답값을 필드별로 검증할 수 있는 메소드이다.
                //$를 기준으로 필드명을 명시
                .andExpect(jsonPath("$.name", is(name)))
                        .andExpect(jsonPath("$.amount", is(amount)));

    }


}
