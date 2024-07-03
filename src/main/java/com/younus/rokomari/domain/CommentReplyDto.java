package com.younus.rokomari.domain;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentReplyDto {
    @NotEmpty(message = "The reply is required!")
    @Size(min = 5, max = 1000, message = "The reply must be between 5 and 1000")
    private String reply;
}
