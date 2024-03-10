package com.springboot.courses.controller;

import com.springboot.courses.payload.UserDto;
import com.springboot.courses.payload.ClassResponse;
import com.springboot.courses.service.UserService;
import com.springboot.courses.utils.AppConstants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired private UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestPart(value = "userDto") @Valid UserDto userDto,
                                             @RequestParam(value = "img", required = false) MultipartFile image){
        UserDto savedUser = userService.createUser(userDto, image);
        URI uri = URI.create("/api/users/" + savedUser.getId());
        return ResponseEntity.created(uri).body(savedUser);
    }

    @GetMapping
    public ResponseEntity<?> listAllUsers(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir,
            @RequestParam(value = "keyword", required = false) String keyword
    ){
        ClassResponse classResponse = userService.listAllUser(pageNo, pageSize, sortBy, sortDir, keyword);
        if(classResponse.getContent().isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(classResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable(value = "id") Integer userId){
        return ResponseEntity.ok(userService.get(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@RequestPart(value = "userDto") @Valid UserDto userDto,
                                              @PathVariable(value = "id") Integer userId,
                                              @RequestParam(value = "img", required = false) MultipartFile img) {
        return ResponseEntity.ok(userService.updateUser(userDto, userId, img));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(value = "id") Integer userId){
        return ResponseEntity.ok(userService.delete(userId));
    }
}
