package vn.edu.ut.service;

import vn.edu.ut.payload.blog.BlogRequest;
import vn.edu.ut.payload.blog.BlogResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BlogService {
    BlogResponse save(BlogRequest blogRequest, MultipartFile img);
    BlogResponse get(String slug);
    BlogResponse update(Integer blogId, BlogRequest blogRequest, MultipartFile img);
    String delete(Integer blogId);
    List<BlogResponse> getAll();
    List<BlogResponse> getAllByUser(Integer userId);
    List<BlogResponse> search(String keyword);
    String view(Integer blogId);
    String checkAuthorOfBlog(Integer blogId, Integer userId);
}
