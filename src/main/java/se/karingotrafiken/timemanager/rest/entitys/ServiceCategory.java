package se.karingotrafiken.timemanager.rest.entitys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class ServiceCategory implements DbEntity, Cloneable {

    @Id @GeneratedValue
    private long id;

    @Column(unique = true, nullable = false)
    private String name;

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceCategory that = (ServiceCategory) o;
        return id == that.id;
    }

    public ServiceCategory clone() {
        ServiceCategory clone = new ServiceCategory();
        clone.id = this.id;
        clone.name = this.name;
        return clone;
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }
}