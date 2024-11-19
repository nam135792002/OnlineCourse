package vn.edu.ut.payload.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@Data
@AllArgsConstructor
@JsonPropertyOrder({"access_token", "token_type"})
public class JWTAuthResponse {

    @JsonProperty("access_token")
    private String accessToken;
}
