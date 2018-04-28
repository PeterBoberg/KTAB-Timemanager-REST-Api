package se.karingotrafiken.timemanager.rest.dto.nonstored.request;

import se.karingotrafiken.timemanager.rest.dto.stored.BoatDTO;

import javax.validation.Valid;
import javax.validation.constraints.Min;

/**
 * This class represents all the fields that are changeable with a scheduled day.
 * It it similar to its sibling <Code>ScheduledDayDTO</Code> but lacks fields that are not changeable
 * post creation
 */

public class ChangeScheduledDayRequest {




    private String startTimeOfDay;
    private String endTimeOfDay;

    @Min(value = 0)
    private double netHours;

    @Min(value = 0)
    private double orderedOvertimeAsWeekday;

    @Min(value = 0)
    private double orderedOvertimeAsWeekend;

    @Min(value = 0)
    private double accumulatedObAsRegularDay;

    @Min(value = 0)
    private double accumulatedObAsHoliday;

    @Min(value = 0)
    private double compensationTime;

    private String comment;

    @Valid
    private BoatDTO boat;

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

    public double getOrderedOvertimeAsWeekday() {
        return orderedOvertimeAsWeekday;
    }

    public void setOrderedOvertimeAsWeekday(double orderedOvertimeAsWeekday) {
        this.orderedOvertimeAsWeekday = orderedOvertimeAsWeekday;
    }

    public double getOrderedOvertimeAsWeekend() {
        return orderedOvertimeAsWeekend;
    }

    public void setOrderedOvertimeAsWeekend(double orderedOvertimeAsWeekend) {
        this.orderedOvertimeAsWeekend = orderedOvertimeAsWeekend;
    }

    public double getAccumulatedObAsRegularDay() {
        return accumulatedObAsRegularDay;
    }

    public void setAccumulatedObAsRegularDay(double accumulatedObAsRegularDay) {
        this.accumulatedObAsRegularDay = accumulatedObAsRegularDay;
    }

    public double getAccumulatedObAsHoliday() {
        return accumulatedObAsHoliday;
    }

    public void setAccumulatedObAsHoliday(double accumulatedObAsHoliday) {
        this.accumulatedObAsHoliday = accumulatedObAsHoliday;
    }

    public double getCompensationTime() {
        return compensationTime;
    }

    public void setCompensationTime(double compensationTime) {
        this.compensationTime = compensationTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setBoat(BoatDTO boat) {
        this.boat = boat;
    }

    public BoatDTO getBoat() {
        return boat;
    }
}
