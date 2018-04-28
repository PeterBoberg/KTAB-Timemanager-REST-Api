package se.karingotrafiken.timemanager.rest.entitys;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Entity
public class WorkDayIndex implements DbEntity {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private Shift shift;

    private int dayIndex;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public int getDayIndex() {
        return dayIndex;
    }

    public void setDayIndex(int dayIndex) {
        this.dayIndex = dayIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkDayIndex that = (WorkDayIndex) o;
        return dayIndex == that.dayIndex;
    }

    @Override
    public int hashCode() {

        return Objects.hash(dayIndex);
    }
}
