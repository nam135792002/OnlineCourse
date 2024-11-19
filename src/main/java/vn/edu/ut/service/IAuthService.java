package vn.edu.ut.service;

import vn.edu.ut.payload.MessageNotice;
import vn.edu.ut.payload.auth.JWTAuthResponse;
import vn.edu.ut.payload.auth.LoginDto;
import vn.edu.ut.payload.user.UserRequest;
import vn.edu.ut.payload.user.UserResponse;

public interface IAuthService {
    JWTAuthResponse login(LoginDto loginDto);

    UserResponse register(UserRequest userRequest);

    MessageNotice verify(String verification, String email);

    void requestPassword(String email);

    UserResponse findByResetPasswordToken(String token);

    void updatePassword(String token, String password);
}
