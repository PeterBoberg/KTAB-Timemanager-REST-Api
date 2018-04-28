package se.karingotrafiken.timemanager.rest.entitys;

import se.karingotrafiken.timemanager.rest.appmodel.common.DayType;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"shift_id", "dayType"})})
@Entity
public class ShiftDayPrototype implements DbEntity {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private Shift shift;

    @Column(nullable = false)
    @Temporal(TemporalType.TIME)
    private Date startTimeOfDay;

    @Column(nullable = false)
    @Temporal(TemporalType.TIME)
    private Date endTimeOfDay;

    @Column(nullable = false)
    private double netHours;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DayType dayType;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "shiftDayPrototype", cascade = CascadeType.ALL)
    private Set<AccumulatedObHours> accumulatedObHours = new HashSet<>();

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
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

    public double getNetHours() {
        return netHours;
    }

    public void setNetHours(double netHours) {
        this.netHours = netHours;
    }

    public DayType getDayType() {
        return dayType;
    }

    public void setDayType(DayType dayType) {
        this.dayType = dayType;
    }

    public Set<AccumulatedObHours> getAccumulatedObHours() {
        return accumulatedObHours;
    }

    public void setAccumulatedObHours(Set<AccumulatedObHours> accumulatedObHours) {
        this.accumulatedObHours = accumulatedObHours;
    }

    public boolean isHoliday() {
        return this.dayType == DayType.HOLIDAY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShiftDayPrototype prototype = (ShiftDayPrototype) o;
        return dayType == prototype.dayType;
    }

    @Override
    public int hashCode() {

        return Objects.hash(dayType);
    }

    @Override
    public String toString() {
        return "ShiftDayPrototype{" +
                "id=" + id +
                ", shift=" + shift +
                ", startTimeOfDay=" + startTimeOfDay +
                ", endTimeOfDay=" + endTimeOfDay +
                '}';
    }
}
