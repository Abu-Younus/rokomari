package com.younus.rokomari.service;

import com.younus.rokomari.domain.CategoryDto;
import com.younus.rokomari.entity.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface CategoryService {
    //create category method
    String createCategory(CategoryDto categoryDto, BindingResult bindingResult);

    //pagination & sorting method
    Page<CategoryEntity> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

    //all category method to subcategory
    List<CategoryEntity> allCategory();

    //show edit category page method
    String showEditCategory(Model model, Long id);

    //update category method
    String updateCategory(Model model, Long id, CategoryDto categoryDto, BindingResult bindingResult);

    //delete category method
    String deleteCategory(Long id);
}
