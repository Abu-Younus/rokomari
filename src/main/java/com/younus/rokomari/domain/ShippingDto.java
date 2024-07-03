package com.younus.rokomari.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShippingDto {
    private String fullName;
    private String email;
    private String phone;
    private String address;

    private String city;
    private String state;
    private String zipcode;
    private String country;
}
