package com.younus.rokomari.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillingDto {
    @NotEmpty(message = "The full name is required!")
    @Size(min = 3, max = 100, message = "The full name must be between 3 and 100")
    private String fullName;

    @NotEmpty(message = "The email is required!")
    @Email
    private String email;

    @NotEmpty(message = "The phone is required!")
    private String phone;

    @NotEmpty(message = "The address is required!")
    @Size(min = 5, max = 500, message = "The address must be between 5 and 500")
    private String address;

    @NotEmpty(message = "The city is required!")
    private String city;

    @NotEmpty(message = "The state is required!")
    private String state;

    @NotEmpty(message = "The zipcode is required!")
    private String zipcode;

    @NotEmpty(message = "The country is required!")
    private String country;
}
