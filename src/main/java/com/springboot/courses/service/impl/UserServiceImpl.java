package com.springboot.courses.service.impl;

import com.springboot.courses.entity.Role;
import com.springboot.courses.entity.User;
import com.springboot.courses.exception.BlogApiException;
import com.springboot.courses.payload.UserDto;
import com.springboot.courses.repository.RoleRepository;
import com.springboot.courses.repository.UserRepository;
import com.springboot.courses.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        // check exists email
        if (userRepository.existsUserByEmail(userDto.getEmail())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Email đã từng tồn tại");
        }

        // check exists phone number
        if (userRepository.existsUserByPhoneNumber(userDto.getPhoneNumber())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Số điện thoại đã từng tồn tại");
        }

        // convert user dto to user entity
        User user = modelMapper.map(userDto, User.class);

        // create time for user
        user.setCreatedTime(new Date());

        // get role-adin for user
        Role role = roleRepository.findByName("Admin").get();
        user.setRole(role);
        user.setEnabled(true);

        User savedUser = userRepository.save(user);

        return modelMapper.map(savedUser, UserDto.class);
    }
}
