package com.product.api.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.product.api.entity.Category;

public interface RepoCategory extends JpaRepository<Category, Integer>{

    @Query(value = "SELECT * FROM category WHERE status = :status", nativeQuery = true)
    List<Category> findByStatus(@Param("status") Integer status);

    @Query(value = "SELECT * FROM category WHERE category_id = :id AND status = 1", nativeQuery = true)
    Optional<Category> findByCategoryId(@Param("id") Integer id);

    @Query(value = "SELECT * FROM category WHERE category_id = :id", nativeQuery = true)
    Optional<Category> findAllByCategoryId(@Param("id") Integer id);

    @Query(value = "SELECT * FROM category WHERE category = :category", nativeQuery = true)
    Optional<Category> findByCategory(@Param("category") String category);

    @Query(value = "SELECT * FROM category WHERE acronym = :acronym", nativeQuery = true)
    Optional<Category> findByAcronym(@Param("acronym") String acronym);

    @Query(value = "SELECT * FROM category WHERE category = :category AND acronym = :acronym", nativeQuery = true)
    Optional<Category> findCategory(@Param("category") String category, @Param("acronym") String acronym);
    
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO category (category,acronym,status) VALUES(:category,:acronym,1)", nativeQuery = true)
    void createCategory(@Param("category") String category, @Param("acronym") String acronym);

    @Modifying
    @Transactional
    @Query(value = "UPDATE category SET category = :category, acronym = :acronym WHERE category_id = :category_id", nativeQuery = true)
    void updateCategory(@Param("category_id") Integer category_id, @Param("category") String category, @Param("acronym") String acronym);
    
    @Modifying
    @Transactional
    @Query(value = "UPDATE category SET status = 1 WHERE category_id = :id", nativeQuery = true)
    void activateCategory(@Param("id") Integer id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE category SET status = 0 WHERE category_id = :id", nativeQuery = true)
    void deleteById(@Param("id") Integer id);
}