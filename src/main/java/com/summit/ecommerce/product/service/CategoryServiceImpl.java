package com.summit.ecommerce.product.service;

import com.summit.ecommerce.product.dto.category.CreateCategoryDTO;
import com.summit.ecommerce.product.dto.category.ReturnCategoryDTO;
import com.summit.ecommerce.product.dto.category.UpdateCategoryDTO;
import com.summit.ecommerce.product.entity.Category;
import com.summit.ecommerce.product.exception.ResourceNotFoundException;
import com.summit.ecommerce.product.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    private CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
        modelMapper = new ModelMapper();
    }

    @Override
    public List<ReturnCategoryDTO> getCategories() {
        return categoryRepository
                .findAll()
                .stream()
                .map(category -> modelMapper.map(category, ReturnCategoryDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ReturnCategoryDTO getCategoryById(UUID id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid category ID"));

        return modelMapper.map(category, ReturnCategoryDTO.class);
    }

    @Override
    public ReturnCategoryDTO createCategory(CreateCategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        categoryRepository.save(category);

        return modelMapper.map(category, ReturnCategoryDTO.class);
    }

    @Override
    public ReturnCategoryDTO updateCategory(UUID id, UpdateCategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid category ID"));

        if (categoryDTO.getName() != null) {
            category.setName(categoryDTO.getName());
        }

        categoryRepository.save(category);

        return modelMapper.map(category, ReturnCategoryDTO.class);
    }

    @Override
    public void deleteCategory(UUID id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid category ID"));

        categoryRepository.delete(category);
    }
}
