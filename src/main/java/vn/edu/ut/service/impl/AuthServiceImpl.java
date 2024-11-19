package vn.edu.ut.service.impl;

import vn.edu.ut.constant.AppConstants;
import vn.edu.ut.entity.Role;
import vn.edu.ut.entity.User;
import vn.edu.ut.enums.ErrorCode;
import vn.edu.ut.exception.AppApiException;
import vn.edu.ut.exception.AppException;
import vn.edu.ut.exception.ResourceNotFoundException;
import vn.edu.ut.payload.MessageNotice;
import vn.edu.ut.payload.auth.JWTAuthResponse;
import vn.edu.ut.payload.auth.LoginDto;
import vn.edu.ut.payload.user.UserRequest;
import vn.edu.ut.payload.user.UserResponse;
import vn.edu.ut.repository.RoleRepository;
import vn.edu.ut.repository.UserRepository;
import vn.edu.ut.security.JwtFactory;
import vn.edu.ut.service.IAuthService;
import vn.edu.ut.utils.Utils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.utility.RandomString;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Transactional
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtFactory jwtFactory;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public JWTAuthResponse login(LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtFactory.generateToken(authentication);

            return new JWTAuthResponse(token);
        } catch (BadCredentialsException e) {
            throw new AppApiException(ErrorCode.EMAIL_OR_PASSWORD_IS_INVALID);
        } catch (DisabledException d) {
            throw new AppApiException(ErrorCode.ACCOUNT_NON_ACTIVE);
        }
    }

    @Override
    public UserResponse register(UserRequest userRequest) {
        Role role = roleRepository.findByName("ROLE_CUSTOMER").get();
        String randomCode = RandomString.make(64);

        User user = modelMapper.map(userRequest, User.class);

        user.setPhoto("https://res.cloudinary.com/dqnoopa0x/image/upload/v1712482876/ooozzfj7t7p1zokgonni.jpg");
        user.setVerificationCode(randomCode);
        user.setEnabled(false);
        user.setRole(role);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        String verifyURL = AppConstants.LOCALHOST + "/auth/verify?code=" + user.getVerificationCode() + "&email=" + user.getEmail();

        Utils.sendEmail(verifyURL, AppConstants.SUBJECT_REGISTER, AppConstants.CONTENT_REGISTER, user);
        User savedUser = userRepository.save(user);

        UserResponse userResponse = modelMapper.map(savedUser, UserResponse.class);
        userResponse.setRoleName(role.getName());

        return userResponse;
    }

    @Override
    public void requestPassword(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

        String token = RandomString.make(30);
        user.setResetPasswordToken(token);
        String url = AppConstants.LOCALHOST + "/auth/request-password?token=" + token;
        Utils.sendEmail(url, AppConstants.SUBJECT_RESET, AppConstants.CONTENT_RESET, user);
        userRepository.save(user);
    }

    @Override
    public MessageNotice verify(String verification, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "email", email));

        if (user.getVerificationCode() == null) {
            return new MessageNotice(false, "Tài khoản đã được kích hoạt");
        } else {
            if (user.getVerificationCode().equals(verification)) {
                userRepository.enable(user.getId());
                return new MessageNotice(true, "Tài khoản kích hoạt thành công");
            } else {
                return new MessageNotice(false, "Sai mã kích hoạt");
            }
        }
    }

    @Override
    public UserResponse findByResetPasswordToken(String token) {
        User user = userRepository.findUserByResetPasswordToken(token);
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        userResponse.setRoleName(user.getRole().getName());
        return userResponse;
    }

    @Override
    public void updatePassword(String token, String password) {
        User user = userRepository.findUserByResetPasswordToken(token);
        if (user == null) {
            throw new AppException(HttpStatus.BAD_REQUEST, "Invalid token");
        }

        user.setPassword(passwordEncoder.encode(password));
        user.setResetPasswordToken(null);

        userRepository.save(user);
    }
}
