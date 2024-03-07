package com.springboot.courses.payload;

import com.springboot.courses.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Integer id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String password;
    private String photo;
    private Date createdTime;
    private Role role;
}
