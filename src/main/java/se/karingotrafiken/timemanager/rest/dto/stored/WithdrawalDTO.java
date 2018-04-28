package se.karingotrafiken.timemanager.rest.dto.stored;

import se.karingotrafiken.timemanager.rest.dto.DtoObject;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class WithdrawalDTO implements DtoObject {

    private long id;

    @Min(value = 0)
    private double amount;

    @NotNull
    private String date; // Date is set when the withdrawal is registered

    private long employeeId;


    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }
}
