package se.karingotrafiken.timemanager.rest.service.users;

import se.karingotrafiken.timemanager.rest.dto.nonstored.request.LoginRequest;
import se.karingotrafiken.timemanager.rest.dto.nonstored.response.LoginResponse;
import se.karingotrafiken.timemanager.rest.entitys.User;

public interface UserService {

    LoginResponse loginWith(LoginRequest loginRequest);

    User getCurrentUser();
}
