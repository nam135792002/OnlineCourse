package com.springboot.courses.payload.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class UserRequest {

    private Integer id;

    @NotEmpty(message = "Full name can not be empty")
    @Length(min = 4, max = 64, message = "Full name must have 4 - 64 characters")
    @JsonProperty("full_name")
    private String fullName;

    @NotEmpty(message = "Username can not be empty")
    @Length(min = 4, max = 64, message = "Username must have 5 - 45 characters")
    private String username;

    @Email(message = "Email is invalid")
    @NotEmpty(message = "Email can not be empty")
    @Length(min = 15, max = 64, message = "Email must have 15 - 64 characters")
    private String email;

    @NotEmpty(message = "Phone number can not be empty")
    @Length(min = 10, max = 11, message = "Phone number must have 10 - 11 numbers")
    @JsonProperty("phone_number")
    private String phoneNumber;

    private String password;

    private boolean enabled;

}
