package com.product;
public class Category implements Comparable<Category>{
    private Integer category_id;
    private String category;
    private String acronym;

    public Category(Integer category_id, String category, String acronym){
        this.category_id = category_id;
        this.category = category;
        this.acronym = acronym;
    }

    public Integer getCategory_id(){
        return category_id;
    }

    public String getCategory(){
        return category;
    }

    public String getAcronym(){
        return acronym;
    }

    public String toString(){
        return "{"+category_id+", \""+category+"\", \""+acronym+"\"}";
    }

    /*a.compareTo(b) == 0 does not imply a.equals(b) */
    public int compareTo(Category c){
            return this.category_id - c.getCategory_id();
    }
}
