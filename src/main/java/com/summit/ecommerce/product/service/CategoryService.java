package com.summit.ecommerce.product.service;

import com.summit.ecommerce.product.dto.category.CreateCategoryDTO;
import com.summit.ecommerce.product.dto.category.ReturnCategoryDTO;
import com.summit.ecommerce.product.dto.category.UpdateCategoryDTO;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    List<ReturnCategoryDTO> getCategories();
    ReturnCategoryDTO getCategoryById(UUID id);
    ReturnCategoryDTO createCategory(CreateCategoryDTO categoryDTO);
    ReturnCategoryDTO updateCategory(UUID id, UpdateCategoryDTO categoryDTO);
    void deleteCategory(UUID id);
}
