package com.springboot.courses.controller;

import com.springboot.courses.payload.blog.BlogRequest;
import com.springboot.courses.payload.blog.BlogResponse;
import com.springboot.courses.service.BlogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/blog")
@Tag(
        name = "Blog Controller",
        description = "REST APIs related to Blog Entity"
)
public class BlogController {

    @Autowired private BlogService blogService;

    @Operation(
            summary = "Save Blog REST API",
            description = "This REST API is used to save a new blog post"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED - Blog post created successfully"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestPart(value = "blog") @Valid BlogRequest blogRequest,
                                  @RequestParam(value = "img") MultipartFile img){
        return new ResponseEntity<>(blogService.save(blogRequest, img), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get Blog by Slug REST API",
            description = "This REST API is used to get a blog post by its slug"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK - Blog post retrieved successfully"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Http Status 404 Not Found - Blog post not found"
    )
    @GetMapping("/get/{slug}")
    public  ResponseEntity<?> get(@PathVariable(value = "slug") String slug){
        return ResponseEntity.ok(blogService.get(slug));
    }

    @Operation(
            summary = "Update Blog REST API",
            description = "This REST API is used to update an existing blog post by its ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK - Blog post updated successfully"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Http Status 404 Not Found - Blog post not found"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") Integer blogId,
                                    @RequestPart(value = "blog") @Valid BlogRequest blogRequest,
                                    @RequestParam(value = "img", required = false) MultipartFile img) {
        return ResponseEntity.ok(blogService.update(blogId, blogRequest, img));
    }

    @Operation(
            summary = "Delete Blog REST API",
            description = "This REST API is used to delete a blog post by its ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK - Blog post deleted successfully"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Http Status 404 Not Found - Blog post not found"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Integer blogId){
        return ResponseEntity.ok(blogService.delete(blogId));
    }

    @Operation(
            summary = "Get All Blogs REST API",
            description = "This REST API is used to get all blog posts"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK - Blogs retrieved successfully"
    )
    @ApiResponse(
            responseCode = "204",
            description = "Http Status 204 No Content - No blogs found"
    )
    @GetMapping("/get-all")
    public ResponseEntity<?> listAll(){
        List<BlogResponse> listBlogs = blogService.getAll();
        if(listBlogs.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listBlogs);
    }

    @Operation(
            summary = "Get Blogs by User ID REST API",
            description = "This REST API is used to get all blog posts by a specific user"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK - Blogs retrieved successfully"
    )
    @ApiResponse(
            responseCode = "204",
            description = "Http Status 204 No Content - No blogs found"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @GetMapping("/get-all/user/{id}")
    public ResponseEntity<?> listAllByUser(@PathVariable(value = "id") Integer userId){
        List<BlogResponse> listBlogs = blogService.getAllByUser(userId);
        if(listBlogs.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(listBlogs);
    }

    @Operation(
            summary = "Search Blogs REST API",
            description = "This REST API is used to search for blog posts by a keyword"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK - Blogs retrieved successfully"
    )
    @ApiResponse(
            responseCode = "204",
            description = "Http Status 204 No Content - No blogs found"
    )
    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(value = "keyword") String keyword){
        List<BlogResponse> listBlogs = blogService.search(keyword);
        if(listBlogs.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(listBlogs);
    }

    @Operation(
            summary = "Update Blog Views REST API",
            description = "This REST API is used to update the view count of a blog post by its ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK - Blog view count updated successfully"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Http Status 404 Not Found - Blog post not found"
    )
    @PutMapping("/update/view/{id}")
    public ResponseEntity<?> view(@PathVariable(value = "id") Integer blogId){
        return ResponseEntity.ok(blogService.view(blogId));
    }

    @Operation(
            summary = "Check Blog Author REST API",
            description = "This REST API is used to check if a user is the author of a blog post"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK - User is the author"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Http Status 404 Not Found - Blog post or user not found"
    )
    @GetMapping("/check-author")
    public ResponseEntity<?> checkAuthor(@RequestParam(value = "user") Integer userId,
                                         @RequestParam(value = "blog") Integer blogId){
        return ResponseEntity.ok(blogService.checkAuthorOfBlog(blogId, userId));
    }
}
