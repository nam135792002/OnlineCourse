package vn.edu.ut.service;

import vn.edu.ut.payload.CategoryDto;
import vn.edu.ut.payload.ClassResponse;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryRequest);

    ClassResponse getAll(int pageNo, int pageSize, String sortBy, String sortDir, String keyword);

    CategoryDto get(Integer categoryId);

    CategoryDto update(Integer categoryId, CategoryDto categoryRequest);

    String delete(Integer categoryId);
}
