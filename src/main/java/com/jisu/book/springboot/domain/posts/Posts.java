package com.jisu.book.springboot.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Posts extends BaseTimeEntity{
    //실제 DB의 테이블과 매칭될 클래스로 Entity 클래스라고 한다.
    //기본 값으로 클래스의 카멜케이스 이름을 언더스코어 네이밍(_)으로 테이블 이름을 매칭한다.
    //ex.SalesManager.java -> sales_manager table

    @Id //해당 테이블의 PK
    //GenerationType.IDENTITY : auto-increament
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@Column은 굳이 선언하지 않아도 해당 클래스의 모든 필드는 @Column으로 인식한다.
    //추가적으로 옵션이 필요한 경우 괄호안에 넣어주면 된다.
    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder
    public Posts(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }
}