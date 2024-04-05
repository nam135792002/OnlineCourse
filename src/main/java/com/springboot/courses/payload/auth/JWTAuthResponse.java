package com.springboot.courses.payload.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.core.annotation.Order;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"access_token", "token_type", "full_name", "thumbnail", "username"})
public class JWTAuthResponse {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("token_type")
    private String tokenType = "Bearer";

    @JsonProperty("full_name")
    private String fullName;

    private String thumbnail;

    private String username;
}
