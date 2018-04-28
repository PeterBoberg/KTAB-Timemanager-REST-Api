package se.karingotrafiken.timemanager.rest.entitys;


import javax.persistence.*;
import java.util.Date;

@Entity
public class UnionContract implements DbEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne
    private ServiceCategory serviceCategory;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Column(nullable = false)
    private double monthlyWorkHours;

    @Column(nullable = false)
    private double obRegularCoeff;

    @Column(nullable = false)
    private double obHolidayCoeff;

    @Column(nullable = false)
    private double overtimeRegulaCoeff;

    @Column(nullable = false)
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

    public ServiceCategory getServiceCategory() {
        return serviceCategory;
    }

    public void setServiceCategory(ServiceCategory serviceCategory) {
        this.serviceCategory = serviceCategory;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
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

    public double getOvertimeRegulaCoeff() {
        return overtimeRegulaCoeff;
    }

    public void setOvertimeRegulaCoeff(double overtimeRegulaCoeff) {
        this.overtimeRegulaCoeff = overtimeRegulaCoeff;
    }

    public double getOvertimeHolidayCoeff() {
        return overtimeHolidayCoeff;
    }

    public void setOvertimeHolidayCoeff(double overtimeHolidayCoeff) {
        this.overtimeHolidayCoeff = overtimeHolidayCoeff;
    }
}
