package com.younus.rokomari.domain;

import jakarta.validation.constraints.Email;
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
public class SettingDto {
    @NotEmpty(message = "The name is required!")
    @Size(min = 2, max = 100, message = "The name must be between 2 and 100")
    private String name;

    @NotEmpty(message = "The main email is required!")
    @Email
    private String mainEmail;

    @Email
    private String supportEmail;

    @NotEmpty(message = "The phone is required!")
    @NumberFormat
    private String phone;

    @NumberFormat
    private String anotherPhone;

    @NotEmpty(message = "The domain is required!")
    private String domain;

    @NotEmpty(message = "The address is required!")
    @Size(min = 5, max = 500, message = "The address must be between 5 and 500")
    private String address;

    private String facebook;
    private String twitter;
    private String instagram;
    private String linkedin;
    private String github;
    private String gitlab;
    private String youtube;

    private MultipartFile logo;
}
