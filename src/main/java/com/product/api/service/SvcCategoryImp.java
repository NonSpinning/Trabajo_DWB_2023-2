package com.product.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.api.entity.Category;
import com.product.api.repository.RepoCategory;

@Service
public class SvcCategoryImp implements SvcCategory{
    @Autowired
    RepoCategory repo;

    @Override
    public List<Category> getCategories() {
        return repo.findByStatus(1);
    }

    @Override
    public Category getCategory(Integer category_id) {
        Optional<Category> ret = repo.findByCategoryId(category_id);
        if (ret.isPresent()){
            return ret.get();
        }else{
            return null;
        }
    }

    @Override
    public String createCategory(Category category) {
        Optional<Category> categoryFound = repo.findCategory(category.getCategory(), category.getAcronym());
        if (categoryFound.isPresent()){
            Category cf = categoryFound.get();
            if (cf.getStatus() == 0){
                repo.activateCategory(cf.getCategory_id());
                return "category has been activated";
            }else{
                return "category already exists";
            }
        }else{
            Optional<Category> categoryCategory = repo.findByCategory(category.getCategory());
            if(categoryCategory.isPresent()){
                return "attributes are mismatched";
            }else{
                Optional<Category> categoryAcronym = repo.findByAcronym(category.getAcronym());
                if (categoryAcronym.isPresent()){
                    return "attributes are mismatched";
                }else{
                    repo.createCategory(category.getCategory(), category.getAcronym());
                    return "category created";
                }
            }
        }
    }

    @Override
    public String updateCategory(Integer category_id, Category category) {
        Optional<Category> categorySaved = repo.findAllByCategoryId(category_id);
        if(categorySaved.isPresent()){
            if(categorySaved.get().getStatus() == 0){
                return "category is not active";
            }else{
                Optional<Category> categoryCategory = repo.findByCategory(category.getCategory());
                if (categoryCategory.isPresent()) return "category already exists";
                Optional<Category> categoryAcronym = repo.findByAcronym(category.getAcronym());
                if (categoryAcronym.isPresent()) return "category already exists";
                repo.updateCategory(category_id, category.getCategory(), category.getAcronym());
                return "category updated";
            }
        }else{
            return "category does not exist";
        }
    }

    @Override
    public String deleteCategory(Integer category_id) {
        Optional<Category> categorySaved = repo.findByCategoryId(category_id);
        if (!categorySaved.isPresent()){
            return "category does not exist";
        }else{
            repo.deleteById(category_id);
            return "category removed";
        }
    }
    
}
