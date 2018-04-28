package se.karingotrafiken.timemanager.rest.dto.stored;

public class ErrorMessageDTO {

    private ErrorCode errorCode;
    private String errorMessage;

    public ErrorMessageDTO() {}

    public ErrorMessageDTO(ErrorCode errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public enum ErrorCode {
        RESOURCE_NOT_FOUND,
        INVALID_INPUT,
        INVALID_DATE_FORMAT,
        INVALID_TIME_FORMAT,
        INVALID_EMAIL_FORMAT,
        DUPLICATE_ENTRY,
        INVALID_OBJECT_MANIPULATION,
        UNSUPPORTED_ACTION,
        INVALID_SHIFT_CONFIGURATION,
        INVALID_SCHEDULING,
        INVALID_PERSONAL_NUMBER,
        INVALID_TOKEN,
        AUTHORIZATION_FAILURE,
        AUTHENTICATION_FAILURE,
        BAD_CREDENTIALS,
        INVALID_PASSWORD_FORMAT,
        INVALID_UNION_CONTRACT_CONFIGURATION,
        OVERLAPING_UNION_CONTRACTS,
        NO_BREAK_DATE_SET,
        INVALID_MONTH_OF_YEAR,
        INVALID_SCHEDULE_SWAP
    }
}