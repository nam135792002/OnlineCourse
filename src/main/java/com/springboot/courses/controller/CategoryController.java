package com.springboot.courses.controller;

import com.springboot.courses.payload.CategoryDto;
import com.springboot.courses.payload.ClassResponse;
import com.springboot.courses.service.CategoryService;
import com.springboot.courses.utils.AppConstants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired private CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<CategoryDto> add(@RequestBody @Valid CategoryDto categoryRequest){
        CategoryDto savedCategory = categoryService.createCategory(categoryRequest);
        URI uri = URI.create("/api/categories/" + savedCategory.getId());

        return ResponseEntity.created(uri).body(savedCategory);
    }

    @GetMapping("/list-all")
    public ResponseEntity<ClassResponse> listAllCategories(
        @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
        @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
        @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
        @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir,
        @RequestParam(value = "keyword", required = false) String keyword
    ){
        ClassResponse classResponse = categoryService.getAll(pageNo, pageSize, sortBy, sortDir, keyword);
        if(classResponse.getContent().isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(classResponse);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<CategoryDto> get(@PathVariable(value = "id") Integer categoryId){
        return ResponseEntity.ok(categoryService.get(categoryId));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryDto> updatedCategory(@PathVariable(value = "id") Integer categoryId,
                                                       @RequestBody @Valid CategoryDto categoryRequest){
        return ResponseEntity.ok(categoryService.update(categoryId, categoryRequest));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable(value = "id") Integer categoryId){
        return ResponseEntity.ok(categoryService.delete(categoryId));
    }
}
