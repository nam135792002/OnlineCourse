package vn.edu.ut.annotation.impl;

import vn.edu.ut.annotation.ValidVnPhoneNo;
import vn.edu.ut.constant.RegexPatterns;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor
public class ValidVnPhoneNoImpl implements ConstraintValidator<ValidVnPhoneNo, String> {
    @Override
    public void initialize(ValidVnPhoneNo validVnPhoneNo) {
        ConstraintValidator.super.initialize(validVnPhoneNo);
    }

    @Override
    public boolean isValid(String phoneNo, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(phoneNo)) {
            return true;
        }
        return phoneNo.matches(RegexPatterns.VN_PHONE_NO);
    }
}
