package com.younus.rokomari.service;

import com.younus.rokomari.domain.SubCategoryDto;
import com.younus.rokomari.entity.SubCategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

public interface SubCategoryService {
    Page<SubCategoryEntity> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

    String createSubCategory(SubCategoryDto subCategoryDto, BindingResult bindingResult);

    String showEditSubCategory(Model model, Long id);

    String updateSubCategory(Model model, Long id, SubCategoryDto subCategoryDto, BindingResult bindingResult);

    String deleteSubCategory(Long id);
}
