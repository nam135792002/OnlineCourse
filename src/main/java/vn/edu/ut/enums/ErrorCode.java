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
    CATEGORY_NAME_EXISTED(1008, "Category name is existed before", HttpStatus.CONFLICT),
    CATEGORY_SLUG_EXISTED(1009, "Category slug is existed before", HttpStatus.CONFLICT),
    COURSE_NAME_EXISTED(1010, "Course name is existed before", HttpStatus.CONFLICT),
    COURSE_SLUG_EXISTED(1011, "Course slug is existed before", HttpStatus.CONFLICT),
    COURSE_SLUG_NAME_EXISTED(1012, "Course slug/name is existed before", HttpStatus.CONFLICT),
    DUPLICATE_NAME_CHAPTER_WITHIN_COURSE(1013, "Name of chapter have existed within same course",
            HttpStatus.CONFLICT);

    private final int code;
    private final String message;
    private final HttpStatusCode httpStatusCode;

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }
}
