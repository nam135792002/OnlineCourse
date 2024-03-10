package com.springboot.courses.service.impl;

import com.springboot.courses.entity.Category;
import com.springboot.courses.entity.User;
import com.springboot.courses.exception.BlogApiException;
import com.springboot.courses.exception.ResourceNotFoundException;
import com.springboot.courses.payload.CategoryDto;
import com.springboot.courses.payload.ClassResponse;
import com.springboot.courses.payload.UserDto;
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
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired private CategoryRepository categoryRepository;
    @Autowired private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {

        if(categoryRepository.existsCategoriesByName(categoryDto.getName())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Category name have existed!");
        }

        Category category = modelMapper.map(categoryDto, Category.class);

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

        ClassResponse classResponse = new ClassResponse();
        classResponse.setContent(content);
        classResponse.setPageNo(categories.getNumber());
        classResponse.setPageSize(categories.getSize());
        classResponse.setTotalElements(categories.getTotalElements());
        classResponse.setTotalPage(categories.getTotalPages());
        classResponse.setLast(categories.isLast());

        return classResponse;
    }

    @Override
    public CategoryDto get(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public CategoryDto update(Integer categoryId, CategoryDto categoryDto) {
        Category categoryInDB = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        String name = categoryDto.getName();
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
