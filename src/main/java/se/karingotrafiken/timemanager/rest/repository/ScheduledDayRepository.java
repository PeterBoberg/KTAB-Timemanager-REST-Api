package se.karingotrafiken.timemanager.rest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import se.karingotrafiken.timemanager.rest.entitys.ScheduledDay;

import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface ScheduledDayRepository extends CrudRepository<ScheduledDay, Long> {

    void deleteByDateBetweenAndEmployeeId(Date startDate, Date endDate, long employeeId);

    List<ScheduledDay> findByEmployeeIdOrderByDateAsc(long employeeId);

    List<ScheduledDay> findByDateBetweenAndEmployeeIdOrderByDateAsc(Date startDate, Date endDate, long employeeId);

    ScheduledDay findByDateAndEmployeeId(Date date, long employeeId);

    List<ScheduledDay> findByEmployeeIdAndDateBeforeOrderByDateAsc(long employeeId, Date beforeDate);
}
