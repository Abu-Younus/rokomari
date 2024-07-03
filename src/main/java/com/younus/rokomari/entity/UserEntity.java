package com.younus.rokomari.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    @Column(unique=true)
    private String email;
    private String password;

    @OneToOne(mappedBy = "user")
    private ProfileEntity profile;

    @OneToOne(mappedBy = "user")
    private PasswordResetEntity passwordReset;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdBy", orphanRemoval = true)
    private List<CategoryEntity> categoryCreatedBy = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "updatedBy", orphanRemoval = true)
    private List<CategoryEntity> categoryUpdatedBy = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdBy", orphanRemoval = true)
    private List<SubCategoryEntity> subCategoryCreatedBy = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "updatedBy", orphanRemoval = true)
    private List<SubCategoryEntity> subCategoryUpdatedBy = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdBy", orphanRemoval = true)
    private List<BrandEntity> brandCreatedBy = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "updatedBy", orphanRemoval = true)
    private List<BrandEntity> brandUpdatedBy = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdBy", orphanRemoval = true)
    private List<AuthorEntity> authorCreatedBy = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "updatedBy", orphanRemoval = true)
    private List<AuthorEntity> authorUpdatedBy = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdBy", orphanRemoval = true)
    private List<ProductEntity> productCreatedBy = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "updatedBy", orphanRemoval = true)
    private List<ProductEntity> productUpdatedBy = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private List<ProductReviewEntity> productReviews = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private List<CommentEntity> comments = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private List<CommentReplyEntity> commentReplies = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private List<CartItemEntity> cartItems = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private List<WishlistEntity> wishlists = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private List<OrderEntity> orders = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private List<OrderItemsEntity> orderItems = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private List<TransactionEntity> transactions = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade= CascadeType.ALL)
    @JoinTable(
            name="users_roles",
            joinColumns={@JoinColumn(name="user_id", referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="role_id", referencedColumnName="id")})
    private List<RoleEntity> roles = new ArrayList<>();
}
