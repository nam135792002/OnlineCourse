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
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

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

    private UserResponse convertToDto(User user){
        UserResponse userResponse = new UserResponse();

        userResponse = modelMapper.map(user, UserResponse.class);
        userResponse.setRoleName(user.getRole().getName());
        return userResponse;
    }

    private User checkValid(UserRequest userRequest, Role role, MultipartFile img){
        // check exists email
        if (userRepository.existsUserByEmail(userRequest.getEmail())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Email has existed for this user");
        }

        // check exists phone number
        if (userRepository.existsUserByPhoneNumber(userRequest.getPhoneNumber())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Phone number has existed for this user");
        }

        //check exists username
        if(userRepository.existsUserByUsername(userRequest.getUsername())){
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
