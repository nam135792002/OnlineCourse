package vn.edu.ut.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RegexPatterns {
    public static final String VN_PHONE_NO = "^(\\+84|84|0)[235789][0-9]{8,9}$";
}
