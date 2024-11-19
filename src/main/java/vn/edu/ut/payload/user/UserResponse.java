package vn.edu.ut.payload.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
public class UserResponse {

    private Integer id;

    @JsonProperty("full_name")
    private String fullName;

    private String username;

    private String email;

    @JsonProperty("phone_number")
    private String phoneNumber;

    private String photo;

    @JsonProperty("created_time")
    private LocalDateTime createdAt;

    private boolean enabled;

    @JsonProperty("role_name")
    private String roleName;
}
