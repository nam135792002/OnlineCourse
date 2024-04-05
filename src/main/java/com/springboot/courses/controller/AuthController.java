package com.springboot.courses.controller;

import com.springboot.courses.payload.auth.JWTAuthResponse;
import com.springboot.courses.payload.auth.LoginDto;
import com.springboot.courses.payload.user.UserRequest;
import com.springboot.courses.payload.user.UserResponse;
import com.springboot.courses.payload.validate.CheckValidateCustomerRequest;
import com.springboot.courses.payload.validate.CheckValidateCustomerResponse;
import com.springboot.courses.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private AuthService authService;

    @PostMapping ("/login")
    public ResponseEntity<JWTAuthResponse> login(@RequestBody @Valid LoginDto loginDto){
        JWTAuthResponse jwtAuthResponse = authService.login(loginDto);
        return ResponseEntity.ok(jwtAuthResponse);
    }

    @PostMapping ("/validate")
    public ResponseEntity<?> validateFormSignUp(@RequestBody @Valid CheckValidateCustomerRequest request){
        List<CheckValidateCustomerResponse> listResponses = authService.checkInfoOfCustomer(request);
        if (listResponses.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listResponses);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registration(@RequestPart(value = "user") @Valid UserRequest userRequest){
        return new ResponseEntity<>(authService.register(userRequest), HttpStatus.CREATED);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verify(@RequestParam(value = "code") String verification){
        boolean verify = authService.verify(verification);
        URI uri = URI.create("/api/users/register/" + (verify?"success":"failed"));
        if(verify){
            return ResponseEntity.ok().body(uri);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(uri);
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> processRequestFormResetPassword(@RequestParam(value = "email") String email,
                                                                  HttpServletRequest request){
        authService.updateResetPasswordToken(email, request);
        return ResponseEntity.ok("We have sent a reset password link to your email. Please check!");
    }

    @PostMapping ("/handle/reset-password")
    public ResponseEntity<String> showResetForm(@RequestParam(value = "token") String token){
        UserResponse response = authService.findByResetPasswordToken(token);
        if(response != null){
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid token");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> updatePasswordInForgotForm(@RequestParam(value = "token") String token,
                                                        @RequestParam(value = "password") String newPassword){
        authService.updatePassword(token, newPassword);
        return ResponseEntity.ok("You have successfully changed your password.");
    }

}
