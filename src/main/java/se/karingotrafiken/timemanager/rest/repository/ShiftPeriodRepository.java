package se.karingotrafiken.timemanager.rest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import se.karingotrafiken.timemanager.rest.entitys.ShiftPeriod;

import java.util.List;

@Repository
@Transactional
public interface ShiftPeriodRepository extends CrudRepository<ShiftPeriod, Long> {

    List<ShiftPeriod> findByEmployeeIdOrderByStartDate(long id);

    List<ShiftPeriod> findByShiftId(long id);
}
