package tn.esprit.pidev.services;

import tn.esprit.pidev.entities.Category;

import java.util.List;

public interface IGestionCategory {
    List<Category> retrieveAllCategories();
    Category addCategory(Category category);
    Category updateCategory(Category category);
    void removeCategory(Long categoryId);
    Category retrieveCategory(Long categoryId);
}
