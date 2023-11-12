package com.localservicesreview.servicemanagementservice.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.localservicesreview.servicemanagementservice.exceptions.CategoryNotFoundException;
import com.localservicesreview.servicemanagementservice.models.Category;
import com.localservicesreview.servicemanagementservice.repositories.CategoryRepo;

@Service
public class CategoryService {
    private CategoryRepo categoryRepo;

    public CategoryService(CategoryRepo categoryRepo){
        this.categoryRepo = categoryRepo;
    }

    public Category createCategory(String name){
        Optional<Category> category = categoryRepo.findByName(name.toLowerCase());
        if(!category.isPresent()){
            Category newCategory = new Category(name.toLowerCase());
            return categoryRepo.save(newCategory);
        }
        return category.get();
    }

    public Category getCategory(UUID categoryId){
        Optional<Category> searchCategory = categoryRepo.findById(categoryId);
        if(!searchCategory.isPresent()){
            throw new CategoryNotFoundException("Category Service: Category not found with id: "+ categoryId);
        }
        return searchCategory.get();
    }

    public List<Category> getAllCategories(){
        return categoryRepo.findAll();
    }

}
