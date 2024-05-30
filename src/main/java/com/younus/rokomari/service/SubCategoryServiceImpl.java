package com.younus.rokomari.service;

import com.younus.rokomari.domain.CategoryDto;
import com.younus.rokomari.domain.SubCategoryDto;
import com.younus.rokomari.entity.CategoryEntity;
import com.younus.rokomari.entity.SubCategoryEntity;
import com.younus.rokomari.repository.CategoryRepository;
import com.younus.rokomari.repository.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

@Service
public class SubCategoryServiceImpl implements SubCategoryService{
    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    //pagination & sorting method
    @Override
    public Page<SubCategoryEntity> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return subCategoryRepository.findAll(pageable);
    }

    //create sub category method
    @Override
    public String createSubCategory(SubCategoryDto subCategoryDto, BindingResult bindingResult) {
        if(subCategoryDto.getImage().isEmpty()) {
            bindingResult.addError(new FieldError("subCategoryDto", "image", "The image is required"));
        }
        if(bindingResult.hasErrors()) {
            return "pages/back-end/sub-category/create";
        }

        MultipartFile image = subCategoryDto.getImage();
        Date createdAt = new Date();
        String imageName = createdAt.getTime() + "_" + image.getOriginalFilename();
        try{
            String uploadDir = "public/images/sub-category/";
            Path uploadPath = Paths.get(uploadDir);
            if(!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            try(InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream, Paths.get(uploadDir + imageName), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }

        SubCategoryEntity subCategoryEntity = new SubCategoryEntity();
        subCategoryEntity.setCategory(subCategoryDto.getCategory());
        subCategoryEntity.setName(subCategoryDto.getName());
        subCategoryEntity.setImage(imageName);
        subCategoryEntity.setCreatedAt(new Date());

        subCategoryRepository.save(subCategoryEntity);

        return "redirect:/admin/sub-category?create_success";
    }

    //show edit sub category page method
    @Override
    public String showEditSubCategory(Model model, Long id) {
        try {
            SubCategoryEntity subCategoryEntity = subCategoryRepository.findById(id).get();
            model.addAttribute("subCategoryEntity", subCategoryEntity);

            SubCategoryDto subCategoryDto = new SubCategoryDto();
            subCategoryDto.setName(subCategoryEntity.getName());

            model.addAttribute("subCategory", subCategoryDto);
            model.addAttribute("categories", categoryRepository.findAll());

        }
        catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return "redirect:/admin/sub-category";
        }
        return "pages/back-end/sub-category/edit";
    }

    //update sub category method
    @Override
    public String updateSubCategory(Model model, Long id, SubCategoryDto subCategoryDto, BindingResult bindingResult) {
        try {
            SubCategoryEntity subCategoryEntity = subCategoryRepository.findById(id).get();
            model.addAttribute("subCategoryEntity", subCategoryEntity);

            if(bindingResult.hasErrors()) {
                return "pages/back-end/sub-category/edit";
            }

            if(!subCategoryDto.getImage().isEmpty()) {
                //delete old image
                String uploadDir = "public/images/sub-category/";
                Path oldImagePath = Paths.get(uploadDir + subCategoryEntity.getImage());

                try {
                    Files.delete(oldImagePath);
                } catch (Exception e) {
                    System.out.println("Exception: " + e.getMessage());
                }

                //save new image
                MultipartFile image = subCategoryDto.getImage();
                Date createdAt = new Date();
                String imageName = createdAt.getTime() + "_" + image.getOriginalFilename();

                try(InputStream inputStream = image.getInputStream()) {
                    Files.copy(inputStream, Paths.get(uploadDir + imageName), StandardCopyOption.REPLACE_EXISTING);
                }

                subCategoryEntity.setImage(imageName);
            }

            subCategoryEntity.setCategory(subCategoryDto.getCategory());
            subCategoryEntity.setName(subCategoryDto.getName());
            subCategoryEntity.setUpdatedAt(new Date());

            subCategoryRepository.save(subCategoryEntity);
        }
        catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        return "redirect:/admin/sub-category?update_success";
    }

    //delete sub category method
    @Override
    public String deleteSubCategory(Long id) {
        SubCategoryEntity subCategoryEntity = subCategoryRepository.findById(id).get();

        //delete old image
        String uploadDir = "public/images/sub-category/";
        Path imagePath = Paths.get(uploadDir + subCategoryEntity.getImage());

        try {
            Files.delete(imagePath);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }

        subCategoryRepository.delete(subCategoryEntity);

        return "redirect:/admin/sub-category?delete_success";
    }
}
