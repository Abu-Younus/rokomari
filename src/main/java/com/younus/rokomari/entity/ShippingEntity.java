package com.younus.rokomari.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "shippings")
public class ShippingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    private String fullName;
    private String email;
    private String phone;

    @Column(length = 500)
    private String address;

    private String city;
    private String state;
    private String zipcode;
    private String country;
    private Date createdAt;
    private Date updatedAt;
}
