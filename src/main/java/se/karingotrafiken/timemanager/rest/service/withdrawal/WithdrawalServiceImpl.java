package se.karingotrafiken.timemanager.rest.service.withdrawal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import se.karingotrafiken.timemanager.rest.dto.stored.WithdrawalDTO;
import se.karingotrafiken.timemanager.rest.entitys.Employee;
import se.karingotrafiken.timemanager.rest.entitys.Withdrawal;
import se.karingotrafiken.timemanager.rest.repository.WithdrawalRepository;
import se.karingotrafiken.timemanager.rest.service.AbstractCrudService;
import se.karingotrafiken.timemanager.rest.service.Translator;
import se.karingotrafiken.timemanager.rest.utils.DateTimeUtils;

import java.util.Date;
import java.util.List;

@Component
public class WithdrawalServiceImpl extends AbstractCrudService<WithdrawalDTO, Withdrawal> implements WithdrawalService {

    @Autowired
    private WithdrawalRepository withdrawalRepository;

    @Override
    @Transactional
    public List<WithdrawalDTO> getAllWithdrawalsForEmployee(long employeeId) {
        List<Withdrawal> withdrawals = withdrawalRepository.findByEmployeeId(employeeId);
        return getTranslator().translateFromEntityList(withdrawals);
    }

    @Override
    @Transactional
    public List<WithdrawalDTO> getAllWithdrawalsForEmployeeUpTonMonth(long employeeId, Date lastDayOfMonth) {
        List<Withdrawal> withdrawals = withdrawalRepository.findByEmployeeIdAndDateBefore(employeeId, lastDayOfMonth);
        return getTranslator().translateFromEntityList(withdrawals);
    }

    @Override
    public CrudRepository<Withdrawal, Long> getRepository() {
        return withdrawalRepository;
    }

    @Override
    public Translator<WithdrawalDTO, Withdrawal> getTranslator() {
        return new Translator<WithdrawalDTO, Withdrawal>() {
            @Override
            public WithdrawalDTO translateFromEntity(Withdrawal entity) {
                WithdrawalDTO dto = new WithdrawalDTO();
                dto.setId(entity.getId());
                dto.setAmount(entity.getAmount());
                dto.setDate(DateTimeUtils.toDateString(entity.getDate()));
                dto.setEmployeeId(entity.getEmployee().getId());
                return dto;
            }

            @Override
            public Withdrawal translateFromDTO(WithdrawalDTO dto) {
                Employee employee = new Employee();
                employee.setId(dto.getEmployeeId());

                Withdrawal entity = new Withdrawal();
                entity.setId(dto.getId());
                entity.setAmount(dto.getAmount());

                entity.setDate(DateTimeUtils.dateFromDateString(dto.getDate()));
                entity.setEmployee(employee);
                return entity;
            }
        };
    }
}
