package se.karingotrafiken.timemanager.rest.entitys;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class AccumulatedObHours implements DbEntity {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private ServiceCategory serviceCategory;

    @Column(nullable = false)
    private double accumulatedHours;

    @ManyToOne
    private ShiftDayPrototype shiftDayPrototype;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ServiceCategory getServiceCategory() {
        return serviceCategory;
    }

    public void setServiceCategory(ServiceCategory serviceCategory) {
        this.serviceCategory = serviceCategory;
    }

    public double getAccumulatedHours() {
        return accumulatedHours;
    }

    public void setAccumulatedHours(double accumulatedHours) {
        this.accumulatedHours = accumulatedHours;
    }

    public ShiftDayPrototype getShiftDayPrototype() {
        return shiftDayPrototype;
    }

    public void setShiftDayPrototype(ShiftDayPrototype shiftDayPrototype) {
        this.shiftDayPrototype = shiftDayPrototype;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccumulatedObHours that = (AccumulatedObHours) o;
        return this.serviceCategory.equals(that.serviceCategory);
    }

    @Override
    public int hashCode() {

        return Objects.hash(serviceCategory);
    }
}
