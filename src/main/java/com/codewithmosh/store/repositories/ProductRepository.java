package com.codewithmosh.store.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.codewithmosh.store.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @EntityGraph(attributePaths = "category")
    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId")
    List<Product> getProductsByCategoryId(@Param("categoryId") Byte categoryId);

    @EntityGraph(attributePaths = "category")
    @Query("SELECT p FROM Product p")
    List<Product> findAllWithCategory();
}