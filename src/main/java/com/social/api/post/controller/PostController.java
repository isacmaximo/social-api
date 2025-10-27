package com.social.api.post.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.social.api.post.dto.PostRequestDto;
import com.social.api.post.dto.PostResponseDto;
import com.social.api.post.service.PostSerice;

@RestController
@RequestMapping("posts")
public class PostController {
    final PostSerice postService;
    
    public PostController(PostSerice postService) {
        this.postService = postService;
    }

    @PostMapping
    ResponseEntity<PostResponseDto> createPost(@RequestBody PostRequestDto postRequestDto) {
        return ResponseEntity.ok(postService.createPost(postRequestDto));
    }

    @GetMapping
    ResponseEntity<Page<PostResponseDto>> getPosts(
        @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC)
        Pageable pageable
    ) {
        return ResponseEntity.ok(postService.getPosts(pageable));
    }

    @GetMapping("/user/{userId}")
    ResponseEntity<Page<PostResponseDto>> getPostsByUserId(
        @PathVariable UUID userId,
        @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC)
        Pageable pageable
    ) {
        return ResponseEntity.ok(postService.getPostsByUserId(userId, pageable));
    }

    @GetMapping("/{postId}")
    ResponseEntity<PostResponseDto> getPostById(@PathVariable UUID postId) {
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    @PutMapping("/{postId}")
    ResponseEntity<PostResponseDto> updatePost(@PathVariable UUID postId, @RequestBody PostRequestDto postRequestDto) {
        return ResponseEntity.ok(postService.updatePost(postId, postRequestDto));
    }

    @DeleteMapping("/{postId}")
    ResponseEntity<Void> deletePost(@PathVariable UUID postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }
}
