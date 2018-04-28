package se.karingotrafiken.timemanager.rest.repository;

import org.springframework.data.repository.CrudRepository;
import se.karingotrafiken.timemanager.rest.entitys.Boat;

public interface BoatRepository extends CrudRepository<Boat, Long> {
}
