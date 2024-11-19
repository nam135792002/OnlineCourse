package vn.edu.ut.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Messages {
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Error {
        public static final String INVALID_VN_PHONE_NO = "This field requires a valid Vietnamese phone number";
        public static final String INVALID_PASSWORD = "Password must contain one digit, lowercase, uppercase, " +
                "special character (@$!%*?&), no space and at least 8 characters";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class CodeResponse {
        public static final int NOT_FOUND_CODE = 404;
    }
}
