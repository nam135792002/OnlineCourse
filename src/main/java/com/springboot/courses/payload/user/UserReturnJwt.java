package com.springboot.courses.payload.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"full_name", "username", "photo", "role_name"})
public class UserReturnJwt {

    @JsonProperty("full_name")
    private String fullName;

    private String username;

    private String email;

    private String photo;

    @JsonProperty("role_name")
    private String roleName;
}
