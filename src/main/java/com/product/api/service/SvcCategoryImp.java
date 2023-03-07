package com.product.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.product.api.dto.ApiResponse;
import com.product.api.entity.Category;
import com.product.api.repository.RepoCategory;
import com.product.exception.ApiException;

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
            throw new ApiException(HttpStatus.BAD_REQUEST,"category does not exist");
        }
    }

    @Override
    public ApiResponse createCategory(Category category) {
        Optional<Category> categoryFound = repo.findCategory(category.getCategory(), category.getAcronym());
        if (categoryFound.isPresent()){
            Category cf = categoryFound.get();
            if (cf.getStatus() == 0){
                repo.activateCategory(cf.getCategory_id());
                return new ApiResponse("category has been activated");
            }else{
                throw new ApiException(HttpStatus.BAD_REQUEST, "category already exists");
            }
        }else{
            Optional<Category> categoryCategory = repo.findByCategory(category.getCategory());
            if(categoryCategory.isPresent()){
                throw new ApiException(HttpStatus.BAD_REQUEST, "attributes are mismatched");
            }else{
                Optional<Category> categoryAcronym = repo.findByAcronym(category.getAcronym());
                if (categoryAcronym.isPresent()){
                    throw new ApiException(HttpStatus.BAD_REQUEST, "attributes are mismatched");
                }else{
                    repo.createCategory(category.getCategory(), category.getAcronym());
                    return new ApiResponse("category created");//TODO this should be 201
                }
            }
        }
    }

    @Override
    public ApiResponse updateCategory(Integer category_id, Category category) {
        Optional<Category> categorySaved = repo.findAllByCategoryId(category_id);
        if(categorySaved.isPresent()){
            if(categorySaved.get().getStatus() == 0){
                throw new ApiException(HttpStatus.BAD_REQUEST,"category is not active");
            }else{
                Optional<Category> categoryCategory = repo.findByCategory(category.getCategory());
                if (categoryCategory.isPresent()) throw new ApiException(HttpStatus.BAD_REQUEST,"category already exists");
                Optional<Category> categoryAcronym = repo.findByAcronym(category.getAcronym());
                if (categoryAcronym.isPresent()) throw new ApiException(HttpStatus.BAD_REQUEST, "category already exists");
                repo.updateCategory(category_id, category.getCategory(), category.getAcronym());
                return  new ApiResponse("category updated");
            }
        }else{
            throw new ApiException(HttpStatus.BAD_REQUEST, "category does not exist");
        }
    }

    @Override
    public ApiResponse deleteCategory(Integer category_id) {
        Optional<Category> categorySaved = repo.findByCategoryId(category_id);
        if (!categorySaved.isPresent()){
            throw new ApiException(HttpStatus.NOT_FOUND,"category does not exist");
        }else{
            repo.deleteById(category_id);
            return new ApiResponse("category removed");
        }
    }
    
}
