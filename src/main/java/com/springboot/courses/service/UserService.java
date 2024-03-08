package com.springboot.courses.service;

import com.springboot.courses.payload.UserDto;

public interface UserService {
    UserDto createUser(UserDto userDto);
}
