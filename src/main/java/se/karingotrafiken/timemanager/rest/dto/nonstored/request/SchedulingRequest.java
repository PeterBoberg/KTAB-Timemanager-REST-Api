package se.karingotrafiken.timemanager.rest.dto.nonstored.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class SchedulingRequest {

    private long employeeId;

    @Min(value = 1, message = "shiftId must be > 0")
    private long shiftId;

    @NotNull(message = "replicationStart can not be null")
    private String replicationStart;

    @NotNull(message = "startDate can not be null")
    private String startDate;

    @NotNull(message = "endDate can not be null")
    private String endDate;


    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public long getShiftId() {
        return shiftId;
    }

    public void setShiftId(long shiftId) {
        this.shiftId = shiftId;
    }

    public String getReplicationStart() {
        return replicationStart;
    }

    public void setReplicationStart(String replicationStart) {
        this.replicationStart = replicationStart;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
