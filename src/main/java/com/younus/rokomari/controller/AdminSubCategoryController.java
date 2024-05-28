package com.younus.rokomari.controller;

import com.younus.rokomari.domain.SubCategoryDto;
import com.younus.rokomari.entity.CategoryEntity;
import com.younus.rokomari.entity.SubCategoryEntity;
import com.younus.rokomari.service.CategoryService;
import com.younus.rokomari.service.SubCategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/sub-category")
public class AdminSubCategoryController {
    @Autowired
    private SubCategoryService subCategoryService;

    @Autowired
    private CategoryService categoryService;

    //get all sub category api
    @GetMapping("")
    public String getAllSubCategory(Model model) {
        return findPaginated(1, "id", "desc", model);
    }

    //sub category pagination & sorting api
    @GetMapping("/page/{pageNo}")
    public String findPaginated(
            @PathVariable(value = "pageNo") int pageNo,
            @RequestParam("sortField") String sortField,
            @RequestParam("sortDirection") String sortDirection,
            Model model
    ) {
        int pageSize = 5;
        Page<SubCategoryEntity> page = subCategoryService.findPaginated(pageNo, pageSize, sortField, sortDirection);
        List<SubCategoryEntity> subCategoryEntities = page.getContent();

        model.addAttribute("subCategories", subCategoryEntities);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseSortDir", sortDirection.equals("asc") ? "desc" : "asc");

        return "pages/back-end/sub-category/manage";
    }

    //show create page & create sub category api
    @GetMapping("/add")
    public String showAddSubCategory(Model model) {
        SubCategoryDto subCategoryDto = new SubCategoryDto();
        model.addAttribute("subCategory", subCategoryDto);
        model.addAttribute("categories", categoryService.allCategory());
        return "pages/back-end/sub-category/create";
    }

    @PostMapping("/add")
    public String createSubCategory(@Valid @ModelAttribute("subCategory") SubCategoryDto subCategoryDto, BindingResult bindingResult) {
        return subCategoryService.createSubCategory(subCategoryDto, bindingResult);
    }

    //show edit page & update sub category api
    @GetMapping("/edit")
    public String showEditSubCategory(Model model, @RequestParam("id") Long id) {
        return subCategoryService.showEditSubCategory(model, id);
    }

    @PostMapping("/edit")
    public String updateSubCategory(Model model,
                                 @RequestParam("id") Long id,
                                 @Valid @ModelAttribute("subCategory") SubCategoryDto subCategoryDto,
                                 BindingResult bindingResult) {
        return subCategoryService.updateSubCategory(model, id, subCategoryDto, bindingResult);
    }

    //delete sub category api
    @GetMapping("/delete")
    public String deleteCategory(@RequestParam Long id) {
        return subCategoryService.deleteSubCategory(id);
    }
}
