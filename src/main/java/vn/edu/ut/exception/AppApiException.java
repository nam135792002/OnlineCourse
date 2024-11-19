package vn.edu.ut.exception;

import vn.edu.ut.enums.ErrorCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AppApiException extends RuntimeException {
    private ErrorCode errorCode;

    public AppApiException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
