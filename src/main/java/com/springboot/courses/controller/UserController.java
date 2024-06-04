package com.springboot.courses.controller;

import com.springboot.courses.payload.user.UserRequest;
import com.springboot.courses.payload.ClassResponse;
import com.springboot.courses.payload.user.UserResponse;
import com.springboot.courses.service.UserService;
import com.springboot.courses.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@RestController
@RequestMapping("/api/users")
@Tag(
        name = "CRUD REST APIs for User Resource"
)
public class UserController {

    @Autowired private UserService userService;

    @Operation(
            summary = "Create admin REST API",
            description = "Create admin user REST API is used to save admin into database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUser(@RequestPart(value = "user") @Valid UserRequest userRequest,
                                                   @RequestParam(value = "img", required = false) MultipartFile image){
        UserResponse savedUser = userService.createUser(userRequest, image);
        URI uri = URI.create("/api/users/" + savedUser.getId());
        return ResponseEntity.created(uri).body(savedUser);
    }

    @Operation(
            summary = "List all user REST API",
            description = "This REST API is used to retrieve a paginated list of all users from the database. " +
                    "The list can be sorted and filtered using query parameters."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK - Successful retrieval of user list"
    )
    @ApiResponse(
            responseCode = "204",
            description = "Http Status 204 No Content - No users found"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
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

    @Operation(
            summary = "Get user by ID REST API",
            description = "Get user by ID REST API is used to get single user from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Http Status 404 NOT FOUND"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @GetMapping("/get/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable(value = "id") Integer userId){
        return ResponseEntity.ok(userService.get(userId));
    }

    @Operation(
            summary = "Update user by ID REST API",
            description = "Update user REST API is used to update a particular user into the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Http Status 404 NOT FOUND"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<UserResponse> updateUser(@RequestPart(value = "user") @Valid UserRequest userRequest,
                                                  @PathVariable(value = "id") Integer userId,
                                                  @RequestParam(value = "img", required = false) MultipartFile img) {
        return ResponseEntity.ok(userService.updateUser(userRequest, userId, img));
    }

    @Operation(
            summary = "Delete user REST API",
            description = "Delete user REST API is used to delete a particular user from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Http Status 404 NOT FOUND"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable(value = "id") Integer userId){
        return ResponseEntity.ok(userService.delete(userId));
    }

    @Operation(
            summary = "Change Password REST API",
            description = "This REST API is used to change the password of a user identified by their email address."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK - Password changed successfully"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestParam(value = "password") String newPassword,
                                                 @RequestParam(value = "email") String email){
        return ResponseEntity.ok(userService.changePasswordInCustomer(newPassword, email));
    }

    @Operation(
            summary = "Update Customer Info REST API",
            description = "This REST API is used to update the information of a customer identified by their email address. " +
                    "It allows updating the full name and profile image."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK - Customer information updated successfully"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PostMapping("/change-info")
    public ResponseEntity<UserResponse> updateInfoCustomer(@RequestParam(value = "full_name", required = false) String fullName,
                                                           @RequestParam(value = "img", required = false) MultipartFile img,
                                                           @RequestParam(value = "email") String email){
        return ResponseEntity.ok(userService.updateInfoCustomer(fullName, img, email));
    }
}
