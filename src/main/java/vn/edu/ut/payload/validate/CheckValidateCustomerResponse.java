package vn.edu.ut.payload.validate;

import com.fasterxml.jackson.annotation.JsonProperty;
import vn.edu.ut.enums.MessageErrorCustomer;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CheckValidateCustomerResponse {

    @JsonProperty("message_error")
    private String messageError;

    @Enumerated(EnumType.STRING)
    private MessageErrorCustomer type;
}
