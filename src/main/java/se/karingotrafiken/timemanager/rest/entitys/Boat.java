package se.karingotrafiken.timemanager.rest.entitys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Boat implements DbEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Override
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
}
