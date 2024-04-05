package com.springboot.courses.service;

import com.springboot.courses.payload.auth.JWTAuthResponse;
import com.springboot.courses.payload.auth.LoginDto;
import com.springboot.courses.payload.user.UserRequest;
import com.springboot.courses.payload.user.UserResponse;
import com.springboot.courses.payload.validate.CheckValidateCustomerRequest;
import com.springboot.courses.payload.validate.CheckValidateCustomerResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AuthService {
    JWTAuthResponse login(LoginDto loginDto);
    List<CheckValidateCustomerResponse> checkInfoOfCustomer(CheckValidateCustomerRequest request);
    UserResponse register(UserRequest userRequest);
    boolean verify(String verification);
    void updateResetPasswordToken(String email, HttpServletRequest request);
    UserResponse findByResetPasswordToken(String token);
    void updatePassword(String token, String password);
}
