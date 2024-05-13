package com.springboot.courses.service;

import com.springboot.courses.payload.blog.BlogRequest;
import com.springboot.courses.payload.blog.BlogResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BlogService {
    BlogResponse save(BlogRequest blogRequest, MultipartFile img);
    BlogResponse get(Integer blogId);
    BlogResponse update(Integer blogId, BlogRequest blogRequest, MultipartFile img);
    String delete(Integer blogId);
    List<BlogResponse> getAll();
    List<BlogResponse> getAllByUser(Integer userId);
    List<BlogResponse> search(String keyword);
    String view(Integer blogId);
}
