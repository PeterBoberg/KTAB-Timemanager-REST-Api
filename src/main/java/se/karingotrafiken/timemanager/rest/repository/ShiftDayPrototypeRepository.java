package se.karingotrafiken.timemanager.rest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import se.karingotrafiken.timemanager.rest.entitys.ShiftDayPrototype;

import java.util.List;
@Repository
@Transactional
public interface ShiftDayPrototypeRepository extends CrudRepository<ShiftDayPrototype, Long> {

    List<ShiftDayPrototype> findByShiftId(long id);
}
