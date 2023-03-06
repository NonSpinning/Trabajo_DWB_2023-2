package com.product.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.api.entity.Category;
import com.product.api.service.SvcCategory;

@RestController
@RequestMapping("/category")
public class CtrlCategory{
    @Autowired
    SvcCategory svc;

    @GetMapping
    public ResponseEntity<List<Category>> getCategories(){
        return new ResponseEntity<>(svc.getCategories(), HttpStatus.OK);
    }

    @GetMapping("/{category_id}")
    public ResponseEntity<?> getCategory(@PathVariable int category_id){
        Category ret = svc.getCategory(category_id);
        if (ret == null){
            return new ResponseEntity<String>("category does not exist", HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<Category>(ret, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<String> createCategory(@Valid @RequestBody Category category, BindingResult bindingResult){
        String message;
        if(bindingResult.hasErrors()){
            message = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        String ret = svc.createCategory(category);
        if (ret.equals("category created")){
            return new ResponseEntity<>(ret, HttpStatus.CREATED);
        }
        if (ret.equals("category has been activated")){
            return new ResponseEntity<>(ret, HttpStatus.OK);
        }
        return new ResponseEntity<>(ret, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{category_id}")
    public ResponseEntity<String> updateCategory(@PathVariable int category_id, @Valid @RequestBody Category category, BindingResult bindingResult){
        String message = "";
        if(bindingResult.hasErrors()){
            message = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        String ret = svc.updateCategory(category_id, category);
        if (ret == "category updated"){
            return new ResponseEntity<>(ret, HttpStatus.OK);
        }
        return new ResponseEntity<>(ret, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{category_id}")
    public ResponseEntity<String> deleteCategory(@PathVariable int category_id){
        String ret = svc.deleteCategory(category_id);
        if (ret.equals("category does not exist")){
            return new ResponseEntity<>(ret, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }
} 