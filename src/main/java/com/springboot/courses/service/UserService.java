package com.springboot.courses.service;

import com.springboot.courses.payload.user.UserRequest;
import com.springboot.courses.payload.ClassResponse;
import com.springboot.courses.payload.user.UserResponse;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    UserResponse createUser(UserRequest userRequest, MultipartFile img);
    ClassResponse listAllUser(int pageNo, int pageSize, String sortBy, String sortDir, String keyword);
    UserResponse get(Integer userId);
    UserResponse updateUser(UserRequest userRequest, Integer userId, MultipartFile img);
    String delete(Integer userId);
    String changePasswordInCustomer(String password, String email);
    UserResponse updateInfoCustomer(String fullName, MultipartFile img, String email);
}
