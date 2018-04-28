package se.karingotrafiken.timemanager.rest.dto.stored;

import org.hibernate.validator.constraints.NotEmpty;
import se.karingotrafiken.timemanager.rest.dto.DtoObject;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Set;

public class ShiftDTO implements DtoObject {

    private long id;

    @NotNull(message = "name can not be null")
    @NotEmpty(message = "name can not be empty")
    private String name;

    @NotNull(message = "Boat can not by null")
    private BoatDTO boat;

    @Min(value = 1, message = "lengthInDays must be > 0")
    private int lengthInDays;

    @Valid
    @NotNull(message = "workDayIndices can not be null, it can still be empty though")
    private Set<WorkDayIndexDTO> workDayIndices;

    @Valid
    @NotNull
    private Set<ShiftDayPrototypeDTO> shiftDayPrototypes;

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

    public BoatDTO getBoat() {
        return boat;
    }

    public void setBoat(BoatDTO boat) {
        this.boat = boat;
    }

    public int getLengthInDays() {
        return lengthInDays;
    }

    public void setLengthInDays(int lengthInDays) {
        this.lengthInDays = lengthInDays;
    }

    public Set<WorkDayIndexDTO> getWorkDayIndices() {
        return workDayIndices;
    }

    public void setWorkDayIndices(Set<WorkDayIndexDTO> workDayIndices) {
        this.workDayIndices = workDayIndices;
    }

    public Set<ShiftDayPrototypeDTO> getShiftDayPrototypes() {
        return shiftDayPrototypes;
    }

    public void setShiftDayPrototypes(Set<ShiftDayPrototypeDTO> shiftDayPrototypes) {
        this.shiftDayPrototypes = shiftDayPrototypes;
    }

    @Override
    public String toString() {
        return "ShiftDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lengthInDays=" + lengthInDays +
                '}';
    }
}
