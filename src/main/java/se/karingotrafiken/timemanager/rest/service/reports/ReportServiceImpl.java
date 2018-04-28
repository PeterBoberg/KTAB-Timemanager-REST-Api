package se.karingotrafiken.timemanager.rest.service.reports;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import se.karingotrafiken.timemanager.rest.dto.nonstored.response.MonthlyReportResponse;
import se.karingotrafiken.timemanager.rest.dto.stored.ErrorMessageDTO;
import se.karingotrafiken.timemanager.rest.dto.stored.WithdrawalDTO;
import se.karingotrafiken.timemanager.rest.entitys.Employee;
import se.karingotrafiken.timemanager.rest.entitys.ScheduledDay;
import se.karingotrafiken.timemanager.rest.exceptions.ApiException;
import se.karingotrafiken.timemanager.rest.repository.EmployeeRepository;
import se.karingotrafiken.timemanager.rest.repository.ScheduledDayRepository;
import se.karingotrafiken.timemanager.rest.service.withdrawal.WithdrawalService;
import se.karingotrafiken.timemanager.rest.utils.DateTimeUtils;

import java.util.Date;
import java.util.List;

@Component
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ScheduledDayRepository scheduledDayRepository;

    @Autowired
    private WithdrawalService withdrawalService;

    @Autowired
    private EmployeeRepository employeeRepository;


    @Override
    @Transactional
    public MonthlyReportResponse getMonthlyReport(long employeeId, int year, int month) {

        if (!DateTimeUtils.isValidYEarAndMonth(year, month))
            throw new ApiException(ErrorMessageDTO.ErrorCode.INVALID_DATE_FORMAT, "Ogiltiga fråge parametrar, år måste vara på formatet yyyy, måndad måste vara mellan 1 och 12");

        Employee employee = employeeRepository.findOne(employeeId);
        if (employee == null)
            throw new ApiException(ErrorMessageDTO.ErrorCode.RESOURCE_NOT_FOUND, "Anställd med id " + employeeId + " existerar inte");

        Date lastDayOfMonth = new DateTime(DateTimeUtils.lastDayOfMonth(year, month)).plusDays(1).toDate();
        List<ScheduledDay> scheduledDays = scheduledDayRepository.findByEmployeeIdAndDateBeforeOrderByDateAsc(employeeId, lastDayOfMonth);
        List<WithdrawalDTO> withdrawals = withdrawalService.getAllWithdrawalsForEmployeeUpTonMonth(employee.getId(), lastDayOfMonth);

        return new ReportBuilder()
                .setScheduledDays(scheduledDays)
                .setWithdrawals(withdrawals)
                .generateMonthlyReport();
    }
}
