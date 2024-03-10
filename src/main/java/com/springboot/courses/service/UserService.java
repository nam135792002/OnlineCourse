package com.springboot.courses.service;

import com.springboot.courses.payload.UserDto;
import com.springboot.courses.payload.ClassResponse;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    UserDto createUser(UserDto userDto, MultipartFile img);
    ClassResponse listAllUser(int pageNo, int pageSize, String sortBy, String sortDir, String keyword);

    UserDto get(Integer userId);

    UserDto updateUser(UserDto userDto, Integer userId, MultipartFile img);

    String delete(Integer userId);

}
