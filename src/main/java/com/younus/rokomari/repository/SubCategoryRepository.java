package com.younus.rokomari.repository;

import com.younus.rokomari.entity.SubCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategoryEntity,Long> {
    List<SubCategoryEntity> findByCategoryId(Long categoryId);
}
