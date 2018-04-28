package se.karingotrafiken.timemanager.rest.dto.nonstored.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class SwapScheduledDayRequest {

    private long askingEmployeeId;

    @NotNull(message = "date can not be null")
    private String date;

    @Min(value = 1, message = "askedEmployeeId must be set")
    private long askedEmployeeId;


    public SwapScheduledDayRequest() {
    }

    public long getAskingEmployeeId() {
        return askingEmployeeId;
    }

    public void setAskingEmployeeId(long askingEmployeeId) {
        this.askingEmployeeId = askingEmployeeId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getAskedEmployeeId() {
        return askedEmployeeId;
    }

    public void setAskedEmployeeId(long askedEmployeeId) {
        this.askedEmployeeId = askedEmployeeId;
    }
}
