package com.product;

import java.util.List;
import java.util.LinkedList;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CtrlProduct{

    @GetMapping("/category")
    public List<Category> categories(){
        LinkedList<Category> l = new LinkedList<Category>();
        l.add(new Category(1, "Línea Blanca", "LB"));
        l.add(new Category(2, "Electrónica", "EL"));
        l.add(new Category(3, "Frutas y Verduras", "FV"));
        return l;
    }
}