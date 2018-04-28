package se.karingotrafiken.timemanager.rest.dto.stored;

import se.karingotrafiken.timemanager.rest.dto.DtoObject;

import javax.validation.constraints.Min;
import java.util.Objects;

public class WorkDayIndexDTO implements DtoObject {

    private long id;
    private long shiftId;

    @Min(value = 1, message = "dayindex must be > 0")
    private int dayIndex;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public long getShiftId() {
        return shiftId;
    }

    public void setShiftId(long shiftId) {
        this.shiftId = shiftId;
    }

    public int getDayIndex() {
        return dayIndex;
    }

    public void setDayIndex(int dayIndex) {
        this.dayIndex = dayIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkDayIndexDTO that = (WorkDayIndexDTO) o;
        return dayIndex == that.dayIndex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dayIndex);
    }
}
