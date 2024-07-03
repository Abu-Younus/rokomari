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
@Table(name = "transactions")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private String paymentMode;
    private String paymentStatus;
    private Date transactionDate;
    private Date approvedDate;
    private Date declinedDate;
    private Date refundDate;
    private Date createdAt;
    private Date updatedAt;
}
