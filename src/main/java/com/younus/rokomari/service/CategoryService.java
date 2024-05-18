package com.younus.rokomari.service;

import com.younus.rokomari.domain.CategoryDto;
import com.younus.rokomari.entity.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

public interface CategoryService {
    String createCategory(CategoryDto categoryDto, BindingResult bindingResult);

    Page<CategoryEntity> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

    String showEditCategory(Model model, Long id);

    String updateCategory(Model model, Long id, CategoryDto categoryDto, BindingResult bindingResult);

    String deleteCategory(Long id);
}
