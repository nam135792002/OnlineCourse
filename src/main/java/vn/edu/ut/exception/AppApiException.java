package vn.edu.ut.exception;

import lombok.Getter;
import lombok.Setter;
import vn.edu.ut.enums.ErrorCode;

@Setter
@Getter
public class AppApiException extends RuntimeException {
    private ErrorCode errorCode;

    public AppApiException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
