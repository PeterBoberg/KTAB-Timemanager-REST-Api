package se.karingotrafiken.timemanager.rest.service.reports;

import org.springframework.stereotype.Service;
import se.karingotrafiken.timemanager.rest.dto.nonstored.response.MonthlyReportResponse;

@Service
public interface ReportService {

    MonthlyReportResponse getMonthlyReport(long employeeId, int year, int month);
}
