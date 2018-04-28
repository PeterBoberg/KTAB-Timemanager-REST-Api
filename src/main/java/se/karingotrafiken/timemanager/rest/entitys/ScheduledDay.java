package se.karingotrafiken.timemanager.rest.entitys;

import javax.persistence.*;
import java.util.Date;

@Entity
public class ScheduledDay implements DbEntity {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private Employee employee;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date date;

    @Temporal(TemporalType.TIME)
    private Date startTimeOfDay;

    @Temporal(TemporalType.TIME)
    private Date endTimeOfDay;

    @Column(nullable = false)
    private String weekday;

    @ManyToOne
    private UnionContract unionContract;

    @ManyToOne
    private Boat boat;

    private double netHours;

    private double compensationTime;

    private String comment;

    private double accumulatedObAsRegularDay;

    private double accumulatedObAsHoliday;

    private double orderedOvertimeWeekday;

    private double orderedOvertimeWeekend;

    private boolean isHoliday = false;


    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public UnionContract getUnionContract() {
        return unionContract;
    }

    public void setUnionContract(UnionContract unionContract) {
        this.unionContract = unionContract;
    }

    public Boat getBoat() {
        return boat;
    }

    public void setBoat(Boat boat) {
        this.boat = boat;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getStartTimeOfDay() {
        return startTimeOfDay;
    }

    public void setStartTimeOfDay(Date startTimeOfDay) {
        this.startTimeOfDay = startTimeOfDay;
    }

    public Date getEndTimeOfDay() {
        return endTimeOfDay;
    }

    public void setEndTimeOfDay(Date endTimeOfDay) {
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

    public double getOrderedOvertimeWeekday() {
        return orderedOvertimeWeekday;
    }

    public void setOrderedOvertimeWeekday(double orderedOvertimeWeekday) {
        this.orderedOvertimeWeekday = orderedOvertimeWeekday;
    }

    public double getOrderedOvertimeWeekend() {
        return orderedOvertimeWeekend;
    }

    public void setOrderedOvertimeWeekend(double orderedOvertimeWeekend) {
        this.orderedOvertimeWeekend = orderedOvertimeWeekend;
    }

    public boolean isHoliday() {
        return isHoliday;
    }

    public void setHoliday(boolean holiday) {
        isHoliday = holiday;
    }


}
