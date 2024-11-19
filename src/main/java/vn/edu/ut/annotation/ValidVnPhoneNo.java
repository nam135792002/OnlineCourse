package vn.edu.ut.annotation;

import vn.edu.ut.annotation.impl.ValidVnPhoneNoImpl;
import vn.edu.ut.constant.Messages;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidVnPhoneNoImpl.class)
public @interface ValidVnPhoneNo {
    String message() default Messages.Error.INVALID_VN_PHONE_NO;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
