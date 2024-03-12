package com.springboot.courses.payload.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    private Integer id;

    @NotEmpty(message = "Họ và tên không được để trống")
    @Length(min = 4, max = 64, message = "Họ và tên phải có từ 4 - 64 ký tự")
    @JsonProperty("full_name")
    private String fullName;

    @Email(message = "Email không hợp lệ")
    @NotEmpty(message = "Email không được để trống")
    @Length(min = 15, max = 64, message = "Email phải có từ 15 - 64 ký tự")
    @JsonProperty("email")
    private String email;

    @NotEmpty(message = "Số điện thoại không được để trống")
    @Length(min = 10, max = 11, message = "Số điện thoại phải có từ 10 - 11 số")
    @JsonProperty("phone_number")
    private String phoneNumber;

    @NotEmpty(message = "Mật khẩu không được để trống")
    @Length(min = 8, max = 30, message = "Mật khẩu phải có từ 8 - 30 ký tự")
    private String password;

    private String photo;

    @JsonProperty("created_time")
    private Date createdTime;

    private boolean enabled;

}
