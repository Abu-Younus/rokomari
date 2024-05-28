package com.younus.rokomari.service;

import com.younus.rokomari.domain.AuthorDto;
import com.younus.rokomari.entity.AuthorEntity;
import com.younus.rokomari.entity.CategoryEntity;
import com.younus.rokomari.repository.AuthorRepository;
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
public class AuthorServiceImpl implements AuthorService{
    @Autowired
    private AuthorRepository authorRepository;

    //create author method
    @Override
    public String createAuthor(AuthorDto authorDto, BindingResult bindingResult) {
        if(authorDto.getImage().isEmpty()) {
            bindingResult.addError(new FieldError("authorDto","image","The image is required"));
        }

        if(bindingResult.hasErrors()) {
            return "pages/back-end/author/create";
        }

        MultipartFile image = authorDto.getImage();
        Date createdAt = new Date();
        String imageName = createdAt.getTime() + "_" + image.getOriginalFilename();

        try {
            String uploadDir = "public/images/author/";
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

        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setName(authorDto.getName());
        authorEntity.setDetails(authorDto.getDetails());
        authorEntity.setImage(imageName);
        authorEntity.setFollowers(0);
        authorEntity.setCreatedAt(new Date());

        authorRepository.save(authorEntity);

        return "redirect:/admin/author/manage?create_success";
    }

    //get all author method to product
    @Override
    public List<AuthorEntity> allAuthor(Model model) {
        List<AuthorEntity> authors= authorRepository.findAll();
        return authors;
    }

    //pagination & sorting method
    @Override
    public Page<AuthorEntity> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return authorRepository.findAll(pageable);
    }

    //show edit author page method
    @Override
    public String showEditAuthor(Model model, Long id) {
        try {
            AuthorEntity authorEntity = authorRepository.findById(id).get();
            model.addAttribute("authorEntity", authorEntity);

            AuthorDto authorDto = new AuthorDto();
            authorDto.setName(authorEntity.getName());
            authorDto.setDetails(authorEntity.getDetails());

            model.addAttribute("author", authorDto);

        }
        catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return "redirect:/admin/author";
        }
        return "pages/back-end/author/edit";
    }

    //update author method
    @Override
    public String updateAuthor(Model model, Long id, AuthorDto authorDto, BindingResult bindingResult) {
        try {
            AuthorEntity authorEntity = authorRepository.findById(id).get();
            model.addAttribute("authorEntity", authorEntity);

            if(bindingResult.hasErrors()) {
                return "pages/back-end/author/edit";
            }

            if(!authorDto.getImage().isEmpty()) {
                //delete old image
                String uploadDir = "public/images/author/";
                Path oldImagePath = Paths.get(uploadDir + authorEntity.getImage());

                try {
                    Files.delete(oldImagePath);
                } catch (Exception e) {
                    System.out.println("Exception: " + e.getMessage());
                }

                //save new image
                MultipartFile image = authorDto.getImage();
                Date createdAt = new Date();
                String imageName = createdAt.getTime() + "_" + image.getOriginalFilename();

                try(InputStream inputStream = image.getInputStream()) {
                    Files.copy(inputStream, Paths.get(uploadDir + imageName), StandardCopyOption.REPLACE_EXISTING);
                }

                authorEntity.setImage(imageName);
            }

            authorEntity.setName(authorDto.getName());
            authorEntity.setDetails(authorDto.getDetails());
            authorEntity.setUpdatedAt(new Date());

            authorRepository.save(authorEntity);
        }
        catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        return "redirect:/admin/author?update_success";
    }

    //delete author method
    @Override
    public String deleteAuthor(Long id) {
        AuthorEntity authorEntity = authorRepository.findById(id).get();

        //delete old image
        String uploadDir = "public/images/author/";
        Path imagePath = Paths.get(uploadDir + authorEntity.getImage());

        try {
            Files.delete(imagePath);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }

        authorRepository.delete(authorEntity);

        return "redirect:/admin/author?delete_success";
    }
}
