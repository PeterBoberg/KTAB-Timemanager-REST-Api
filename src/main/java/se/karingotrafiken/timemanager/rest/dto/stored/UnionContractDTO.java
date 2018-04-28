package se.karingotrafiken.timemanager.rest.dto.stored;

import org.hibernate.validator.constraints.NotEmpty;
import se.karingotrafiken.timemanager.rest.dto.DtoObject;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class UnionContractDTO implements DtoObject {

    private long id;

    @NotNull(message = "name can not be null")
    @NotEmpty(message = "name can not me empty")
    private String name;

    @Min(value = 1, message = "serviceCategoryId must me > 0")
    private long serviceCategoryId;

    @NotNull(message = "startDate can not be null")
    private String startDate;

    @NotNull(message = "endDate van not be null")
    private String endDate;

    @Min(value = 0, message = "monthlyWorkHours must be > 0")
    private double monthlyWorkHours;

    @Min(value = 0, message = "obRegularCoeff must be > 0")
    private double obRegularCoeff;

    @Min(value = 0, message = "obHolidayCoeff must be > 0")
    private double obHolidayCoeff;

    @Min(value = 0, message = "overtimeRegularCoeff must be > 0")
    private double overtimeRegularCoeff;

    @Min(value = 0, message = "overtimeHolidayCoeff must be > 0")
    private double overtimeHolidayCoeff;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getServiceCategoryId() {
        return serviceCategoryId;
    }

    public void setServiceCategoryId(long serviceCategoryId) {
        this.serviceCategoryId = serviceCategoryId;
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

    public double getMonthlyWorkHours() {
        return monthlyWorkHours;
    }

    public void setMonthlyWorkHours(double monthlyWorkHours) {
        this.monthlyWorkHours = monthlyWorkHours;
    }

    public double getObRegularCoeff() {
        return obRegularCoeff;
    }

    public void setObRegularCoeff(double obRegularCoeff) {
        this.obRegularCoeff = obRegularCoeff;
    }

    public double getObHolidayCoeff() {
        return obHolidayCoeff;
    }

    public void setObHolidayCoeff(double obHolidayCoeff) {
        this.obHolidayCoeff = obHolidayCoeff;
    }

    public double getOvertimeRegularCoeff() {
        return overtimeRegularCoeff;
    }

    public void setOvertimeRegularCoeff(double overtimeRegularCoeff) {
        this.overtimeRegularCoeff = overtimeRegularCoeff;
    }

    public double getOvertimeHolidayCoeff() {
        return overtimeHolidayCoeff;
    }

    public void setOvertimeHolidayCoeff(double overtimeHolidayCoeff) {
        this.overtimeHolidayCoeff = overtimeHolidayCoeff;
    }
}
