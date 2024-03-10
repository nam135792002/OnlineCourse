package com.springboot.courses.service;

import com.springboot.courses.payload.CategoryDto;
import com.springboot.courses.payload.ClassResponse;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);

    ClassResponse getAll(int pageNo, int pageSize, String sortBy, String sortDir, String keyword);

    CategoryDto get(Integer categoryId);

    CategoryDto update(Integer categoryId, CategoryDto categoryDto);

    String delete(Integer categoryId);
}
