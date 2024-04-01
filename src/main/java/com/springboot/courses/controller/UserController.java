package com.springboot.courses.controller;

import com.springboot.courses.payload.user.UserRequest;
import com.springboot.courses.payload.ClassResponse;
import com.springboot.courses.payload.user.UserResponse;
import com.springboot.courses.service.UserService;
import com.springboot.courses.utils.AppConstants;
import jakarta.servlet.http.HttpServletRequest;
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

    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUser(@RequestPart(value = "user") @Valid UserRequest userRequest,
                                                   @RequestParam(value = "img", required = false) MultipartFile image){
        UserResponse savedUser = userService.createUser(userRequest, image);
        URI uri = URI.create("/api/users/" + savedUser.getId());
        return ResponseEntity.created(uri).body(savedUser);
    }

    @GetMapping("/list-all")
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

    @GetMapping("/get/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable(value = "id") Integer userId){
        return ResponseEntity.ok(userService.get(userId));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserResponse> updateUser(@RequestPart(value = "user") @Valid UserRequest userRequest,
                                                  @PathVariable(value = "id") Integer userId,
                                                  @RequestParam(value = "img", required = false) MultipartFile img) {
        return ResponseEntity.ok(userService.updateUser(userRequest, userId, img));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable(value = "id") Integer userId){
        return ResponseEntity.ok(userService.delete(userId));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registration(@RequestPart(value = "user") @Valid UserRequest userRequest,
                                          @RequestParam(value = "img", required = false) MultipartFile img,
                                          HttpServletRequest request){
        return new ResponseEntity<>(userService.createCustomer(userRequest, img, request), HttpStatus.CREATED);
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verify(@RequestParam(value = "code") String verification){
        boolean verify = userService.verify(verification);
        URI uri = URI.create("/api/users/register/" + (verify?"success":"failed"));
        if(verify){
            return ResponseEntity.ok().body(uri);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(uri);
        }
    }
}
