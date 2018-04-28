package se.karingotrafiken.timemanager.rest.service.withdrawal;

import org.springframework.stereotype.Service;
import se.karingotrafiken.timemanager.rest.dto.stored.WithdrawalDTO;
import se.karingotrafiken.timemanager.rest.entitys.Withdrawal;
import se.karingotrafiken.timemanager.rest.service.GenericCrudService;

import java.util.Date;
import java.util.List;

@Service
public interface WithdrawalService extends GenericCrudService<WithdrawalDTO, Withdrawal> {
    List<WithdrawalDTO> getAllWithdrawalsForEmployee(long employeeId);
    List<WithdrawalDTO> getAllWithdrawalsForEmployeeUpTonMonth(long employeeId, Date lastDayOfMonth);
}
