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
public class CategoryDto {

    @NotEmpty(message = "The name is required!")
    @Size(min = 3, max = 100, message = "The name must be between 3 and 100!")
    public String name;

    private MultipartFile image;
}
