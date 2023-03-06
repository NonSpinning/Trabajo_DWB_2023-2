package com.product.api.entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "category")
public class Category implements Comparable<Category>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer category_id;
    
    @NotNull 
    @Column(name = "category")
    private String category;
    
    @NotNull
    @Column(name = "acronym")
    private String acronym;

    @Column(name = "status")
    @Min(value = 0, message="status must be 0 or 1")
    @Max(value = 1, message="status must be 0 or 1")
    @JsonIgnore
    private Integer status;

    public Category(){}

    public Category(Integer category_id, String category, String acronym, Integer status){
        this.category_id = category_id;
        this.category = category;
        this.acronym = acronym;
        this.status = status;
    }
    
    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    /*public String toString(){
        return "{"+category_id+", \""+category+"\", \""+acronym+"\"}";
    }*/
    @Override
    public String toString() {
        return "Category [category_id=" + category_id + ", category=" + category + ", acronym=" + acronym + ", status="
                + status + "]";
    }

    /*a.compareTo(b) == 0 does not imply a.equals(b) */
    public int compareTo(Category c){
            return this.category_id - c.getCategory_id();
    }

    public boolean sameAs(Category c){
        return (this.category.equals(c.category) && this.acronym.equals(c.acronym));
    }
}
