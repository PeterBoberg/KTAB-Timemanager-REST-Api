package se.karingotrafiken.timemanager.rest.dto.nonstored.response;

public class CheckScheduleResponse {

    private long employeeId;
    private String fromDate;
    private String toDate;

    public CheckScheduleResponse() {
    }

    public CheckScheduleResponse(long employeeId, String fromDate, String toDate) {
        this.employeeId = employeeId;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }
}
