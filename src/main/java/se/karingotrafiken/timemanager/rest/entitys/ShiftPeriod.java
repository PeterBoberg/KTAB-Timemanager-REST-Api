package se.karingotrafiken.timemanager.rest.entitys;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"employee_id", "shift_id", "startDate", "endDate"})})
public class ShiftPeriod implements DbEntity {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private Employee employee;

    @ManyToOne
    private Shift shift;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date endDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
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

    @Override
    public String toString() {
        return "ShiftPeriod{" +
                "id=" + id +
                ", employee=" + employee +
                ", shift=" + shift +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
