package com.younus.rokomari.repository;

import com.younus.rokomari.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,Long> {
    List<ProductEntity> findByCategoryId(Long categoryId);

    List<ProductEntity> findBySubCategoryId(Long subCategoryId);

    List<ProductEntity> findByBrandId(Long brandId);

    List<ProductEntity> findByAuthorId(Long authorId);
}
