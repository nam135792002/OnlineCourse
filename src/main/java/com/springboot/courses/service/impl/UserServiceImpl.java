package com.springboot.courses.service.impl;

import com.springboot.courses.entity.Role;
import com.springboot.courses.entity.User;
import com.springboot.courses.exception.BlogApiException;
import com.springboot.courses.exception.ResourceNotFoundException;
import com.springboot.courses.payload.UserDto;
import com.springboot.courses.payload.ClassResponse;
import com.springboot.courses.repository.RoleRepository;
import com.springboot.courses.repository.UserRepository;
import com.springboot.courses.service.UserService;
import com.springboot.courses.utils.UploadImage;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private ModelMapper modelMapper;
    @Autowired private UploadImage uploadImage;

    @Override
    public UserDto createUser(UserDto userDto, MultipartFile img) {
        // check exists email
        if (userRepository.existsUserByEmail(userDto.getEmail())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Email đã từng tồn tại");
        }

        // check exists phone number
        if (userRepository.existsUserByPhoneNumber(userDto.getPhoneNumber())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Số điện thoại đã từng tồn tại");
        }

        // upload image on the cloudinary
        if(img != null){
            String url = uploadImage.uploadImageOnCloudinary(img);
            userDto.setPhoto(url);
        }

        // convert user dto to user entity
        User user = modelMapper.map(userDto, User.class);

        // create time for user
        user.setCreatedTime(new Date());

        // get role-admin for user
        Role role = roleRepository.findByName("Admin").get();
        user.setRole(role);
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

        List<UserDto> content = listUsers.stream().map(this::convertToDto).toList();

        ClassResponse classResponse = new ClassResponse();
        classResponse.setContent(content);
        classResponse.setPageNo(users.getNumber());
        classResponse.setPageSize(users.getSize());
        classResponse.setTotalElements(users.getTotalElements());
        classResponse.setTotalPage(users.getTotalPages());
        classResponse.setLast(users.isLast());

        return classResponse;
    }

    @Override
    public UserDto get(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        return convertToDto(user);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId, MultipartFile img){
        // Get user in database
        User userInDB = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        // Change image avatar of user
        if(img != null){
            if(userInDB.getPhoto() != null){
                uploadImage.deleteImageInCloudinary(userInDB);
            }
            String url = uploadImage.uploadImageOnCloudinary(img);
            userInDB.setPhoto(url);
        }

        // Change password
        if(!userDto.getPassword().equals("Unknown password")){
            userInDB.setPassword(userDto.getPassword());
        }

        userInDB.setFullName(userDto.getFullName());
        userInDB.setEmail(userDto.getEmail());
        userInDB.setPhoneNumber(userDto.getPhoneNumber());
//        userInDB.setEnabled(userDto.isEnabled());

        User savedUser = userRepository.save(userInDB);
        return convertToDto(savedUser);
    }

    @Override
    public String delete(Integer userId) {
        // Get user in database
        User userInDB = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        // delete avatar in cloudinary
        if(userInDB.getPhoto() != null){
            uploadImage.deleteImageInCloudinary(userInDB);
        }

        userRepository.delete(userInDB);
        return "Delete user successfully!";
    }

    private UserDto convertToDto(User user){
        UserDto userDto = new UserDto();
        userDto = modelMapper.map(user, UserDto.class);
        userDto.setPassword(null);
        userDto.setRoleName(user.getRole().getName());
        return userDto;
    }
}