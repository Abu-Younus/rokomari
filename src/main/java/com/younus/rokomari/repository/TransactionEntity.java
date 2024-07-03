package com.younus.rokomari.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionEntity extends JpaRepository<TransactionEntity,Long> {
}
