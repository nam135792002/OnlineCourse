package com.springboot.courses.payload.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

    @Email(message = "Email is invalid")
    @NotEmpty(message = "Email can not be empty")
    @Length(min = 15, max = 64, message = "Email must have 15 - 64 characters")
    private String email;

    @NotEmpty(message = "Password can not be empty")
    @Length(min = 8, max = 30, message = "Password must have 8 - 30 characters")
    private String password;
}
