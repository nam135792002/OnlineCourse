package com.springboot.courses.controller;

import com.springboot.courses.payload.UserRequest;
import com.springboot.courses.payload.ClassResponse;
import com.springboot.courses.payload.UserResponse;
import com.springboot.courses.service.UserService;
import com.springboot.courses.utils.AppConstants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired private UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestPart(value = "userDto") @Valid UserRequest userRequest,
                                                   @RequestParam(value = "img", required = false) MultipartFile image){
        UserResponse savedUser = userService.createUser(userRequest, image);
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
    public ResponseEntity<UserResponse> getUser(@PathVariable(value = "id") Integer userId){
        return ResponseEntity.ok(userService.get(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@RequestPart(value = "userDto") @Valid UserRequest userRequest,
                                                  @PathVariable(value = "id") Integer userId,
                                                  @RequestParam(value = "img", required = false) MultipartFile img) {
        return ResponseEntity.ok(userService.updateUser(userRequest, userId, img));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(value = "id") Integer userId){
        return ResponseEntity.ok(userService.delete(userId));
    }
}
