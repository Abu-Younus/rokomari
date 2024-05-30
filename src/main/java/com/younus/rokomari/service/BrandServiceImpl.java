package com.younus.rokomari.service;

import com.younus.rokomari.domain.BrandDto;
import com.younus.rokomari.entity.BrandEntity;
import com.younus.rokomari.repository.BrandRepository;
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

@Service
public class BrandServiceImpl implements BrandService{
    @Autowired
    private BrandRepository brandRepository;

    //create brand method
    @Override
    public String createBrand(BrandDto brandDto, BindingResult bindingResult) {
        if(brandDto.getImage().isEmpty()) {
            bindingResult.addError(new FieldError("brandDto", "image", "The image is required"));
        }
        if(bindingResult.hasErrors()) {
            return "pages/back-end/brand/create";
        }

        MultipartFile image = brandDto.getImage();
        Date createdAt = new Date();
        String imageName = createdAt.getTime() + "_" + image.getOriginalFilename();
        try{
            String uploadDir = "public/images/brand/";
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

        BrandEntity brandEntity = new BrandEntity();
        brandEntity.setName(brandDto.getName());
        brandEntity.setImage(imageName);
        brandEntity.setCreatedAt(new Date());

        brandRepository.save(brandEntity);

        return "redirect:/admin/brand?create_success";
    }

    //pagination & sorting method
    @Override
    public Page<BrandEntity> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return brandRepository.findAll(pageable);
    }

    //show edit category page method
    @Override
    public String showEditBrand(Model model, Long id) {
        try {
            BrandEntity brandEntity = brandRepository.findById(id).get();
            model.addAttribute("brandEntity", brandEntity);

            BrandDto brandDto = new BrandDto();
            brandDto.setName(brandEntity.getName());

            model.addAttribute("brand", brandDto);

        }
        catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return "redirect:/admin/brand";
        }
        return "pages/back-end/brand/edit";
    }

    //update brand method
    @Override
    public String updateBrand(Model model, Long id, BrandDto brandDto, BindingResult bindingResult) {
        try {
            BrandEntity brandEntity = brandRepository.findById(id).get();
            model.addAttribute("brandEntity", brandEntity);

            if(bindingResult.hasErrors()) {
                return "pages/back-end/brand/edit";
            }

            if(!brandDto.getImage().isEmpty()) {
                //delete old image
                String uploadDir = "public/images/brand/";
                Path oldImagePath = Paths.get(uploadDir + brandEntity.getImage());

                try {
                    Files.delete(oldImagePath);
                } catch (Exception e) {
                    System.out.println("Exception: " + e.getMessage());
                }

                //save new image
                MultipartFile image = brandDto.getImage();
                Date createdAt = new Date();
                String imageName = createdAt.getTime() + "_" + image.getOriginalFilename();

                try(InputStream inputStream = image.getInputStream()) {
                    Files.copy(inputStream, Paths.get(uploadDir + imageName), StandardCopyOption.REPLACE_EXISTING);
                }

                brandEntity.setImage(imageName);
            }

            brandEntity.setName(brandDto.getName());
            brandEntity.setUpdatedAt(new Date());

            brandRepository.save(brandEntity);
        }
        catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        return "redirect:/admin/brand?update_success";
    }

    //delete brand method
    @Override
    public String deleteBrand(Long id) {
        BrandEntity brandEntity = brandRepository.findById(id).get();

        //delete old image
        String uploadDir = "public/images/brand/";
        Path imagePath = Paths.get(uploadDir + brandEntity.getImage());

        try {
            Files.delete(imagePath);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }

        brandRepository.delete(brandEntity);

        return "redirect:/admin/brand?delete_success";
    }
}
