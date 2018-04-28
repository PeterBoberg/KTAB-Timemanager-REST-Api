package se.karingotrafiken.timemanager.rest.entitys;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Shift implements DbEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne
    private Boat boat;

    @Column(nullable = false)
    private int lengthInDays;

    @OneToMany(mappedBy = "shift", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ShiftDayPrototype> shiftDayPrototypes = new HashSet<>();

    @OneToMany(mappedBy = "shift", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<WorkDayIndex> workDayIndices = new HashSet<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boat getBoat() {
        return boat;
    }

    public void setBoat(Boat boat) {
        this.boat = boat;
    }

    public int getLengthInDays() {
        return lengthInDays;
    }

    public void setLengthInDays(int lengthInDays) {
        this.lengthInDays = lengthInDays;
    }


    public Set<ShiftDayPrototype> getShiftDayPrototypes() {
        return shiftDayPrototypes;
    }

    public void setShiftDayPrototypes(Set<ShiftDayPrototype> shiftDayPrototypes) {
        this.shiftDayPrototypes = shiftDayPrototypes;
    }

    public Set<WorkDayIndex> getWorkDayIndices() {
        return workDayIndices;
    }

    public void setWorkDayIndices(Set<WorkDayIndex> workDayIndices) {
        this.workDayIndices = workDayIndices;
    }

    @Override
    public String toString() {
        return "Shift{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lengthInDays=" + lengthInDays +
                '}';
    }
}
