package com.younus.rokomari.service;

import com.younus.rokomari.domain.SubCategoryDto;
import com.younus.rokomari.entity.SubCategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

public interface SubCategoryService {
    //pagination & sorting method
    Page<SubCategoryEntity> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

    //create sub category method
    String createSubCategory(SubCategoryDto subCategoryDto, BindingResult bindingResult);

    //show edit sub category page method
    String showEditSubCategory(Model model, Long id);

    //update sub category method
    String updateSubCategory(Model model, Long id, SubCategoryDto subCategoryDto, BindingResult bindingResult);

    //delete sub category method
    String deleteSubCategory(Long id);
}
