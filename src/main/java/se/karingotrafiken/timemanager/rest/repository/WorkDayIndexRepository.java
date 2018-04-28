package se.karingotrafiken.timemanager.rest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import se.karingotrafiken.timemanager.rest.entitys.WorkDayIndex;

@Repository
@Transactional
public interface WorkDayIndexRepository extends CrudRepository<WorkDayIndex, Long> {

    void deleteByShiftId(long shiftId);
}
