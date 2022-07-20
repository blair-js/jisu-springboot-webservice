package com.jisu.book.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//@EnableJpaAuditing
//@SpringBootApplication : 스프링 부트의 자동설정, 스프링 Bean 읽기와 생성을 모두 자동으로 설정
@SpringBootApplication
public class Application {
    //해당 프로젝트의 메인 클래스
    public static void main(String[] args){
        //SpringApplication.run : 내장 WAS 실행
        SpringApplication.run(Application.class, args);
    }
}
