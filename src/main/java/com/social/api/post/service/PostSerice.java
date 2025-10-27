package com.social.api.post.service;

import com.social.api.post.repository.PostRepository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.social.api.post.dto.PostRequestDto;
import com.social.api.post.dto.PostResponseDto;

import org.springframework.stereotype.Service;


import com.social.api.post.entity.Post;

import java.time.LocalDateTime;

@Service
public class PostSerice {
    private final PostRepository postRepository;

    public PostSerice(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Page<PostResponseDto> getPosts(Pageable pageable) {
        return postRepository.findAll(pageable).map(PostResponseDto::from);
    }

    public Page<PostResponseDto> getPostsByUserId(UUID userId, Pageable pageable) {
        return postRepository.findAllByUserId(userId, pageable).map(PostResponseDto::from);
    }

    public PostResponseDto getPostById(UUID id) {
        return postRepository.findById(id).map(PostResponseDto::from).orElse(null);
    }

    public PostResponseDto createPost(PostRequestDto postRequestDto) {
        System.out.println(postRequestDto);
        Post post = new Post();
        LocalDateTime now = LocalDateTime.now();
        post.setUserId(postRequestDto.getUserId());
        post.setAuthor(postRequestDto.getAuthor());
        post.setMessage(postRequestDto.getMessage());
        post.setImage(postRequestDto.getImage());
        post.setCreatedAt(now); 
        post.setUpdatedAt(now);
        Post saved = postRepository.save(post);
        return PostResponseDto.from(saved);
    }


    public PostResponseDto updatePost(UUID id, PostRequestDto postRequestDto) {
        Post post = postRepository.findById(id).orElse(null);
        if (post == null) {
            return null;
        }
        post.setUserId(postRequestDto.getUserId());
        post.setAuthor(postRequestDto.getAuthor());
        post.setMessage(postRequestDto.getMessage());
        post.setImage(postRequestDto.getImage());
        post.setUpdatedAt(LocalDateTime.now());
        Post updated = postRepository.save(post);
        return PostResponseDto.from(updated);
    }

    public boolean deletePost(UUID id) {
        Post post = postRepository.findById(id).orElse(null);
        if (post == null) {
            return false;
        }
        postRepository.delete(post);
        return true;
    }
}
