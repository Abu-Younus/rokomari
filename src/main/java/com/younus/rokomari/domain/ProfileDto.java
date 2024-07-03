package com.younus.rokomari.domain;

import com.younus.rokomari.entity.UserEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDto {

    private UserEntity user;

    @NotEmpty(message = "The phone is required!")
    @NumberFormat
    private String phone;

    @NotEmpty(message = "The phone is required!")
    @Size(min = 5, max = 500, message = "The address must be between 5 and 500!")
    private String address;

    @NotEmpty(message = "The city is required!")
    private String city;

    @NotEmpty(message = "The state is required!")
    private String state;

    @NotEmpty(message = "The zipcode is required!")
    @NumberFormat
    private String zipcode;

    @NotEmpty(message = "The country is required!")
    private String country;

    private MultipartFile image;
}
