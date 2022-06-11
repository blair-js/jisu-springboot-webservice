package com.jisu.book.springboot.service.posts;

import com.jisu.book.springboot.domain.posts.Posts;
import com.jisu.book.springboot.domain.posts.PostsRepository;
import com.jisu.book.springboot.web.dto.PostsResponseDto;
import com.jisu.book.springboot.web.dto.PostsSaveRequestDto;
import com.jisu.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){

        //먼저 해당 id로 Posts가 존재하는지 확인 후 없으면 IllegalArgumentException 예외를 발생시킨다.
        Posts posts = postsRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        //있으면 해당 Posts에 데이터를 업데이트 해준다.
        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;

    }

    public PostsResponseDto findById(Long id){
        Posts entity = postsRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        return new PostsResponseDto(entity);
    }
}
