package com.jisu.book.springboot.web;

import com.jisu.book.springboot.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//@RestController : 컨트롤러를 JSON으로 반환하는 컨트롤러로 만들어준다.
//각 메소드마다 @ResponseBody를 선언하지 않아도 된다.
@RestController
public class HelloController {

    //GetMapping : HTTP Method인 Get 요청을 받을 수 있는 API
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }


    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(@RequestParam("name") String name, @RequestParam("amount") int amount){
        return new HelloResponseDto(name, amount);
    }
}
