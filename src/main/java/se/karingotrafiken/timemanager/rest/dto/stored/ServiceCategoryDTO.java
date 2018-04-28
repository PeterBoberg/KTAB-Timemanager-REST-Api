package se.karingotrafiken.timemanager.rest.dto.stored;

import org.hibernate.validator.constraints.NotEmpty;
import se.karingotrafiken.timemanager.rest.dto.DtoObject;

import javax.validation.constraints.NotNull;

public class ServiceCategoryDTO implements DtoObject, Cloneable {

    private long id;
    @NotNull(message = "Name can not be null")
    @NotEmpty(message = "Name ca not be empty")
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ServiceCategoryDTO clone() {
        ServiceCategoryDTO cloned = new ServiceCategoryDTO();
        cloned.id = id;
        cloned.name = name;
        return cloned;
    }
}
