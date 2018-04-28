package se.karingotrafiken.timemanager.rest.entitys;

import se.karingotrafiken.timemanager.rest.appmodel.common.BreakDateType;

import javax.persistence.*;

@Entity
public class BreakDate implements DbEntity {

    @Id
    @GeneratedValue
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private BreakDateType breakDateType;

    @Column(nullable = false)
    private int month;

    @Column(nullable = false)
    private int day;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public BreakDateType getBreakDateType() {
        return breakDateType;
    }

    public void setBreakDateType(BreakDateType breakDateType) {
        this.breakDateType = breakDateType;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
