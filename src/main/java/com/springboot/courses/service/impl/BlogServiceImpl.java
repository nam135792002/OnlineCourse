package com.springboot.courses.service.impl;

import com.springboot.courses.entity.Blog;
import com.springboot.courses.entity.User;
import com.springboot.courses.exception.ResourceNotFoundException;
import com.springboot.courses.payload.blog.BlogRequest;
import com.springboot.courses.payload.blog.BlogResponse;
import com.springboot.courses.repository.BlogRepository;
import com.springboot.courses.repository.UserRepository;
import com.springboot.courses.service.BlogService;
import com.springboot.courses.utils.UploadFile;
import com.springboot.courses.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired private BlogRepository blogRepository;
    @Autowired private ModelMapper modelMapper;
    @Autowired private UserRepository userRepository;
    @Autowired private UploadFile uploadFile;

    @Override
    public BlogResponse save(BlogRequest blogRequest, MultipartFile img) {
        User user = userRepository.findById(blogRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", blogRequest.getUserId()));
        
        Blog blog = modelMapper.map(blogRequest, Blog.class);
        blog.setUser(user);
        blog.setCreatedAt(new Date());
        
        String thumbnail = uploadFile.uploadFileOnCloudinary(img);
        blog.setThumbnail(thumbnail);

        Blog savedBlog = blogRepository.save(blog);

        return convertToResponse(savedBlog);
    }

    @Override
    public BlogResponse get(Integer blogId) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new ResourceNotFoundException("Blog", "id", blogId));

        return convertToResponse(blog);
    }

    @Override
    public BlogResponse update(Integer blogId, BlogRequest blogRequest, MultipartFile img) {
        Blog blogInDB = blogRepository.findById(blogId)
                .orElseThrow(() -> new ResourceNotFoundException("Blog", "id", blogId));

        if(img != null){
            uploadFile.deleteImageInCloudinary(blogInDB.getThumbnail());

            String thumbnail = uploadFile.uploadFileOnCloudinary(img);
            blogInDB.setThumbnail(thumbnail);
        }
        blogInDB.setTitle(blogRequest.getTitle());
        blogInDB.setContent(blogRequest.getContent());
        blogInDB.setDescription(blogRequest.getDescription());

        Blog savedBlog = blogRepository.save(blogInDB);
        return convertToResponse(savedBlog);
    }

    @Override
    public String delete(Integer blogId) {
        Blog blogInDB = blogRepository.findById(blogId)
                .orElseThrow(() -> new ResourceNotFoundException("Blog", "id", blogId));

        blogRepository.delete(blogInDB);

        return "SUCCESS";
    }

    @Override
    public List<BlogResponse> getAll() {
        List<Blog> listBlogs = blogRepository.findAll();

        return listBlogs.stream().map(this::convertToResponse).toList();
    }

    @Override
    public List<BlogResponse> getAllByUser(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        List<Blog> listBlogs = blogRepository.findAllByUser(user);
        return listBlogs.stream().map(this::convertToResponse).toList();
    }

    @Override
    public List<BlogResponse> search(String keyword) {
        List<Blog> listBlogs = blogRepository.search(keyword);
        return listBlogs.stream().map(this::convertToResponse).toList();
    }

    @Override
    public String view(Integer blogId) {
        Blog blogInDB = blogRepository.findById(blogId)
                .orElseThrow(() -> new ResourceNotFoundException("Blog", "id", blogId));
        int view = blogInDB.getView();

        blogInDB.setView(++view);

        blogRepository.save(blogInDB);

        return "SUCCESS";
    }

    private BlogResponse convertToResponse(Blog savedBlog) {
        BlogResponse response = modelMapper.map(savedBlog, BlogResponse.class);
        Instant now = Instant.now();
        response.setCreatedAtFormat(Utils.formatDuration(Duration.between(savedBlog.getCreatedAt().toInstant(), now)));
        response.setUsername(savedBlog.getUser().getUsername());
        response.setAvatarUser(savedBlog.getUser().getPhoto());

        return response;
    }
}
