package com.summit.ecommerce.product.service;

import com.summit.ecommerce.product.dto.product.CreateProductDTO;
import com.summit.ecommerce.product.dto.product.ReturnProductDTO;
import com.summit.ecommerce.product.dto.product.UpdateProductDTO;
import com.summit.ecommerce.product.entity.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface ProductService {
    Page<ReturnProductDTO> getProducts(Optional<ProductStatus> status, Optional<String> category, Pageable pageable);
    ReturnProductDTO getProductById(UUID id);
    ReturnProductDTO createProduct(CreateProductDTO product);
    ReturnProductDTO updateProduct(UUID id, UpdateProductDTO product);
    void deleteProduct(UUID id);
}
