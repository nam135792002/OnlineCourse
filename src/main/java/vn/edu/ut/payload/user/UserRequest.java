package vn.edu.ut.payload.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import vn.edu.ut.annotation.StrongPassword;
import vn.edu.ut.annotation.ValidVnPhoneNo;

@Data
@NoArgsConstructor
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
    @JsonProperty("phone_number")
    @ValidVnPhoneNo
    private String phoneNumber;

    @StrongPassword
    private String password;

    private boolean enabled;
}
