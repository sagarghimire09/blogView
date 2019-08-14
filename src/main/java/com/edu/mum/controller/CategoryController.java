package com.edu.mum.controller;

import com.edu.mum.domain.Category;
import com.edu.mum.service.impl.CategoryServiceImpl;
import com.edu.mum.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDate;

@Controller
public class CategoryController {
    private CategoryServiceImpl categoryService;
    @Autowired
    public CategoryController(CategoryServiceImpl categoryService){
        this.categoryService = categoryService;
    }

    @GetMapping(value = {"/category/list"})
    public String showCategoryList(@PageableDefault(sort = {"categoryName"}, value = 5)Pageable pageable, Model model){
        Page<Category> categories = categoryService.getAllCategoryPaged(pageable);

        model.addAttribute("categories", categories);
        return  "views/category/list";
    }
    @GetMapping(value = {"/category/new"})
    public String showAddForm(Model model){
        model.addAttribute("category", new Category());
        return "views/category/add";
    }

    @PostMapping(value = {"/category/new"})
    public String addNewCategory(@Valid @ModelAttribute("category") Category category,
                                 BindingResult bindingResult, Model model){
        ModelAndView modelAndView = new ModelAndView();
        if(bindingResult.hasErrors()){
            modelAndView.addObject("successMessage", "Category couldn't be saved");
            modelAndView.setViewName("views/category/list");
        }
        categoryService.saveCategory(category);
        return "redirect:/category/list";
    }
    @GetMapping(value = {"/category/edit/{id}"})
    public String editCategory(@PathVariable("id") Long id, Model model){
        Category category = categoryService.getCategoryById(id).get();
        if(category!=null){
            model.addAttribute("category", category);
            return "views/category/edit";
        }
        return "views/category/list";

    }
    @PostMapping(value = {"/category/update"})
    public String updateCategory(@Valid @ModelAttribute("category") Category category,
                                 BindingResult bindingResult, Model model){
        ModelAndView modelAndView = new ModelAndView();
        if(bindingResult.hasErrors()){
            modelAndView.addObject("successMessage", "Category couldn't be saved");
            modelAndView.setViewName("views/category/list");
        }
        categoryService.saveCategory(category);
        return "redirect:/category/list";
    }
    @GetMapping(value = {"/category/delete/{id}"})
    public String deleteCategory(@PathVariable("id")Long id, Model model){
        categoryService.deleteCategory(id);
        return "redirect:/category/list";
    }
}
