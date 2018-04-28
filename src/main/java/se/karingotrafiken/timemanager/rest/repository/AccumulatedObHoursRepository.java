package se.karingotrafiken.timemanager.rest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import se.karingotrafiken.timemanager.rest.entitys.AccumulatedObHours;

@Repository
@Transactional
public interface AccumulatedObHoursRepository extends CrudRepository<AccumulatedObHours, Long> {
}
