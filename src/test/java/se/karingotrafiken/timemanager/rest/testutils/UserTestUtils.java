package se.karingotrafiken.timemanager.rest.testutils;

import org.springframework.stereotype.Component;
import se.karingotrafiken.timemanager.rest.appmodel.common.Role;
import se.karingotrafiken.timemanager.rest.dto.stored.UserDTO;
import se.karingotrafiken.timemanager.rest.entitys.User;

import java.util.Arrays;

@Component
public class UserTestUtils {

    public static User testUser() {
        User user = new User();
        user.setId(1);
        user.setUsername("admin");
        user.setPassword("adminadmin");
        user.setRoles(Arrays.asList(Role.ROLE_ADMIN, Role.ROLE_USER));
        return user;
    }

    public static UserDTO newUser() {
        UserDTO user = new UserDTO();
        user.setId(1);
        user.setUsername("admin");
        user.setPassword("adminadmin");
        user.setRoles(Arrays.asList(Role.ROLE_ADMIN, Role.ROLE_USER));
        return user;
    }
}
