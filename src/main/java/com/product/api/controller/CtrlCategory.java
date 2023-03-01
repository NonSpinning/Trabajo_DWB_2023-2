package com.product.api.controller;

import java.util.LinkedList;
import java.util.List;

import javax.validation.Valid;

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

@RestController
@RequestMapping("/category")
public class CtrlCategory{
    private LinkedList<Category> l;

    public CtrlCategory(){
        l = new LinkedList<Category>();
        l.add(new Category(1, "Línea Blanca", "LB", 1));
        l.add(new Category(2, "Electrónica", "Electr", 1));
    }

    @GetMapping
    public ResponseEntity<List<Category>> getCategories(){
        return new ResponseEntity<>(this.l, HttpStatus.OK);
    }

    @GetMapping("/{category_id}")
    public ResponseEntity<?> getCategory(@PathVariable int category_id){
        for (Category c: l){
            if (c.getCategory_id() == category_id) return new ResponseEntity<Category>(c, HttpStatus.OK);
        }
        return new ResponseEntity<String>("category does not exist", HttpStatus.BAD_REQUEST);
    }

    @PostMapping
    public ResponseEntity<String> createCategory(@Valid @RequestBody Category category, BindingResult bindingResult){
        String message;
        if(bindingResult.hasErrors()){
            message = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        for (Category c: l){
            if (c.sameAs(category)) return new ResponseEntity<>("category already exists", HttpStatus.BAD_REQUEST); 
        }
        return new ResponseEntity<>("category created", HttpStatus.OK);    
    }

    @PutMapping("/{category_id}")
    public ResponseEntity<String> updateCategory(@PathVariable int category_id, @Valid @RequestBody Category category, BindingResult bindingResult){
        String message = "";
        if(bindingResult.hasErrors()){
            message = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        for (Category c: l){
            if (c.getCategory_id() == category_id) return new ResponseEntity<>("category updated", HttpStatus.OK);   
        }
        return new ResponseEntity<>("category does not exist",HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{category_id}")
    public ResponseEntity<String> deleteCategory(@PathVariable int category_id){
        for (Category c: l){
            if (c.getCategory_id() == category_id) return new ResponseEntity<>("category removed", HttpStatus.OK);   
        }
        return new ResponseEntity<>("category does not exist",HttpStatus.BAD_REQUEST);
    }
}