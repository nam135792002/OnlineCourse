package com.springboot.courses.service.impl;

import com.springboot.courses.entity.MessageErrorCustomer;
import com.springboot.courses.entity.Role;
import com.springboot.courses.entity.User;
import com.springboot.courses.exception.BlogApiException;
import com.springboot.courses.exception.ResourceNotFoundException;
import com.springboot.courses.payload.auth.JWTAuthResponse;
import com.springboot.courses.payload.auth.LoginDto;
import com.springboot.courses.payload.user.UserRequest;
import com.springboot.courses.payload.user.UserResponse;
import com.springboot.courses.payload.validate.CheckValidateCustomerRequest;
import com.springboot.courses.payload.validate.CheckValidateCustomerResponse;
import com.springboot.courses.repository.RoleRepository;
import com.springboot.courses.repository.UserRepository;
import com.springboot.courses.security.JwtTokenProvider;
import com.springboot.courses.service.AuthService;
import com.springboot.courses.utils.AppConstants;
import com.springboot.courses.utils.UploadFile;
import com.springboot.courses.utils.Utils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Transactional
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private JwtTokenProvider jwtTokenProvider;
    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private ModelMapper modelMapper;
    @Autowired private UploadFile uploadFile;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public JWTAuthResponse login(LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);

        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

        jwtAuthResponse.setFullName(user.getFullName());
        jwtAuthResponse.setUsername(user.getUsername());
        jwtAuthResponse.setThumbnail(user.getPhoto());

        return jwtAuthResponse;
    }

    @Override
    public List<CheckValidateCustomerResponse> checkInfoOfCustomer(CheckValidateCustomerRequest request) {
        List<CheckValidateCustomerResponse> listResponses = new ArrayList<>();
        if(userRepository.existsUserByEmail(request.getEmail())){
            listResponses.add(new CheckValidateCustomerResponse(MessageErrorCustomer.EMAIL.noteMessageError(), MessageErrorCustomer.EMAIL));
        }
        if(userRepository.existsUserByUsername(request.getUsername())){
            listResponses.add(new CheckValidateCustomerResponse(MessageErrorCustomer.USERNAME.noteMessageError(), MessageErrorCustomer.USERNAME));
        }
        if (userRepository.existsUserByPhoneNumber(request.getPhoneNumber())){
            listResponses.add(new CheckValidateCustomerResponse(MessageErrorCustomer.PHONE_NUMBER.noteMessageError(), MessageErrorCustomer.PHONE_NUMBER));
        }

        return listResponses;
    }

    @Override
    public UserResponse register(UserRequest userRequest, MultipartFile img) {
        Role role = roleRepository.findByName("ROLE_CUSTOMER").get();
        String randomCode = RandomString.make(64);

        User user = modelMapper.map(userRequest, User.class);

        if(img != null){
            String url = uploadFile.uploadFileOnCloudinary(img);
            user.setPhoto(url);
        }

        user.setVerificationCode(randomCode);
        user.setCreatedTime(new Date());
        user.setRole(role);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        String verifyURL = "http://localhost:5173/api/auth/verify?code=" + user.getVerificationCode();

        Utils.sendEmail(verifyURL, AppConstants.SUBJECT_REGISTER, AppConstants.CONTENT_REGISTER, user);
        User savedUser = userRepository.save(user);

        UserResponse userResponse = modelMapper.map(savedUser, UserResponse.class);
        userResponse.setRoleName(role.getName());

        return userResponse;
    }

    @Override
    public void updateResetPasswordToken(String email, HttpServletRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

        String token = RandomString.make(30);
        user.setResetPasswordToken(token);
        String url = "http://localhost:5173/api/auth/reset-password?token=" + token;
        Utils.sendEmail(url, AppConstants.SUBJECT_RESET, AppConstants.CONTENT_RESET, user);
        userRepository.save(user);
    }

    @Override
    public boolean verify(String verification) {
        User user = userRepository.findUserByVerificationCode(verification);
        if(user == null || user.isEnabled()){
            return false;
        }else{
            userRepository.enable(user.getId());
            return true;
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
        if(user == null){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Invalid token");
        }

        user.setPassword(passwordEncoder.encode(password));
        user.setResetPasswordToken(null);

        userRepository.save(user);
    }
}
