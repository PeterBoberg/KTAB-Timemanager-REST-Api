package se.karingotrafiken.timemanager.rest.entitys;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Withdrawal implements DbEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private double amount;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date date;

    @ManyToOne
    private Employee employee;


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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
