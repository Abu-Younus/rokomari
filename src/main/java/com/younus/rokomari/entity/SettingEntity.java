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
@Table(name = "settings")
public class SettingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String mainEmail;
    private String supportEmail;
    private String phone;
    private String anotherPhone;
    private String domain;

    @Column(length = 500)
    private String address;

    private String facebook;
    private String twitter;
    private String instagram;
    private String linkedin;
    private String github;
    private String gitlab;
    private String youtube;

    private String logo;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private UserEntity createdBy;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    private UserEntity updatedBy;

    private Date createdAt;
    private Date updatedAt;
}
