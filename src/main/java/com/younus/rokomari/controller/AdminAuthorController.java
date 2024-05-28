package com.younus.rokomari.controller;

import com.younus.rokomari.domain.AuthorDto;
import com.younus.rokomari.entity.AuthorEntity;
import com.younus.rokomari.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/author")
public class AdminAuthorController {

    @Autowired
    private AuthorService authorService;

    //get all author api
    @GetMapping("")
    public String getAllAuthor(Model model) {
        return findPaginated(1, "id", "desc", model);
    }

    //pagination & sorting api
    @GetMapping("/page/{pageNo}")
    public String findPaginated(
            @PathVariable(value = "pageNo") int pageNo,
            @RequestParam("sortField") String sortField,
            @RequestParam("sortDirection") String sortDirection,
            Model model
    ) {
        int pageSize = 5;

        Page<AuthorEntity> page = authorService.findPaginated(pageNo, pageSize, sortField, sortDirection);
        List<AuthorEntity> authorEntities = page.getContent();

        model.addAttribute("authors", authorEntities);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseSortDir", sortDirection.equals("asc") ? "desc" : "asc");

        return "pages/back-end/author/manage";
    }

    //show add page & create author api
    @GetMapping("/add")
    public String showAddAuthor(Model model) {
        AuthorDto authorDto = new AuthorDto();
        model.addAttribute("author", authorDto);
        return "pages/back-end/author/create";
    }

    @PostMapping("/add")
    public String createAuthor(@Valid @ModelAttribute("author") AuthorDto authorDto, BindingResult bindingResult) {
        return authorService.createAuthor(authorDto, bindingResult);
    }

    //show edit page & update category api
    @GetMapping("/edit")
    public String showEditAuthor(Model model, @RequestParam Long id) {
        return authorService.showEditAuthor(model, id);
    }

    @PostMapping("/edit")
    public String updateAuthor(Model model,
                                 @RequestParam Long id,
                                 @Valid @ModelAttribute("author") AuthorDto authorDto,
                                 BindingResult bindingResult) {
        return authorService.updateAuthor(model, id, authorDto, bindingResult);
    }

    //delete author api
    @GetMapping("/delete")
    public String deleteAuthor(@RequestParam Long id) {
        return authorService.deleteAuthor(id);
    }

}
