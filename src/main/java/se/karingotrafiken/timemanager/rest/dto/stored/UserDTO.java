package se.karingotrafiken.timemanager.rest.dto.stored;

import org.hibernate.validator.constraints.NotEmpty;
import se.karingotrafiken.timemanager.rest.appmodel.common.Role;
import se.karingotrafiken.timemanager.rest.dto.DtoObject;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class UserDTO implements DtoObject {


    private long id;
    @NotNull
    @Size(min = 4, message = "username must be >= 4 in length")
    private String username;

    private String password;

    @NotEmpty
    private List<Role> roles;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
