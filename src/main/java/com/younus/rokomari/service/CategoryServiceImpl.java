package com.younus.rokomari.service;

import com.younus.rokomari.domain.CategoryDto;
import com.younus.rokomari.entity.CategoryEntity;
import com.younus.rokomari.repository.CategoryRepository;
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
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    private CategoryRepository categoryRepository;

    //create category method
    @Override
    public String createCategory(CategoryDto categoryDto, BindingResult bindingResult) {
        if(categoryDto.getImage().isEmpty()) {
            bindingResult.addError(new FieldError("categoryDto", "image", "The image is required"));
        }
        if(bindingResult.hasErrors()) {
            return "pages/back-end/category/create";
        }

        MultipartFile image = categoryDto.getImage();
        Date createdAt = new Date();
        String imageName = createdAt.getTime() + "_" + image.getOriginalFilename();
        try{
            String uploadDir = "public/images/category/";
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

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(categoryDto.getName());
        categoryEntity.setImage(imageName);
        categoryEntity.setCreatedAt(new Date());

        categoryRepository.save(categoryEntity);

        return "redirect:/admin/category?create_success";
    }

    @Override
    public Page<CategoryEntity> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return categoryRepository.findAll(pageable);
    }

    //get all category method to subcategory
    @Override
    public List<CategoryEntity> allCategory() {
        List<CategoryEntity> categories = categoryRepository.findAll();
        return categories;
    }

    @Override
    public String showEditCategory(Model model, Long id) {
        try {
            CategoryEntity categoryEntity = categoryRepository.findById(id).get();
            model.addAttribute("categoryEntity", categoryEntity);

            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setName(categoryEntity.getName());

            model.addAttribute("category", categoryDto);

        }
        catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return "redirect:/admin/category";
        }
        return "pages/back-end/category/edit";
    }

    @Override
    public String updateCategory(Model model, Long id, CategoryDto categoryDto, BindingResult bindingResult) {
        try {
            CategoryEntity categoryEntity = categoryRepository.findById(id).get();
            model.addAttribute("categoryEntity", categoryEntity);

            if(bindingResult.hasErrors()) {
                return "pages/back-end/category/edit";
            }

            if(!categoryDto.getImage().isEmpty()) {
                //delete old image
                String uploadDir = "public/images/category/";
                Path oldImagePath = Paths.get(uploadDir + categoryEntity.getImage());

                try {
                    Files.delete(oldImagePath);
                } catch (Exception e) {
                    System.out.println("Exception: " + e.getMessage());
                }

                //save new image
                MultipartFile image = categoryDto.getImage();
                Date createdAt = new Date();
                String imageName = createdAt.getTime() + "_" + image.getOriginalFilename();

                try(InputStream inputStream = image.getInputStream()) {
                    Files.copy(inputStream, Paths.get(uploadDir + imageName), StandardCopyOption.REPLACE_EXISTING);
                }

                categoryEntity.setImage(imageName);
            }

            categoryEntity.setName(categoryDto.getName());
            categoryEntity.setUpdatedAt(new Date());

            categoryRepository.save(categoryEntity);
        }
        catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        return "redirect:/admin/category?update_success";
    }

    @Override
    public String deleteCategory(Long id) {
        CategoryEntity categoryEntity = categoryRepository.findById(id).get();

        //delete old image
        String uploadDir = "public/images/category/";
        Path imagePath = Paths.get(uploadDir + categoryEntity.getImage());

        try {
            Files.delete(imagePath);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }

        categoryRepository.delete(categoryEntity);

        return "redirect:/admin/category?delete_success";
    }
}
