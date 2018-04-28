package se.karingotrafiken.timemanager.rest.dto.nonstored.response;

import se.karingotrafiken.timemanager.rest.dto.stored.WithdrawalDTO;

import java.util.ArrayList;
import java.util.List;

public class MonthlyReportResponse {

    private List<WithdrawalDTO> withdrawals = new ArrayList<>();
    private List<PreviousTotal> previousTotals = new ArrayList<>();
    private Summary thisMonth = new Summary();
    private Summary inbound = new Summary();

    public List<WithdrawalDTO> getWithdrawals() {
        return withdrawals;
    }

    public void setWithdrawals(List<WithdrawalDTO> withdrawals) {
        this.withdrawals = withdrawals;
    }

    public List<PreviousTotal> getPreviousTotals() {
        return previousTotals;
    }

    public void setPreviousTotals(List<PreviousTotal> previousTotals) {
        this.previousTotals = previousTotals;
    }

    public Summary getThisMonth() {
        return thisMonth;
    }

    public void setThisMonth(Summary thisMonth) {
        this.thisMonth = thisMonth;
    }

    public Summary getInbound() {
        return inbound;
    }

    public void setInbound(Summary inbound) {
        this.inbound = inbound;
    }

    public class Summary {
        private double grossRegularObHours;
        private double grossHolidayObHours;
        private double netRegularObHours;
        private double netHolidayObHours;

        private double grossRegularOvertimeHours;
        private double grossWeekendOvertimeHours;
        private double netRegularOvertimeHours;
        private double netWeekendOvertimeHours;

        private double paidOutCompensationTime;
        private double takenOutCompensationTime;
        private double netTotal;


        public double getGrossRegularObHours() {
            return grossRegularObHours;
        }

        public void setGrossRegularObHours(double grossRegularObHours) {
            this.grossRegularObHours = grossRegularObHours;
        }

        public double getGrossHolidayObHours() {
            return grossHolidayObHours;
        }

        public void setGrossHolidayObHours(double grossHolidayObHours) {
            this.grossHolidayObHours = grossHolidayObHours;
        }

        public double getNetRegularObHours() {
            return netRegularObHours;
        }

        public void setNetRegularObHours(double netRegularObHours) {
            this.netRegularObHours = netRegularObHours;
        }

        public double getNetHolidayObHours() {
            return netHolidayObHours;
        }

        public void setNetHolidayObHours(double netHolidayObHours) {
            this.netHolidayObHours = netHolidayObHours;
        }

        public double getGrossRegularOvertimeHours() {
            return grossRegularOvertimeHours;
        }

        public void setGrossRegularOvertimeHours(double grossRegularOvertimeHours) {
            this.grossRegularOvertimeHours = grossRegularOvertimeHours;
        }

        public double getGrossWeekendOvertimeHours() {
            return grossWeekendOvertimeHours;
        }

        public void setGrossWeekendOvertimeHours(double grossWeekendOvertimeHours) {
            this.grossWeekendOvertimeHours = grossWeekendOvertimeHours;
        }

        public double getNetRegularOvertimeHours() {
            return netRegularOvertimeHours;
        }

        public void setNetRegularOvertimeHours(double netRegularOvertimeHours) {
            this.netRegularOvertimeHours = netRegularOvertimeHours;
        }

        public double getNetWeekendOvertimeHours() {
            return netWeekendOvertimeHours;
        }

        public void setNetWeekendOvertimeHours(double netWeekendOvertimeHours) {
            this.netWeekendOvertimeHours = netWeekendOvertimeHours;
        }

        public double getPaidOutCompensationTime() {
            return paidOutCompensationTime;
        }

        public void setPaidOutCompensationTime(double paidOutCompensationTime) {
            this.paidOutCompensationTime = paidOutCompensationTime;
        }

        public double getTakenOutCompensationTime() {
            return takenOutCompensationTime;
        }

        public void setTakenOutCompensationTime(double takenOutCompensationTime) {
            this.takenOutCompensationTime = takenOutCompensationTime;
        }

        public double getNetTotal() {
            return netTotal;
        }

        public void setNetTotal(double netTotal) {
            this.netTotal = netTotal;
        }
    }
}
