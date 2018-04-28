package se.karingotrafiken.timemanager.rest.dto.nonstored.response;

public class AuthorizationFailureResponse {

    private final String message = "Full authorization is needed to access this page";

    public String getMessage() {
        return message;
    }

}
