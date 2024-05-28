package com.younus.rokomari.controller;

import com.younus.rokomari.domain.CategoryDto;
import com.younus.rokomari.entity.CategoryEntity;
import com.younus.rokomari.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/category")
public class AdminCategoryController {

    @Autowired
    private CategoryService categoryService;

    //get all category api
    @GetMapping("")
    public String getAllCategory(Model model) {
        return findPaginated(1, "id", "desc", model);
    }

    //category pagination & sorting api
    @GetMapping("/page/{pageNo}")
    public String findPaginated(
            @PathVariable(value = "pageNo") int pageNo,
            @RequestParam("sortField") String sortField,
            @RequestParam("sortDirection") String sortDirection,
            Model model
    ) {
        int pageSize = 5;
        Page<CategoryEntity> page = categoryService.findPaginated(pageNo, pageSize, sortField, sortDirection);
        List<CategoryEntity> categoryEntities = page.getContent();

        model.addAttribute("categories", categoryEntities);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseSortDir", sortDirection.equals("asc") ? "desc" : "asc");

        return "pages/back-end/category/manage";
    }

    //show create page & create category api
    @GetMapping("/add")
    public String showAddCategory(Model model) {
        CategoryDto categoryDto = new CategoryDto();
        model.addAttribute("category", categoryDto);
        return "pages/back-end/category/create";
    }

    @PostMapping("/add")
    public String createCategory(@Valid @ModelAttribute("category") CategoryDto categoryDto, BindingResult bindingResult) {
        return categoryService.createCategory(categoryDto, bindingResult);
    }

    //show edit page & update category api
    @GetMapping("/edit")
    public String showEditCategory(Model model, @RequestParam() Long id) {
        return categoryService.showEditCategory(model, id);
    }

    @PostMapping("/edit")
    public String updateCategory(Model model,
                             @RequestParam() Long id,
                             @Valid @ModelAttribute("category") CategoryDto categoryDto,
                             BindingResult bindingResult) {
        return categoryService.updateCategory(model, id, categoryDto, bindingResult);
    }

    //delete category api
    @GetMapping("/delete")
    public String deleteCategory(@RequestParam Long id) {
        return categoryService.deleteCategory(id);
    }
}
