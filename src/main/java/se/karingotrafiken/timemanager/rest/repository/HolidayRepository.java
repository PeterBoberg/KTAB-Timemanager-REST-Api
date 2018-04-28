package se.karingotrafiken.timemanager.rest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.karingotrafiken.timemanager.rest.entitys.Holiday;

import java.util.Date;
import java.util.List;

@Repository
public interface HolidayRepository extends CrudRepository<Holiday, Long> {

    List<Holiday> findByDateBetween(Date fromDate, Date toDate);
}
