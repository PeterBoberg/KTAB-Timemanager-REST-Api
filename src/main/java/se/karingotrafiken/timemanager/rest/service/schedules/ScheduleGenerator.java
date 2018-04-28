package se.karingotrafiken.timemanager.rest.service.schedules;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Interval;
import org.springframework.stereotype.Component;
import se.karingotrafiken.timemanager.rest.appmodel.common.DayType;
import se.karingotrafiken.timemanager.rest.dto.nonstored.request.SchedulingRequest;
import se.karingotrafiken.timemanager.rest.dto.stored.ErrorMessageDTO;
import se.karingotrafiken.timemanager.rest.entitys.*;
import se.karingotrafiken.timemanager.rest.exceptions.ApiException;
import se.karingotrafiken.timemanager.rest.utils.DateTimeUtils;

import java.util.*;

@Component
public class ScheduleGenerator {

    private SchedulingRequest schedulingRequest;
    private Employee employee;
    private Shift shift;
    private HashMap<Date, Holiday> holidays;
    private List<UnionContract> unionContracts;

    private ScheduleGenerator() {
    }

    public List<ScheduledDay> generateScheduledDays() {

        List<ScheduledDay> scheduledDays = new ArrayList<>();

        DateTime scheduleEnd = new DateTime(schedulingRequest.getEndDate());
        Set<WorkDayIndex> workDayIndices = shift.getWorkDayIndices();
        Set<ShiftDayPrototype> shiftDayPrototypes = shift.getShiftDayPrototypes();

        int jumpOnIndex = findJumpOnIndex(workDayIndices);
        int jumpOffIndex = findJumpOffIndex(workDayIndices);

        DateTime currentDate = new DateTime(schedulingRequest.getStartDate());
        while (currentDate.isBefore(scheduleEnd.plusDays(1))) {

            // Gets the current day index for this iteration
            int currentIndex = getCurrentDayIndex(shift, currentDate);

            ScheduledDay scheduledDay;
            if (isWorkingDay(currentIndex, workDayIndices)) {
                // This is a working day in the shift
                ShiftDayPrototype prototype = findPrototype(currentIndex, currentDate, jumpOnIndex, jumpOffIndex, shiftDayPrototypes);
                scheduledDay = constructScheduledDayFor(employee, prototype, currentDate);
                scheduledDay.setBoat(shift.getBoat());
            } else {
                scheduledDay = constructFreeDayFor(employee, currentDate);
            }
            scheduledDays.add(scheduledDay);
            currentDate = currentDate.plusDays(1);
        }
        scheduledDays.forEach(scheduledDay -> {
            UnionContract contract = findContract(scheduledDay.getDate());
            if (contract == null)
                throw new ApiException(ErrorMessageDTO.ErrorCode.INVALID_UNION_CONTRACT_CONFIGURATION, "Inget kollektivavtal är definierat för tjänstekategorin "
                        + employee.getServiceCategory().getName() + " för datumet" + se.karingotrafiken.timemanager.rest.utils.DateTimeUtils.toDateString(scheduledDay.getDate()));
            scheduledDay.setUnionContract(contract);
            scheduledDay.setBoat(shift.getBoat());
        });
        return scheduledDays;
    }

    private ScheduledDay constructFreeDayFor(Employee employee, DateTime currentDate) {
        ScheduledDay scheduledDay = new ScheduledDay();
        scheduledDay.setEmployee(employee);
        scheduledDay.setDate(currentDate.toDate());
        scheduledDay.setWeekday(currentDate.dayOfWeek().getAsText());
        if (isHoliday(currentDate))
            scheduledDay.setHoliday(true);
        return scheduledDay;
    }

    private ScheduledDay constructScheduledDayFor(Employee employee, ShiftDayPrototype prototype, DateTime currentDate) {
        ScheduledDay scheduledDay = new ScheduledDay();
        scheduledDay.setEmployee(employee);
        scheduledDay.setDate(currentDate.toDate());
        scheduledDay.setWeekday(currentDate.dayOfWeek().getAsText());
        scheduledDay.setStartTimeOfDay(prototype.getStartTimeOfDay());
        scheduledDay.setEndTimeOfDay(prototype.getEndTimeOfDay());
        scheduledDay.setNetHours(prototype.getNetHours());
        AccumulatedObHours accumulatedObHours = findObHoursForEmployee(prototype, employee);
        if (isHoliday(currentDate)) {
            scheduledDay.setAccumulatedObAsHoliday(accumulatedObHours.getAccumulatedHours());
            scheduledDay.setHoliday(true);
        } else {
            scheduledDay.setAccumulatedObAsRegularDay(accumulatedObHours.getAccumulatedHours());
        }
        return scheduledDay;
    }

    private AccumulatedObHours findObHoursForEmployee(ShiftDayPrototype prototype, Employee employee) {
        ServiceCategory serviceCategory = employee.getServiceCategory();
        for (AccumulatedObHours obHours : prototype.getAccumulatedObHours())
            if (obHours.getServiceCategory().equals(serviceCategory))
                return obHours;
        throw new ApiException(ErrorMessageDTO.ErrorCode.INVALID_SHIFT_CONFIGURATION, "Kunde inte hitta accumulerade OB timmar för den sökta arbetskategorin");
    }

    private ShiftDayPrototype findPrototype(int currentIndex, DateTime currentDate, int jumpOnIndex, int jumpOffIndex, Set<ShiftDayPrototype> shiftDayPrototypes) {
        DayType dayType = null;
        if (isSwitchDay(currentIndex, jumpOnIndex, jumpOffIndex)) {
            if (currentIndex == jumpOnIndex)
                dayType = DayType.START_DAY;
            if (currentIndex == jumpOffIndex)
                dayType = DayType.END_DAY;
        } else if (isHoliday(currentDate)) {
            dayType = DayType.HOLIDAY;

        } else {
            dayType = DayType.valueOf(currentDate.dayOfWeek().getAsText().toUpperCase());
        }

        for (ShiftDayPrototype prototype : shiftDayPrototypes) {
            if (prototype.getDayType().equals(dayType))
                return prototype;
        }

        throw new ApiException(ErrorMessageDTO.ErrorCode.INVALID_SHIFT_CONFIGURATION, "Kunde inte schemalägga." +
                "Dagsprototypen " + dayType + " existerar inte för detta skift");
    }

    private boolean isHoliday(DateTime currentDate) {
        return holidays.get(currentDate.toDate()) != null;
    }

    private boolean isSwitchDay(int currentIndex, int jumpOnIndex, int jumpOffIndex) {
        return currentIndex == jumpOnIndex || currentIndex == jumpOffIndex;
    }

    private int findJumpOffIndex(Set<WorkDayIndex> workDayIndices) {
        if (workDayIndices.size() > 0) {
            int jumpOffIndex = 0;
            for (WorkDayIndex index : workDayIndices)
                if (index.getDayIndex() > jumpOffIndex)
                    jumpOffIndex = index.getDayIndex();
            return jumpOffIndex;
        }
        return 1;
    }

    private int findJumpOnIndex(Set<WorkDayIndex> workDayIndices) {
        if (workDayIndices.size() > 0) {
            int jumpOnIndex = Integer.MAX_VALUE;
            for (WorkDayIndex index : workDayIndices)
                if (index.getDayIndex() < jumpOnIndex)
                    jumpOnIndex = index.getDayIndex();
            return jumpOnIndex;
        }
        return 1;
    }


    private boolean isWorkingDay(int currentIndex, Set<WorkDayIndex> workDayIndices) {
        for (WorkDayIndex index : workDayIndices)
            if (index.getDayIndex() == currentIndex)
                return true;

        return false;
    }

    private int getCurrentDayIndex(Shift shift, DateTime currentDate) {
        DateTime repicationStart = new DateTime(DateTimeUtils.dateFromDateString(schedulingRequest.getReplicationStart()));
        int days = Days.daysBetween(repicationStart, currentDate).getDays();
        int lengthInDay = shift.getLengthInDays();
        return (days % lengthInDay) + 1;
    }

    private UnionContract findContract(Date date) {
        DateTime searchedDate = new DateTime(date);
        for (UnionContract contract : unionContracts) {
            Interval interval = new Interval(new DateTime(contract.getStartDate()), new DateTime(contract.getEndDate()));
            if (interval.contains(searchedDate))
                return contract;
        }
        return null;
    }

    static class Builder {

        private ScheduleGenerator scheduleGenerator;

        public Builder() {
            scheduleGenerator = new ScheduleGenerator();
        }

        public Builder setRequest(SchedulingRequest request) {
            scheduleGenerator.schedulingRequest = request;
            return this;
        }

        public Builder setEmployee(Employee employee) {
            scheduleGenerator.employee = employee;
            return this;
        }

        public Builder setShift(Shift shift) {
            scheduleGenerator.shift = shift;
            return this;
        }

        public Builder setHolidays(HashMap<Date, Holiday> holidays) {
            scheduleGenerator.holidays = holidays;
            return this;
        }

        public Builder setApplicableContracts(List<UnionContract> applicableContracts) {
            scheduleGenerator.unionContracts = applicableContracts;
            return this;
        }

        public ScheduleGenerator build() {
            if (scheduleGenerator.schedulingRequest == null)
                throw new IllegalStateException("Illegal state, ScheduleGenerator must have a scheduling request set");
            if (scheduleGenerator.employee == null)
                throw new IllegalStateException("Illegal state, ScheduleGenerator must have employee set");
            if (scheduleGenerator.shift == null)
                throw new IllegalStateException("Illegal state, ScheduleGenerator must have shift set");
            if (scheduleGenerator.holidays == null)
                throw new IllegalStateException("Illegal state, ScheduleGenerator must have holidays set");
            if (scheduleGenerator.unionContracts == null)
                throw new IllegalStateException("Illegal state, ScheduleGenerator must have union contracts set");

            return scheduleGenerator;
        }
    }
}
