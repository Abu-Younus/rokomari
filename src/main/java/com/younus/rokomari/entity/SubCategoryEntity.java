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
@Table(name = "sub_categories")
public class SubCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    private String name;
    private String image;
    private Date createdAt;
    private Date updatedAt;
}
