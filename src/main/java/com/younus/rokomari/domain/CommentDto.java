package com.younus.rokomari.domain;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    @NotEmpty(message = "The comment is required!")
    @Size(min = 5, max = 1000, message = "The comment must be between 5 and 1000")
    private String comment;
}
