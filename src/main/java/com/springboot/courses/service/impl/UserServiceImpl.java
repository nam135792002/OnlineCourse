package com.springboot.courses.service.impl;

import com.springboot.courses.entity.Role;
import com.springboot.courses.entity.User;
import com.springboot.courses.exception.BlogApiException;
import com.springboot.courses.exception.ResourceNotFoundException;
import com.springboot.courses.payload.user.UserRequest;
import com.springboot.courses.payload.ClassResponse;
import com.springboot.courses.payload.user.UserResponse;
import com.springboot.courses.repository.RoleRepository;
import com.springboot.courses.repository.UserRepository;
import com.springboot.courses.service.UserService;
import com.springboot.courses.utils.UploadFile;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Transactional
@Service
public class UserServiceImpl implements UserService {

    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private ModelMapper modelMapper;
    @Autowired private UploadFile uploadFile;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(UserRequest userRequest, MultipartFile img) {
        // get role-admin for user
        Role role = roleRepository.findByName("ROLE_ADMIN").get();

        User user = checkValid(userRequest, role, img);
        user.setEnabled(true);
        User savedUser = userRepository.save(user);

        return convertToDto(savedUser);
    }

    @Override
    public ClassResponse listAllUser(int pageNo, int pageSize, String sortBy, String sortDir, String keyword){
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<User> users = null;
        if(keyword != null && !keyword.isEmpty()){
            users = userRepository.search(keyword, pageable);
        }else{
            users = userRepository.findAll(pageable);
        }

        List<User> listUsers = users.getContent();

        List<UserResponse> content = listUsers.stream().map(this::convertToDto).toList();

        return ClassResponse.convertToClassResponse(users, content);
    }

    @Override
    public UserResponse get(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        return convertToDto(user);
    }

    @Override
    public UserResponse updateUser(UserRequest userRequest, Integer userId, MultipartFile img){
        // Get user in database
        User userInDB = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        // Change image avatar of user
        if(img != null){
            if(userInDB.getPhoto() != null){
                uploadFile.deleteImageInCloudinary(userInDB.getPhoto());
            }
            String url = uploadFile.uploadFileOnCloudinary(img);
            userInDB.setPhoto(url);
        }

        // Change password
        if(!userRequest.getPassword().equals("Unknown password")){
            userInDB.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        }

        userInDB.setFullName(userRequest.getFullName());
        userInDB.setEmail(userRequest.getEmail());
        userInDB.setPhoneNumber(userRequest.getPhoneNumber());
        userInDB.setEnabled(userRequest.isEnabled());

        User savedUser = userRepository.save(userInDB);
        return convertToDto(savedUser);
    }

    @Override
    public String delete(Integer userId) {
        // Get user in database
        User userInDB = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        // delete avatar in cloudinary
        if(userInDB.getPhoto() != null){
            System.out.println(userInDB.getPhoto());
            uploadFile.deleteImageInCloudinary(userInDB.getPhoto());
        }
        userRepository.delete(userInDB);
        return "Delete user successfully!";
    }

    @Override
    public UserResponse createCustomer(UserRequest userRequest, MultipartFile img, HttpServletRequest request) {
        Role role = roleRepository.findByName("ROLE_CUSTOMER").get();
        User user = checkValid(userRequest, role, img);
        String randomCode = RandomString.make(64);
        user.setVerificationCode(randomCode);
        sendEmail(request, user);
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserResponse.class);
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

    private void sendEmail(HttpServletRequest request, User user){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("tech.courses.895@gmail.com");
        mailSender.setPassword("giug qtcr yjag occm");
        mailSender.setDefaultEncoding("utf-8");

        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth","true");
        properties.setProperty("mail.smtp.starttls.enable","true");
        mailSender.setJavaMailProperties(properties);

        String toAddress = user.getEmail();
        String subject = "Please verify your registration to continue";
        String content = "<div style=\"font-size: 16px; letter-spacing: normal;\">Dear [[name]]," +
                "</div><div style=\"font-size: 16px; letter-spacing: normal;\"><i><br></i></div>" +
                "<div style=\"font-size: 16px; letter-spacing: normal;\">" +
                "<i>Click the link below to verify your registration:</i></div>" +
                "<div style=\"font-size: 16px; letter-spacing: normal;\"><i><br></i></div>" +
                "<a href=\"[[URL]]\" target=\"_self\" style=\"color: rgb(0, 123, 255); background-color: " +
                "transparent; font-size: 16px; letter-spacing: normal;\">VERIFY</a><div style=\"font-size: 16px;" +
                " letter-spacing: normal;\"><span style=\"font-size: 18px;\"><span style=\"font-size: 24px;\">" +
                "<span style=\"font-weight: bolder;\"><font color=\"#ff0000\"></font></span></span>" +
                "</span></div><div style=\"font-size: 16px; letter-spacing: normal;\"><br></div>" +
                "<div style=\"font-size: 16px; letter-spacing: normal;\">Thanks,</div>" +
                "<div style=\"font-size: 16px; letter-spacing: normal;\">The Tech Courses Team</div>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setFrom("tech.courses.895@gmail.com","Tech Courses");
            helper.setTo(toAddress);
            helper.setSubject(subject);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Send email failed!");
        }

        content = content.replace("[[name]]",user.getFullName());
        String siteURL = request.getRequestURL().toString();
        siteURL =  siteURL.replace(request.getServletPath(),"");
        String verifyURL = siteURL + "/api/users/verify?code=" + user.getVerificationCode();

        content = content.replace("[[URL]]",verifyURL);
        try {
            helper.setText(content,true);
        } catch (MessagingException e) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Send email failed!");
        }
        mailSender.send(message);
    }

    private UserResponse convertToDto(User user){
        UserResponse userResponse = new UserResponse();

        userResponse = modelMapper.map(user, UserResponse.class);
        userResponse.setRoleName(user.getRole().getName());
        return userResponse;
    }

    private User checkValid(UserRequest userRequest, Role role, MultipartFile img){
        // check exists email
        if (userRepository.existsUserByEmailAndRole(userRequest.getEmail(), role)){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Email has existed for this user");
        }

        // check exists phone number
        if (userRepository.existsUserByPhoneNumberAndRole(userRequest.getPhoneNumber(), role)){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Phone number has existed for this user");
        }

        //check exists username
        if(userRepository.existsUserByUsernameAndRole(userRequest.getUsername(), role)){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Username has existed for this user");
        }

        // convert user dto to user entity
        User user = modelMapper.map(userRequest, User.class);

        // upload image on the cloudinary
        if(img != null){
            String url = uploadFile.uploadFileOnCloudinary(img);
            user.setPhoto(url);
        }

        // create time for user
        user.setCreatedTime(new Date());

        user.setRole(role);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return user;
    }
}
