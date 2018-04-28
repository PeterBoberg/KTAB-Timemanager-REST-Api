package se.karingotrafiken.timemanager.rest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.karingotrafiken.timemanager.rest.entitys.Withdrawal;

import java.util.Date;
import java.util.List;

@Repository
public interface WithdrawalRepository extends CrudRepository<Withdrawal, Long> {

    List<Withdrawal> findByEmployeeId(long id);

    List<Withdrawal> findByEmployeeIdAndDateBefore(long employeeId, Date endDate);
}
