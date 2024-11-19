package vn.edu.ut.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    EMAIL_OR_PASSWORD_IS_INVALID(1001, "Email or password is invalid", HttpStatus.UNAUTHORIZED),
    ACCOUNT_NON_ACTIVE(1002, "Account have never active", HttpStatus.UNAUTHORIZED),
    EMAIL_EXISTED(1003, "Email has been registered before", HttpStatus.CONFLICT),
    USERNAME_EXISTED(1004, "Username has been registered before", HttpStatus.CONFLICT),
    PHONE_NUMBER_EXISTED(1005, "Phone number has been registered before", HttpStatus.CONFLICT),
    UNAUTHENTICATED(1006, "UnAuthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "UnAuthorized", HttpStatus.FORBIDDEN),
    NOT_FOUND_RESOURCE(1008, "%s not found with %s: '%s'", HttpStatus.NOT_FOUND);

    private final int code;
    private final String message;
    private final HttpStatusCode httpStatusCode;

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }
}
