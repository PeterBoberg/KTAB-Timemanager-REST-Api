package se.karingotrafiken.timemanager.rest.entitys;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Holiday {

    @Id
    @GeneratedValue
    private long id;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
