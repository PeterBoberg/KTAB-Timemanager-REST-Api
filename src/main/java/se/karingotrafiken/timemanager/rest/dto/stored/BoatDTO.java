package se.karingotrafiken.timemanager.rest.dto.stored;

import org.hibernate.validator.constraints.NotEmpty;
import se.karingotrafiken.timemanager.rest.dto.DtoObject;

import javax.validation.constraints.NotNull;

public class BoatDTO implements DtoObject {

    private long id;

    @NotNull(message = "Boat can not be null")
    @NotEmpty(message = "Boat can not be empty")
    private String name;

    @Override
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
}
