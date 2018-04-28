package se.karingotrafiken.timemanager.rest.dto.stored;

import se.karingotrafiken.timemanager.rest.dto.DtoObject;

public class ScheduledDayDTO implements DtoObject {

    private long id;
    private long employeeId;
    private long unionContractId;
    private String date;
    private String startTimeOfDay; // Using String instead of date because we cant use multiple date formatters when serializing
    private String endTimeOfDay; // Using String instead of date because we cant use multiple date formatters when serializing
    private String weekday;
    private double netHours;
    private boolean isHoliday;
    private double compensationTime;
    private String comment;
    private double accumulatedObAsRegularDay;
    private double accumulatedObAsHoliday;
    private double orderedOvertimeAsWeekday;
    private double orderedOvertimeAsWeekend;
    private BoatDTO boat;


    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public long getId() {
        return this.id;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public long getUnionContractId() {
        return unionContractId;
    }

    public void setUnionContractId(long unionContractId) {
        this.unionContractId = unionContractId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public double getNetHours() {
        return netHours;
    }

    public void setNetHours(double netHours) {
        this.netHours = netHours;
    }

    public boolean isHoliday() {
        return isHoliday;
    }

    public void setHoliday(boolean holiday) {
        isHoliday = holiday;
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

    public BoatDTO getBoat() {
        return boat;
    }

    public void setBoat(BoatDTO boat) {
        this.boat = boat;
    }
}
