package se.karingotrafiken.timemanager.rest.exceptions;

import se.karingotrafiken.timemanager.rest.dto.stored.ErrorMessageDTO;

public class ApiException extends RuntimeException {

    private ErrorMessageDTO.ErrorCode errorCode;

    public ApiException(ErrorMessageDTO.ErrorCode errorCode, String s) {
        super(s);
        this.errorCode = errorCode;
    }

    public ErrorMessageDTO.ErrorCode getErrorCode() {
        return errorCode;
    }
}
