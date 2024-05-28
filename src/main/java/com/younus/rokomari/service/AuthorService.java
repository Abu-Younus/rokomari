package com.younus.rokomari.service;

import com.younus.rokomari.domain.AuthorDto;
import com.younus.rokomari.entity.AuthorEntity;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface AuthorService {
    //create author method
    String createAuthor(AuthorDto authorDto, BindingResult bindingResult);

    //get all author method
    List<AuthorEntity> allAuthor(Model model);

    //pagination & sorting method
    Page<AuthorEntity> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

    //show edit page method
    String showEditAuthor(Model model, Long id);

    //update author method
    String updateAuthor(Model model, Long id, AuthorDto authorDto, BindingResult bindingResult);

    //delete author method
    String deleteAuthor(Long id);
}
