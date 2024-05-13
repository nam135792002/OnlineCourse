package com.springboot.courses.controller;

import com.springboot.courses.payload.blog.BlogRequest;
import com.springboot.courses.payload.blog.BlogResponse;
import com.springboot.courses.service.BlogService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/blog")
public class BlogController {

    @Autowired private BlogService blogService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestPart(value = "blog") @Valid BlogRequest blogRequest,
                                  @RequestParam(value = "img") MultipartFile img){
        return new ResponseEntity<>(blogService.save(blogRequest, img), HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public  ResponseEntity<?> get(@PathVariable(value = "id") Integer blogId){
        return ResponseEntity.ok(blogService.get(blogId));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") Integer blogId,
                                    @RequestPart(value = "blog") @Valid BlogRequest blogRequest,
                                    @RequestParam(value = "img", required = false) MultipartFile img) {
        return ResponseEntity.ok(blogService.update(blogId, blogRequest, img));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Integer blogId){
        return ResponseEntity.ok(blogService.delete(blogId));
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> listAll(){
        List<BlogResponse> listBlogs = blogService.getAll();
        if(listBlogs.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listBlogs);
    }

    @GetMapping("/get-all/user/{id}")
    public ResponseEntity<?> listAllByUser(@PathVariable(value = "id") Integer userId){
        List<BlogResponse> listBlogs = blogService.getAllByUser(userId);
        if(listBlogs.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(listBlogs);
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(value = "keyword") String keyword){
        List<BlogResponse> listBlogs = blogService.search(keyword);
        if(listBlogs.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(listBlogs);
    }

    @PutMapping("/update/view/{id}")
    public ResponseEntity<?> view(@PathVariable(value = "id") Integer blogId){
        return ResponseEntity.ok(blogService.view(blogId));
    }
}
