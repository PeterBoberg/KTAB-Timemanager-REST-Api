package se.karingotrafiken.timemanager.rest.dto.stored;

import se.karingotrafiken.timemanager.rest.appmodel.common.DayType;
import se.karingotrafiken.timemanager.rest.dto.DtoObject;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ShiftDayPrototypeDTO implements DtoObject {

    private long id;

    private long shiftId;

    @NotNull(message = "startTimeOfDay can not be null")
    private String startTimeOfDay;

    @NotNull(message = "endTimeOfDay can not be null")
    private String endTimeOfDay;

    @Min(value = 1, message = "netHours must be > 0")
    private double netHours;

    @NotNull(message = "dayType incorrect, can only be one of the values: MONDAY | TUESDAY | WEDNESDAY | THURSDAY | FRIDAY | SATURDAY | SUNDAY | HOLIDAY | START_DAY | END_DAY")
    private DayType dayType;

    @NotNull(message = "accumulatedObHours can not be null")
    private Set<AccumulatedObHoursDTO> accumulatedObHours = new HashSet<>();

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

    public String getStartTimeOfDay() {
        return startTimeOfDay;
    }

    public void setStartTimeOfDay(String startTimeOfDay) {
        this.startTimeOfDay = startTimeOfDay;
    }

    public String getEndTimeOfDay() {
        return endTimeOfDay;
    }

    public void setEndTimeOfDay(String endTimeOfDay) {
        this.endTimeOfDay = endTimeOfDay;
    }

    public double getNetHours() {
        return netHours;
    }

    public void setNetHours(double netHours) {
        this.netHours = netHours;
    }

    public DayType getDayType() {
        return dayType;
    }

    public void setDayType(DayType dayType) {
        this.dayType = dayType;
    }

    public Set<AccumulatedObHoursDTO> getAccumulatedObHours() {
        return accumulatedObHours;
    }

    public void setAccumulatedObHours(Set<AccumulatedObHoursDTO> accumulatedObHours) {
        this.accumulatedObHours = accumulatedObHours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShiftDayPrototypeDTO that = (ShiftDayPrototypeDTO) o;
        return dayType == that.dayType;
    }

    @Override
    public int hashCode() {

        return Objects.hash(dayType);
    }
}
