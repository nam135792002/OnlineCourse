package com.springboot.courses.controller;

import com.springboot.courses.payload.auth.JWTAuthResponse;
import com.springboot.courses.payload.auth.LoginDto;
import com.springboot.courses.payload.user.UserRequest;
import com.springboot.courses.payload.user.UserResponse;
import com.springboot.courses.payload.validate.CheckValidateCustomerRequest;
import com.springboot.courses.payload.validate.CheckValidateCustomerResponse;
import com.springboot.courses.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@Tag(
        name = "Authentication Controller",
        description = "REST APIs related to Authentication and Authorization"
)
public class AuthController {

    @Autowired private AuthService authService;

    @Operation(
            summary = "Login REST API",
            description = "This REST API is used to authenticate a user and return a JWT token"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK - User authenticated successfully"
    )
    @PostMapping ("/login")
    public ResponseEntity<JWTAuthResponse> login(@RequestBody @Valid LoginDto loginDto){
        JWTAuthResponse jwtAuthResponse = authService.login(loginDto);
        return ResponseEntity.ok(jwtAuthResponse);
    }

    @Operation(
            summary = "Validate Signup Form REST API",
            description = "This REST API is used to validate the signup form including email, username, phone number"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK - Validation successful"
    )
    @ApiResponse(
            responseCode = "204",
            description = "Http Status 204 No Content - No validation issues found"
    )
    @PostMapping ("/validate")
    public ResponseEntity<?> validateFormSignUp(@RequestBody @Valid CheckValidateCustomerRequest request){
        List<CheckValidateCustomerResponse> listResponses = authService.checkInfoOfCustomer(request);
        if (listResponses.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listResponses);
    }

    @Operation(
            summary = "Register User REST API",
            description = "This REST API is used to register a new user"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 Created - User registered successfully"
    )
    @PostMapping("/register")
    public ResponseEntity<?> registration(@RequestPart(value = "user") @Valid UserRequest userRequest){
        return new ResponseEntity<>(authService.register(userRequest), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Verify Email REST API",
            description = "This REST API is used to verify a user's email address"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK - Email verified successfully"
    )
    @PostMapping("/verify")
    public ResponseEntity<?> verify(@RequestParam(value = "code") String verification,
                                    @RequestParam(value = "email") String email){
        return ResponseEntity.ok(authService.verify(verification, email));
    }

    @Operation(
            summary = "Forgot Password REST API",
            description = "This REST API is used to request a password reset link"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK - Password reset link sent"
    )
    @PostMapping("/forgot-password")
    public ResponseEntity<String> processRequestFormResetPassword(@RequestParam(value = "email") String email){
        authService.requestPassword(email);
        return ResponseEntity.ok("Chúng tôi đã gửi một liên kết đặt lại mật khẩu đến địa chỉ email của bạn. Vui lòng kiểm tra!");
    }

    @Operation(
            summary = "Show Reset Password Form REST API",
            description = "This REST API is used to show the reset password form using a token"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK - Token is valid"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Http Status 404 Not Found - Invalid token"
    )
    @PostMapping ("/handle/reset-password")
    public ResponseEntity<String> showResetForm(@RequestParam(value = "token") String token){
        UserResponse response = authService.findByResetPasswordToken(token);
        if(response != null){
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mã không hợp lệ");
    }

    @Operation(
            summary = "Reset Password REST API",
            description = "This REST API is used to reset the password using a token"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK - Password reset successfully"
    )
    @PostMapping("/reset-password")
    public ResponseEntity<?> updatePasswordInForgotForm(@RequestParam(value = "token") String token,
                                                        @RequestParam(value = "password") String newPassword){
        authService.updatePassword(token, newPassword);
        return ResponseEntity.ok("Bạn đã thay đổi mật khẩu thành công.");
    }

}
