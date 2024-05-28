package com.younus.rokomari.domain;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDto {
    @NotEmpty(message = "The name is required!")
    @Size(min = 3, max = 100, message = "The details must be between 3 and 100")
    private String name;

    @NotEmpty(message = "The details is required!")
    @Size(min = 5, max = 1000, message = "The details must be between 5 and 1000")
    private String details;

    private MultipartFile image;
}
