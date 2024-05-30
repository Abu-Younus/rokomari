package com.younus.rokomari.controller;

import com.younus.rokomari.domain.BrandDto;
import com.younus.rokomari.entity.BrandEntity;
import com.younus.rokomari.service.BrandService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/brand")
public class AdminBrandController {
    @Autowired
    private BrandService brandService;

    //get all brand api
    @GetMapping("")
    public String getAllBrand(Model model) {
        return findPaginated(1, "id", "desc", model);
    }

    //brand pagination & sorting api
    @GetMapping("/page/{pageNo}")
    public String findPaginated(
            @PathVariable(value = "pageNo") int pageNo,
            @RequestParam("sortField") String sortField,
            @RequestParam("sortDirection") String sortDirection,
            Model model
    ) {
        int pageSize = 5;
        Page<BrandEntity> page = brandService.findPaginated(pageNo, pageSize, sortField, sortDirection);
        List<BrandEntity> brandEntities = page.getContent();

        model.addAttribute("brands", brandEntities);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseSortDir", sortDirection.equals("asc") ? "desc" : "asc");

        return "pages/back-end/brand/manage";
    }

    //show create page & create brand api
    @GetMapping("/add")
    public String showAddBrand(Model model) {
        BrandDto brandDto = new BrandDto();
        model.addAttribute("brand", brandDto);
        return "pages/back-end/brand/create";
    }

    @PostMapping("/add")
    public String createBrand(@Valid @ModelAttribute("brand") BrandDto brandDto, BindingResult bindingResult) {
        return brandService.createBrand(brandDto, bindingResult);
    }

    //show edit page & update brand api
    @GetMapping("/edit")
    public String showEditBrand(Model model, @RequestParam() Long id) {
        return brandService.showEditBrand(model, id);
    }

    @PostMapping("/edit")
    public String updateBrand(Model model,
                                 @RequestParam() Long id,
                                 @Valid @ModelAttribute("brand") BrandDto brandDto,
                                 BindingResult bindingResult) {
        return brandService.updateBrand(model, id, brandDto, bindingResult);
    }

    //delete brand api
    @GetMapping("/delete")
    public String deleteBrand(@RequestParam Long id) {
        return brandService.deleteBrand(id);
    }
}
