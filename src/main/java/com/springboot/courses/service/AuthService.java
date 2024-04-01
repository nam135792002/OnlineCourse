package com.springboot.courses.service;

import com.springboot.courses.payload.auth.LoginDto;

public interface AuthService {
    String login(LoginDto loginDto);

}
