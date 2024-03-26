package tn.esprit.pidev.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.pidev.entities.Category;
import tn.esprit.pidev.repository.ICategoryRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class GestionCategoryImpl implements IGestionCategory{
    @Autowired
    ICategoryRepository categoryRepository;

    @Override
    public List<Category> retrieveAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void removeCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    @Override
    public Category retrieveCategory(Long categoryId) {
        return categoryRepository.findById(categoryId).orElse(null);
    }
}
