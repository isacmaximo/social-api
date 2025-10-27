package com.social.api.post.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.social.api.post.entity.Post;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

@Getter
@Setter
public class PostResponseDto {
    private UUID id;

    @JsonProperty("user_id")
    private UUID userId;

    private String author;
    private String message;
    private byte[] image;

    @JsonProperty("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    public static PostResponseDto from(Post post) {
        PostResponseDto dto = new PostResponseDto();
        dto.setId(post.getId());
        dto.setUserId(post.getUserId());
        dto.setAuthor(post.getAuthor());
        dto.setMessage(post.getMessage());
        dto.setImage(post.getImage());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setUpdatedAt(post.getUpdatedAt());
        return dto;
    }
}