package com.jisu.book.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Posts, Long> {
    //제네릭에 기본 Entity 클래스와 해당 Entity 클래스의 PK 대입
}
