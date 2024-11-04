package com.summit.ecommerce.product.controller;

import com.summit.ecommerce.product.dto.product.CreateProductDTO;
import com.summit.ecommerce.product.dto.product.ReturnProductDTO;
import com.summit.ecommerce.product.entity.ProductStatus;
import com.summit.ecommerce.product.service.ProductServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductServiceImpl productServiceImpl;

    @Autowired
    public ProductController(ProductServiceImpl productServiceImpl) {
        this.productServiceImpl = productServiceImpl;
    }

    @GetMapping
    public Page<ReturnProductDTO> getAllProducts(
            @RequestParam(required = false) Optional<String> status,
            @RequestParam(required = false) Optional<ProductStatus> category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);

        return productServiceImpl.getProducts(category, status, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReturnProductDTO> getCategoryById(@PathVariable UUID id) {
        ReturnProductDTO category = productServiceImpl.getProductById(id);
        return ResponseEntity.ok(category);
    }

    @PostMapping
    public ResponseEntity<ReturnProductDTO> createCategory(@Valid @RequestBody CreateProductDTO createProductDTO) {
        ReturnProductDTO returnProductDTO = productServiceImpl.createProduct(createProductDTO);
        return ResponseEntity.status(201).body(returnProductDTO);
    }
}
