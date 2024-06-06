package com.springboot.courses.controller;

import com.springboot.courses.payload.CategoryDto;
import com.springboot.courses.payload.ClassResponse;
import com.springboot.courses.service.CategoryService;
import com.springboot.courses.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/categories")
@Tag(
        name = "CRUD REST APIs for Category Resource"
)
public class CategoryController {

    @Autowired private CategoryService categoryService;

    @Operation(
            summary = "Create category REST API",
            description = "Create category REST API is used to save category into database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PostMapping("/create")
    public ResponseEntity<CategoryDto> add(@RequestBody @Valid CategoryDto categoryRequest){
        CategoryDto savedCategory = categoryService.createCategory(categoryRequest);
        URI uri = URI.create("/api/categories/" + savedCategory.getId());

        return ResponseEntity.created(uri).body(savedCategory);
    }

    @Operation(
            summary = "Get all category REST API",
            description = "This REST API is used to retrieve a paginated list of all categories from the database. " +
                    "The list can be sorted and filtered using query parameters."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK - Successful retrieval of category list"
    )
    @ApiResponse(
            responseCode = "204",
            description = "Http Status 204 No Content - No categories found"
    )
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

    @Operation(
            summary = "Get category by ID REST API",
            description = "Get category by ID REST API is used to get single category from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Http Status 404 NOT FOUND"
    )
    @GetMapping("/get/{id}")
    public ResponseEntity<CategoryDto> get(@PathVariable(value = "id") Integer categoryId){
        return ResponseEntity.ok(categoryService.get(categoryId));
    }

    @Operation(
            summary = "Update category by ID REST API",
            description = "Update category REST API is used to update a particular category into the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Http Status 404 NOT FOUND"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryDto> updatedCategory(@PathVariable(value = "id") Integer categoryId,
                                                       @RequestBody @Valid CategoryDto categoryRequest){
        return ResponseEntity.ok(categoryService.update(categoryId, categoryRequest));
    }

    @Operation(
            summary = "Delete category REST API",
            description = "Delete category REST API is used to delete a particular category from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Http Status 404 NOT FOUND"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable(value = "id") Integer categoryId){
        return ResponseEntity.ok(categoryService.delete(categoryId));
    }
}
