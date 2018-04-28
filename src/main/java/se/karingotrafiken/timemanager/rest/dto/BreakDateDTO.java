package se.karingotrafiken.timemanager.rest.dto;

import se.karingotrafiken.timemanager.rest.appmodel.common.BreakDateType;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class BreakDateDTO implements DtoObject {

    private long id;

    @NotNull(message = "breakDateType cannot be null")
    private BreakDateType breakDateType;

    @Min(value = 1, message = "month must be between 1 and 12")
    @Max(value = 12, message = "month must be between 1 and 12")
    private int month;

    @Min(value = 1, message = "day must be between 1 and 31")
    @Max(value = 31, message = "day must be between 1 and 31")
    private int day;

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BreakDateType getBreakDateType() {
        return breakDateType;
    }

    public void setBreakDateType(BreakDateType breakDateType) {
        this.breakDateType = breakDateType;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
