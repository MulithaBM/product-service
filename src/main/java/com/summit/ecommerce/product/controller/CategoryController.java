package com.summit.ecommerce.product.controller;

import com.summit.ecommerce.product.dto.category.CreateCategoryDTO;
import com.summit.ecommerce.product.dto.category.ReturnCategoryDTO;
import com.summit.ecommerce.product.dto.category.UpdateCategoryDTO;
import com.summit.ecommerce.product.service.CategoryServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryServiceImpl categoryServiceImpl;

    @Autowired
    public CategoryController(CategoryServiceImpl categoryServiceImpl) {
        this.categoryServiceImpl = categoryServiceImpl;
    }

    @GetMapping
    public ResponseEntity<List<ReturnCategoryDTO>> getAllCategories() {
        List<ReturnCategoryDTO> returnCategoryDTOS = categoryServiceImpl.getCategories();
        return ResponseEntity.ok(returnCategoryDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReturnCategoryDTO> getCategoryById(@PathVariable UUID id) {
        ReturnCategoryDTO category = categoryServiceImpl.getCategoryById(id);
        return ResponseEntity.ok(category);
    }

    @PostMapping
    public ResponseEntity<ReturnCategoryDTO> createCategory(@Valid @RequestBody CreateCategoryDTO createCategoryDTO) {
        ReturnCategoryDTO returnCategoryDTO = categoryServiceImpl.createCategory(createCategoryDTO);
        return ResponseEntity.status(201).body(returnCategoryDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ReturnCategoryDTO> updateCategory(@PathVariable UUID id, @Valid @RequestBody UpdateCategoryDTO updateCategoryDTO) {
        ReturnCategoryDTO returnCategoryDTO = categoryServiceImpl.updateCategory(id, updateCategoryDTO);
        return ResponseEntity.ok(returnCategoryDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id) {
        categoryServiceImpl.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
