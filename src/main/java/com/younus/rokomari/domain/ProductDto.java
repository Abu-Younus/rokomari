package com.younus.rokomari.domain;

import com.younus.rokomari.entity.AuthorEntity;
import com.younus.rokomari.entity.BrandEntity;
import com.younus.rokomari.entity.CategoryEntity;
import com.younus.rokomari.entity.SubCategoryEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    @NotNull(message = "The category is required!")
    private CategoryEntity category;

    @NotNull(message = "The sub category is required!")
    private SubCategoryEntity subCategory;

    private BrandEntity brand;
    private AuthorEntity author;

    @NotEmpty(message = "The title is required!")
    @Size(min = 3, max = 100, message = "The title must be between 3 and 100")
    private String title;

    @NotNull(message = "The regular price is required!")
    private double regularPrice;
    @NotNull(message = "The purchase price is required!")
    private double purchasePrice;

    private double discountedPrice;

    @NotEmpty(message = "The description is required!")
    @Size(min = 5, max = 1000, message = "The description must be between 5 and 1000")
    private String description;

    private String publisher;
    private Long isbn;
    private String edition;
    private int numberOfPages;
    private String country;
    private String language;

    private boolean status;

    private MultipartFile image;

    private MultipartFile[] images;
}
