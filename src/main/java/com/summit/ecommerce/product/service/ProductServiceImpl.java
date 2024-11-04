package com.summit.ecommerce.product.service;

import com.summit.ecommerce.product.dto.product.CreateProductDTO;
import com.summit.ecommerce.product.dto.product.ReturnProductDTO;
import com.summit.ecommerce.product.dto.product.UpdateProductDTO;
import com.summit.ecommerce.product.entity.Category;
import com.summit.ecommerce.product.entity.Product;
import com.summit.ecommerce.product.entity.ProductStatus;
import com.summit.ecommerce.product.exception.ResourceNotFoundException;
import com.summit.ecommerce.product.repository.CategoryRepository;
import com.summit.ecommerce.product.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    private final ModelMapper modelMapper;

    @Autowired
    private ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;

        modelMapper = new ModelMapper();
    }

    @Override
    public Page<ReturnProductDTO> getProducts(Optional<ProductStatus> status, Optional<String> category, Pageable pageable) {
        String effectiveStatus = status.map(Enum::name).orElse("APPROVED");

        return category.map(s -> productRepository
                .findByStatusAndCategory(effectiveStatus, s, pageable)
                .map(product -> modelMapper.map(product, ReturnProductDTO.class))).orElseGet(() -> productRepository
                .findByStatus(effectiveStatus, pageable)
                .map(product -> modelMapper.map(product, ReturnProductDTO.class)));
    }

    @Override
    public ReturnProductDTO getProductById(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid category ID"));

        return modelMapper.map(product, ReturnProductDTO.class);
    }

    @Override
    public ReturnProductDTO createProduct(CreateProductDTO createProductDTO) {
        // validation

        Category category = categoryRepository.findById(createProductDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid category ID"));

        Product product = modelMapper.map(createProductDTO, Product.class);

        product.setCategory(category);

        productRepository.save(product);

        return modelMapper.map(product, ReturnProductDTO.class);
    }

    @Override
    public ReturnProductDTO updateProduct(UUID id, UpdateProductDTO productDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid category ID"));

        // Validation

        // Update entity

        productRepository.save(product);

        return modelMapper.map(product, ReturnProductDTO.class);
    }

    @Override
    public void deleteProduct(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid category ID"));

        productRepository.delete(product);
    }
}
