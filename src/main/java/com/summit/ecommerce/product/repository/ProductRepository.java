package com.summit.ecommerce.product.repository;

import com.summit.ecommerce.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {

    Page<Product> findByStatus(String status, Pageable pageable);

    Page<Product> findByStatusAndCategory(String category, String status, Pageable pageable);
}