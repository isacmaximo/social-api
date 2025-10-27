package com.social.api.post.repository;

import com.social.api.post.entity.Post;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {
    Page<Post> findAllByUserId(UUID userId, Pageable pageable);
}
