package se.karingotrafiken.timemanager.rest.dto.stored;

import javax.validation.constraints.Min;
import java.util.Objects;

public class AccumulatedObHoursDTO {


    private long id;

    private long shiftDayPrototypeId;

    @Min(value = 1, message = "serviceCategoryId must be > 0")
    private long serviceCategoryId;

    @Min(value = 0, message = "accumulatedObHours can not be null, it can still be empty though")
    private double accumulatedHours;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getShiftDayPrototypeId() {
        return shiftDayPrototypeId;
    }

    public void setShiftDayPrototypeId(long shiftDayPrototypeId) {
        this.shiftDayPrototypeId = shiftDayPrototypeId;
    }

    public long getServiceCategoryId() {
        return serviceCategoryId;
    }

    public void setServiceCategoryId(long serviceCategoryId) {
        this.serviceCategoryId = serviceCategoryId;
    }

    public double getAccumulatedHours() {
        return accumulatedHours;
    }

    public void setAccumulatedHours(double accumulatedHours) {
        this.accumulatedHours = accumulatedHours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccumulatedObHoursDTO that = (AccumulatedObHoursDTO) o;
        return serviceCategoryId == that.serviceCategoryId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(serviceCategoryId);
    }
}
