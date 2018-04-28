package se.karingotrafiken.timemanager.rest.service.reports;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;
import se.karingotrafiken.timemanager.rest.dto.MonthAndYear;
import se.karingotrafiken.timemanager.rest.dto.nonstored.response.MonthlyReportResponse;
import se.karingotrafiken.timemanager.rest.dto.nonstored.response.PreviousTotal;
import se.karingotrafiken.timemanager.rest.dto.stored.WithdrawalDTO;
import se.karingotrafiken.timemanager.rest.entitys.ScheduledDay;

import java.util.*;

@Component
class ReportBuilder {

    private List<ScheduledDay> scheduledDays;
    private List<WithdrawalDTO> withdrawals;
    private TreeMap<MonthAndYear, List<ScheduledDay>> monthAndYearScheduledDayMap = new TreeMap<>();
    private TreeMap<MonthAndYear, List<WithdrawalDTO>> withdrawalDTOTreeMap = new TreeMap<>();

    private MonthlyReportResponse monthlyReport = new MonthlyReportResponse();


    MonthlyReportResponse generateMonthlyReport() {
        if (scheduledDays == null)
            throw new IllegalStateException("Illegal state, scheduledDays must be set");
        if (withdrawals == null)
            throw new IllegalStateException("Illegal state, withdrawals must be set");

        loadTreeMaps();
        calculate();
        return monthlyReport;
    }

    @SuppressWarnings({"Java8MapApi", "Duplicates"}) //TODO: Improve/refactor this snippet
    private void loadTreeMaps() {
        scheduledDays.forEach((scheduledDay) -> {
            DateTime dateTime = new DateTime(scheduledDay.getDate());
            MonthAndYear monthAndYear = new MonthAndYear(dateTime.getYear(), dateTime.getMonthOfYear());
            if (monthAndYearScheduledDayMap.get(monthAndYear) == null)
                monthAndYearScheduledDayMap.put(monthAndYear, new ArrayList<>());
            monthAndYearScheduledDayMap.get(monthAndYear).add(scheduledDay);
        });

        withdrawals.forEach(withdrawalDTO -> {
            DateTime dateTime = new DateTime(withdrawalDTO.getDate());
            MonthAndYear monthAndYear = new MonthAndYear(dateTime.getYear(), dateTime.getMonthOfYear());
            if (withdrawalDTOTreeMap.get(monthAndYear) == null)
                withdrawalDTOTreeMap.put(monthAndYear, new ArrayList<>());
            withdrawalDTOTreeMap.get(monthAndYear).add(withdrawalDTO);
        });
    }


    ReportBuilder setScheduledDays(List<ScheduledDay> scheduledDays) {
        this.scheduledDays = scheduledDays;
        return this;
    }

    ReportBuilder setWithdrawals(List<WithdrawalDTO> withdrawals) {
        this.withdrawals = withdrawals;
        return this;
    }


    @SuppressWarnings("Duplicates") //TODO: Improve/refactor this method, too long
    private void calculate() {

        double grossRegularObHours = 0;
        double grossHolidayObHours = 0;
        double grossRegularOverTimeHours = 0;
        double grossWeekendOverTimeHours = 0;
        double takenOutCompensationTime = 0;

        double netRegularObHours = 0;
        double netHolidayObHours = 0;
        double netRegularOvertimeHours = 0;
        double netWeekendOvertimeHours = 0;
        double grossTotal = 0;
        double paidOutCompensationTime = 0;
        double netTotal = 0;


        int limit = monthAndYearScheduledDayMap.entrySet().size() - 2;
        int counter = 0;

        // For all previous months
        for (Iterator<MonthAndYear> iterator = monthAndYearScheduledDayMap.keySet().iterator(); iterator.hasNext() && counter <= limit; ) {
            MonthAndYear currentMonthAndYear = iterator.next();

            // For each day in previous months
            for (ScheduledDay scheduledDay : monthAndYearScheduledDayMap.get(currentMonthAndYear)) {

                grossRegularObHours += scheduledDay.getAccumulatedObAsRegularDay();
                grossHolidayObHours += scheduledDay.getAccumulatedObAsHoliday();
                grossRegularOverTimeHours += scheduledDay.getOrderedOvertimeWeekday();
                grossWeekendOverTimeHours += scheduledDay.getOrderedOvertimeWeekend();
                takenOutCompensationTime += scheduledDay.getCompensationTime();

                netRegularObHours += scheduledDay.getAccumulatedObAsRegularDay() * scheduledDay.getUnionContract().getObRegularCoeff();
                netHolidayObHours += scheduledDay.getAccumulatedObAsHoliday() * scheduledDay.getUnionContract().getObHolidayCoeff();
                netRegularOvertimeHours += scheduledDay.getOrderedOvertimeWeekday() * scheduledDay.getUnionContract().getOvertimeRegulaCoeff();
                netWeekendOvertimeHours += scheduledDay.getOrderedOvertimeWeekend() * scheduledDay.getUnionContract().getOvertimeHolidayCoeff();
            }

            grossTotal = netRegularObHours + netHolidayObHours + netRegularOvertimeHours + netWeekendOvertimeHours;
            paidOutCompensationTime = sumUpCompensationTimeUpUntil(currentMonthAndYear);
            netTotal = grossTotal - paidOutCompensationTime - takenOutCompensationTime;

            monthlyReport.getPreviousTotals().add(new PreviousTotal(currentMonthAndYear.getYear(), currentMonthAndYear.getMonth(), netTotal));
            counter++;

        }

        monthlyReport.getInbound().setGrossRegularObHours(grossRegularObHours);
        monthlyReport.getInbound().setGrossHolidayObHours(grossHolidayObHours);
        monthlyReport.getInbound().setNetRegularObHours(netRegularObHours);
        monthlyReport.getInbound().setNetHolidayObHours(netHolidayObHours);

        monthlyReport.getInbound().setGrossRegularOvertimeHours(grossRegularOverTimeHours);
        monthlyReport.getInbound().setGrossWeekendOvertimeHours(grossWeekendOverTimeHours);
        monthlyReport.getInbound().setNetRegularOvertimeHours(netRegularOvertimeHours);
        monthlyReport.getInbound().setNetWeekendOvertimeHours(netWeekendOvertimeHours);

        monthlyReport.getInbound().setPaidOutCompensationTime(paidOutCompensationTime);
        monthlyReport.getInbound().setTakenOutCompensationTime(takenOutCompensationTime);
        monthlyReport.getInbound().setNetTotal(netTotal);

        grossRegularObHours = 0;
        grossHolidayObHours = 0;
        grossRegularOverTimeHours = 0;
        grossWeekendOverTimeHours = 0;
        takenOutCompensationTime = 0;

        netRegularObHours = 0;
        netHolidayObHours = 0;
        netRegularOvertimeHours = 0;
        netWeekendOvertimeHours = 0;

        if (monthAndYearScheduledDayMap.lastEntry() != null) {
            MonthAndYear currentMonthAndYear = monthAndYearScheduledDayMap.lastEntry().getKey();
            for (ScheduledDay scheduledDay : monthAndYearScheduledDayMap.lastEntry().getValue()) {
                grossRegularObHours += scheduledDay.getAccumulatedObAsRegularDay();
                grossHolidayObHours += scheduledDay.getAccumulatedObAsHoliday();
                grossRegularOverTimeHours += scheduledDay.getOrderedOvertimeWeekday();
                grossWeekendOverTimeHours += scheduledDay.getOrderedOvertimeWeekend();
                takenOutCompensationTime += scheduledDay.getCompensationTime();

                netRegularObHours += scheduledDay.getAccumulatedObAsRegularDay() * scheduledDay.getUnionContract().getObRegularCoeff();
                netHolidayObHours += scheduledDay.getAccumulatedObAsHoliday() * scheduledDay.getUnionContract().getObHolidayCoeff();
                netRegularOvertimeHours += scheduledDay.getOrderedOvertimeWeekday() * scheduledDay.getUnionContract().getOvertimeRegulaCoeff();
                netWeekendOvertimeHours += scheduledDay.getOrderedOvertimeWeekend() * scheduledDay.getUnionContract().getOvertimeHolidayCoeff();

            }

            grossTotal = netRegularObHours + netHolidayObHours + netRegularOvertimeHours + netWeekendOvertimeHours;
            paidOutCompensationTime = sumUpCompensationTimeFor(currentMonthAndYear);
            netTotal = grossTotal - paidOutCompensationTime - takenOutCompensationTime;
        }

        monthlyReport.getThisMonth().setGrossRegularObHours(grossRegularObHours);
        monthlyReport.getThisMonth().setGrossHolidayObHours(grossHolidayObHours);
        monthlyReport.getThisMonth().setNetRegularObHours(netRegularObHours);
        monthlyReport.getThisMonth().setNetHolidayObHours(netHolidayObHours);

        monthlyReport.getThisMonth().setGrossRegularOvertimeHours(grossRegularOverTimeHours);
        monthlyReport.getThisMonth().setGrossWeekendOvertimeHours(grossWeekendOverTimeHours);
        monthlyReport.getThisMonth().setNetRegularOvertimeHours(netRegularOvertimeHours);
        monthlyReport.getThisMonth().setNetWeekendOvertimeHours(netWeekendOvertimeHours);

        monthlyReport.getThisMonth().setPaidOutCompensationTime(paidOutCompensationTime);
        monthlyReport.getThisMonth().setTakenOutCompensationTime(takenOutCompensationTime);
        monthlyReport.getThisMonth().setNetTotal(netTotal);
        monthlyReport.setWithdrawals(withdrawals);
    }

    private double sumUpCompensationTimeFor(MonthAndYear currentMonthAndYear) {
        if (withdrawalDTOTreeMap.get(currentMonthAndYear) == null)
            return 0;

        double paidUpCompTimeThisMonth = 0;
        for (WithdrawalDTO withdrawal : withdrawalDTOTreeMap.get(currentMonthAndYear)) {
            paidUpCompTimeThisMonth += withdrawal.getAmount();
        }
        return paidUpCompTimeThisMonth;
    }

    private double sumUpCompensationTimeUpUntil(MonthAndYear monthAndYear) {
        double paidCompTime = 0;
        for (Map.Entry<MonthAndYear, List<WithdrawalDTO>> entry : withdrawalDTOTreeMap.entrySet())
            if (entry.getKey().compareTo(monthAndYear) < 1)
                for (WithdrawalDTO withdrawal : entry.getValue())
                    paidCompTime += withdrawal.getAmount();
        return paidCompTime;
    }
}
