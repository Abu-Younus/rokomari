package com.younus.rokomari.service;

import com.younus.rokomari.domain.BrandDto;
import com.younus.rokomari.entity.BrandEntity;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

public interface BrandService {
    //create brand method
    String createBrand(BrandDto brandDto, BindingResult bindingResult);

    //pagination & sorting method
    Page<BrandEntity> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

    //show edit brand page method
    String showEditBrand(Model model, Long id);

    //update brand method
    String updateBrand(Model model, Long id, BrandDto brandDto, BindingResult bindingResult);

    //delete brand method
    String deleteBrand(Long id);
}
