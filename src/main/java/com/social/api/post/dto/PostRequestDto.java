package com.social.api.post.dto;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Null;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequestDto {

    @NotNull(message = "User ID é obrigatório")
    @JsonProperty("user_id")
    private UUID userId;

    @NotBlank(message = "Autor é obrigatório")
    @Size(max = 100, message = "Autor deve ter no máximo 100 caracteres")
    private String author;

    @NotBlank(message = "Mensagem é obrigatória")
    @Size(max = 255, message = "Mensagem deve ter no máximo 255 caracteres")
    private String message;

    private byte[] image;



    PostRequestDto (UUID userId, String author, String message, byte[] image, Date createdAt, Date updatedAt) {
        this.userId = userId;
        this.author = author;
        this.message = message;
        this.image = image;
    }
    
    @JsonProperty("created_at")
    @Null(message = "created_at é gerenciado pelo servidor")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    @Null(message = "updated_at é gerenciado pelo servidor")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
}
