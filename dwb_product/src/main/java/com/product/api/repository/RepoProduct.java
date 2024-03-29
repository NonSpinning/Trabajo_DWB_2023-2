package com.product.api.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.product.api.entity.Product;

public interface RepoProduct extends JpaRepository<Product, Integer>{

	// 3. Implementar la firma de un método que permita consultar un producto por su código GTIN y con estatus 1
	Product findByGtinAndStatus(String gtin, Integer status);
	

	@Modifying
	@Transactional
	@Query(value ="UPDATE product "
					+ "SET gtin = :gtin, "
						+ "product = :product, "
						+ "description = :description, "
						+ "price = :price, "
						+ "stock = :stock, "
						+ "status = 1, "
						+ "category_id = :category_id "
					+ "WHERE product_id = :product_id", nativeQuery = true)
	Integer updateProduct(
			@Param("product_id") Integer product_id,
			@Param("gtin") String gtin, 
			@Param("product") String product, 
			@Param("description") String description, 
			@Param("price") Double price, 
			@Param("stock") Integer stock,
			@Param("category_id") Integer category_id
		);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE product SET status = 1 WHERE gtin = :gtin", nativeQuery = true)
	void activateProduct(@Param("gtin") String gtin);

	@Modifying
	@Transactional
	@Query(value ="UPDATE product SET status = 0 WHERE product_id = :product_id AND status = 1", nativeQuery = true)
	Integer deleteProduct(@Param("product_id") Integer product_id);
	
	@Modifying
	@Transactional
	@Query(value ="UPDATE product SET stock = :stock WHERE gtin = :gtin AND status = 1", nativeQuery = true)
	Integer updateProductStock(@Param("gtin") String gtin, @Param("stock") Integer stock);

	@Modifying
	@Transactional
	@Query(value="UPDATE product SET category_id = :category_id WHERE gtin = :gtin", nativeQuery=true)
	Integer updateProductCategory(@Param("gtin") String gtin, @Param("category_id") Integer category_id);
}
