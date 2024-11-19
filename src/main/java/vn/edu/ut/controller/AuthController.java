package vn.edu.ut.controller;

import vn.edu.ut.payload.auth.JWTAuthResponse;
import vn.edu.ut.payload.auth.LoginDto;
import vn.edu.ut.payload.user.UserRequest;
import vn.edu.ut.payload.user.UserResponse;
import vn.edu.ut.service.IAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(
        name = "Authentication Controller",
        description = "REST APIs related to Authentication and Authorization"
)
public class AuthController {

    private final IAuthService iAuthService;

    @Operation(
            summary = "Login REST API",
            description = "This REST API is used to authenticate a user and return a JWT token"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK - User authenticated successfully"
    )
    @PostMapping("/token")
    public ResponseEntity<JWTAuthResponse> generateToken(@RequestBody @Valid LoginDto loginDto) {
        JWTAuthResponse jwtAuthResponse = iAuthService.login(loginDto);
        return ResponseEntity.ok(jwtAuthResponse);
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
    public ResponseEntity<?> registration(@RequestPart(value = "user") @Valid UserRequest userRequest) {
        return new ResponseEntity<>(iAuthService.register(userRequest), HttpStatus.CREATED);
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
                                    @RequestParam(value = "email") String email) {
        return ResponseEntity.ok(iAuthService.verify(verification, email));
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
    public ResponseEntity<String> processRequestFormResetPassword(@RequestParam(value = "email") String email) {
        iAuthService.requestPassword(email);
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
    @PostMapping("/handle/reset-password")
    public ResponseEntity<String> showResetForm(@RequestParam(value = "token") String token) {
        UserResponse response = iAuthService.findByResetPasswordToken(token);
        if (response != null) {
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
                                                        @RequestParam(value = "password") String newPassword) {
        iAuthService.updatePassword(token, newPassword);
        return ResponseEntity.ok("Bạn đã thay đổi mật khẩu thành công.");
    }

}
