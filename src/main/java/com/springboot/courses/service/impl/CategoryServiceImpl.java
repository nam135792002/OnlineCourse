package com.springboot.courses.service.impl;

import com.springboot.courses.entity.Category;
import com.springboot.courses.exception.BlogApiException;
import com.springboot.courses.exception.ResourceNotFoundException;
import com.springboot.courses.payload.CategoryDto;
import com.springboot.courses.payload.ClassResponse;
import com.springboot.courses.repository.CategoryRepository;
import com.springboot.courses.service.CategoryService;
import com.springboot.courses.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired private CategoryRepository categoryRepository;
    @Autowired private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryRequest) {

        // check duplicate category name.
        if(categoryRepository.existsCategoriesByName(categoryRequest.getName())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Category name have existed!");
        }

        // check duplicate category slug
        if(categoryRepository.existsCategoriesBySlug(categoryRequest.getSlug())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Category slug have existed!");
        }

        Category category = modelMapper.map(categoryRequest, Category.class);

        // code convert name to slug
        String slug = Utils.removeVietnameseAccents(category.getName());

        category.setSlug(slug);

        Category savedCategory = categoryRepository.save(category);

        return modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public ClassResponse getAll(int pageNo, int pageSize, String sortBy, String sortDir, String keyword) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Category> categories = null;
        if(keyword != null && !keyword.isEmpty()){
            categories = categoryRepository.search(keyword, pageable);
        }else{
            categories = categoryRepository.findAll(pageable);
        }

        List<Category> listCategories = categories.getContent();

        List<CategoryDto> content = listCategories.stream().map(category -> modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());

        return ClassResponse.convertToClassResponse(categories, content);
    }

    @Override
    public CategoryDto get(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public CategoryDto update(Integer categoryId, CategoryDto categoryRequest) {
        Category categoryInDB = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        Category category = categoryRepository.findByNameOrSlug(categoryRequest.getName(), categoryRequest.getSlug());
        if(!Objects.equals(category.getId(), categoryInDB.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Category name/slug have been existed!");
        }

        String name = categoryRequest.getName();
        categoryInDB.setName(name);
        categoryInDB.setSlug(Utils.removeVietnameseAccents(name));
        Category updatedCategory = categoryRepository.save(categoryInDB);
        return modelMapper.map(updatedCategory, CategoryDto.class);
    }

    @Override
    public String delete(Integer categoryId) {
        Category categoryInDB = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        categoryRepository.delete(categoryInDB);
        return "Delete Category Successfully!";
    }
}
