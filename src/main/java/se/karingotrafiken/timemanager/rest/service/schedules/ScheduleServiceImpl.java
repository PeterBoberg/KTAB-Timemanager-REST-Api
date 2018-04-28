package se.karingotrafiken.timemanager.rest.service.schedules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import se.karingotrafiken.timemanager.rest.appmodel.common.Role;
import se.karingotrafiken.timemanager.rest.dto.nonstored.request.ChangeScheduledDayRequest;
import se.karingotrafiken.timemanager.rest.dto.nonstored.request.CommentRequestDTO;
import se.karingotrafiken.timemanager.rest.dto.nonstored.request.SchedulingRequest;
import se.karingotrafiken.timemanager.rest.dto.nonstored.request.SwapScheduledDayRequest;
import se.karingotrafiken.timemanager.rest.dto.nonstored.response.CheckScheduleResponse;
import se.karingotrafiken.timemanager.rest.dto.stored.BoatDTO;
import se.karingotrafiken.timemanager.rest.dto.stored.ErrorMessageDTO;
import se.karingotrafiken.timemanager.rest.dto.stored.ScheduledDayDTO;
import se.karingotrafiken.timemanager.rest.entitys.*;
import se.karingotrafiken.timemanager.rest.exceptions.ApiException;
import se.karingotrafiken.timemanager.rest.repository.EmployeeRepository;
import se.karingotrafiken.timemanager.rest.repository.ScheduledDayRepository;
import se.karingotrafiken.timemanager.rest.repository.ShiftRepository;
import se.karingotrafiken.timemanager.rest.repository.UnionContractRepository;
import se.karingotrafiken.timemanager.rest.service.Translator;
import se.karingotrafiken.timemanager.rest.service.holidays.HolidayService;
import se.karingotrafiken.timemanager.rest.service.users.UserService;
import se.karingotrafiken.timemanager.rest.utils.DateTimeUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * THis class is not a regular CRUD-service since it accepts different parameterns than the regular DTO/Entity pattern
 * used in the other CRUD services. Therefore ,this class acts a stand alone service.
 */

@Component
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduledDayRepository scheduledDayRepository;

    @Autowired
    private ShiftRepository shiftRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private HolidayService holidayService;

    @Autowired
    private UnionContractRepository unionContractRepository;

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public void createScheduleFrom(SchedulingRequest req) {

        Date fromDate = DateTimeUtils.dateFromDateString(req.getStartDate());
        Date toDate = DateTimeUtils.dateFromDateString(req.getEndDate());
        Shift shift = shiftRepository.findOne(req.getShiftId());
        Employee employee = employeeRepository.findOne(req.getEmployeeId());
        Date replicationStart = DateTimeUtils.dateFromDateString(req.getReplicationStart());

        if (shift == null)
            throw new ApiException(ErrorMessageDTO.ErrorCode.RESOURCE_NOT_FOUND, "Skift med id " + req.getShiftId() + " existerar inte");

        if (employee == null)
            throw new ApiException(ErrorMessageDTO.ErrorCode.RESOURCE_NOT_FOUND, "Anställd med id " + req.getEmployeeId() + " existerar inte");

        if (fromDate.compareTo(replicationStart) < 0)
            throw new ApiException(ErrorMessageDTO.ErrorCode.INVALID_SCHEDULING, "Kan inte schemalägga en anställd före replikeringsdatumet "
                    + replicationStart + "på " + shift.getName());

        scheduledDayRepository.deleteByDateBetweenAndEmployeeId(fromDate, toDate, req.getEmployeeId());
        HashMap<Date, Holiday> holidays = holidayService.getHolidaysBetween(fromDate, toDate);

        List<UnionContract> unionContracts = unionContractRepository.findByServiceCategoryIdOrderByStartDate(employee.getServiceCategory().getId());
        ScheduleGenerator generator = new ScheduleGenerator.Builder()
                .setEmployee(employee)
                .setShift(shift)
                .setRequest(req)
                .setHolidays(holidays)
                .setApplicableContracts(unionContracts)
                .build();

        List<ScheduledDay> scheduledDays = generator.generateScheduledDays();
        scheduledDayRepository.save(scheduledDays);
    }

    @Override
    @Transactional
    public ScheduledDayDTO getById(long id) {
        ScheduledDay scheduledDay = findById(id);
        return getTranslator().translateFromEntity(scheduledDay);
    }

    @Override
    @Transactional
    public List<ScheduledDayDTO> getByEmployeeId(long employeeId) {
        List<ScheduledDay> scheduledDays = scheduledDayRepository.findByEmployeeIdOrderByDateAsc(employeeId);
        return getTranslator().translateFromEntityList(scheduledDays);
    }

    @Override
    @Transactional
    public List<ScheduledDayDTO> getByEmployeeIdAndYearAndMonth(long employeeId, int year, int month) {
        Date firstDayOfMonth = DateTimeUtils.firstDayOfMonth(year, month);
        Date lastDayOfMonth = DateTimeUtils.lastDayOfMonth(year, month);
        List<ScheduledDay> scheduledDays = scheduledDayRepository.findByDateBetweenAndEmployeeIdOrderByDateAsc(firstDayOfMonth, lastDayOfMonth, employeeId);
        return getTranslator().translateFromEntityList(scheduledDays);
    }

    @Override
    @Transactional
    public CheckScheduleResponse checkScheduledDaysBetween(String fromDate, String toDate, long employeeId) {
        if (fromDate == null || toDate == null)
            throw new ApiException(ErrorMessageDTO.ErrorCode.INVALID_DATE_FORMAT, "Till och från datum måste finnas");

        Date firstDate = DateTimeUtils.dateFromDateString(fromDate);
        Date lastDate = DateTimeUtils.dateFromDateString(toDate);
        List<ScheduledDay> scheduledDays = scheduledDayRepository.findByDateBetweenAndEmployeeIdOrderByDateAsc(firstDate, lastDate, employeeId);
        if (scheduledDays.size() > 0) {
            ScheduledDay firstDay = scheduledDays.get(0);
            ScheduledDay lastDay = scheduledDays.get(scheduledDays.size() - 1);
            return new CheckScheduleResponse(employeeId, DateTimeUtils.toDateString(firstDay.getDate()), DateTimeUtils.toDateString(lastDay.getDate()));
        }
        return null;
    }

    @Override
    @Transactional
    public void swapScheduledDates(SwapScheduledDayRequest swapDayRequest) {
        Date date = DateTimeUtils.dateFromDateString(swapDayRequest.getDate());
        ScheduledDay shDay1 = scheduledDayRepository.findByDateAndEmployeeId(date, swapDayRequest.getAskingEmployeeId());
        ScheduledDay shDay2 = scheduledDayRepository.findByDateAndEmployeeId(date, swapDayRequest.getAskedEmployeeId());
        if (shDay1 == null || shDay2 == null)
            throw new ApiException(ErrorMessageDTO.ErrorCode.RESOURCE_NOT_FOUND, "Kan inte byta dag eftersom någon utan personerna inte är schemalgda denna dag");

        Employee askingEmpl = shDay1.getEmployee();
        Employee askedEmpl = shDay2.getEmployee();

        if (askedEmpl.getServiceCategory().equals(askingEmpl.getServiceCategory())) {
            shDay1.setEmployee(askedEmpl);
            shDay2.setEmployee(askingEmpl);
            scheduledDayRepository.save(Arrays.asList(shDay1, shDay2));
        } else
            throw new ApiException(ErrorMessageDTO.ErrorCode.INVALID_SCHEDULE_SWAP, "Kan inte byta dag eftesom den efterfrågande är " + askingEmpl.getServiceCategory().getName() +
                    "och den eftefrågade är " + askedEmpl.getServiceCategory().getName());
    }

    @Override
    @Transactional
    public ScheduledDayDTO updateScheduledDay(long scheduledDayId, ChangeScheduledDayRequest changeRequest) {
        ScheduledDay updatable = findById(scheduledDayId);
        ScheduledDayTranslator translator = getTranslator();
        translator.updateScheduledDayWith(changeRequest, updatable);
        return translator.translateFromEntity(scheduledDayRepository.save(updatable));
    }

    @Override
    @Transactional
    public ScheduledDayDTO addCommentForDay(long employeeId, CommentRequestDTO commentRequest) {
        User currentUser = userService.getCurrentUser();
        ScheduledDay day = findById(commentRequest.getScheduledDayId());
        if (!currentUser.hasRole(Role.ROLE_ADMIN) && currentUser.getId() != day.getEmployee().getUser().getId())
            throw new ApiException(ErrorMessageDTO.ErrorCode.AUTHENTICATION_FAILURE, "Du har inte behörighet att modifiera någon annans dagar");

        day.setComment(commentRequest.getComment());
        return getTranslator().translateFromEntity(scheduledDayRepository.save(day));
    }

    @Override
    @Transactional
    public ScheduledDayDTO resetDay(long id) {
        ScheduledDay freeDay = findById(id);
        freeDay.setStartTimeOfDay(null);
        freeDay.setEndTimeOfDay(null);
        freeDay.setNetHours(0);
        freeDay.setAccumulatedObAsRegularDay(0);
        freeDay.setAccumulatedObAsHoliday(0);
        freeDay.setOrderedOvertimeWeekday(0);
        freeDay.setOrderedOvertimeWeekend(0);
        freeDay.setCompensationTime(0);
        freeDay.setComment(null);

        return getTranslator().translateFromEntity(scheduledDayRepository.save(freeDay));

    }

    private ScheduledDay findById(long id) {
        ScheduledDay scheduledDay = scheduledDayRepository.findOne(id);
        if (scheduledDay == null)
            throw new ApiException(ErrorMessageDTO.ErrorCode.RESOURCE_NOT_FOUND, "Shemalagd dag med id" + id + "existerar inte");
        return scheduledDay;
    }

    private ScheduledDayTranslator getTranslator() {
        return new ScheduledDayTranslator();
    }

    private class ScheduledDayTranslator extends Translator<ScheduledDayDTO, ScheduledDay> {

        @Override
        public ScheduledDayDTO translateFromEntity(ScheduledDay entity) {
            ScheduledDayDTO dto = new ScheduledDayDTO();
            dto.setId(entity.getId());
            dto.setEmployeeId(entity.getEmployee().getId());
            dto.setDate(DateTimeUtils.toDateString(entity.getDate()));

            // Setting time manually since we cant use genson to write both dates and time strings
            if (entity.getStartTimeOfDay() != null)
                dto.setStartTimeOfDay(DateTimeUtils.toTimestring(entity.getStartTimeOfDay()));

            if (entity.getEndTimeOfDay() != null)
                dto.setEndTimeOfDay(DateTimeUtils.toTimestring(entity.getEndTimeOfDay()));
            dto.setHoliday(entity.isHoliday());
            dto.setWeekday(entity.getWeekday());
            dto.setNetHours(entity.getNetHours());
            dto.setCompensationTime(entity.getCompensationTime());
            dto.setComment(entity.getComment());
            dto.setAccumulatedObAsRegularDay(entity.getAccumulatedObAsRegularDay());
            dto.setAccumulatedObAsHoliday(entity.getAccumulatedObAsHoliday());
            dto.setOrderedOvertimeAsWeekday(entity.getOrderedOvertimeWeekday());
            dto.setOrderedOvertimeAsWeekend(entity.getOrderedOvertimeWeekend());
            dto.setUnionContractId(entity.getUnionContract().getId());

            BoatDTO boatDTO = new BoatDTO();
            boatDTO.setId(entity.getBoat().getId());
            boatDTO.setName(entity.getBoat().getName());
            dto.setBoat(boatDTO);
            return dto;
        }

        @Override
        public ScheduledDay translateFromDTO(ScheduledDayDTO dto) {
            throw new RuntimeException("translateFromDto() is not implemented in ScheduleService");
        }

        void updateScheduledDayWith(ChangeScheduledDayRequest request, ScheduledDay scheduledDay) {
            scheduledDay.setStartTimeOfDay(request.getStartTimeOfDay() == null ? null : DateTimeUtils.dateFromTimeString(request.getStartTimeOfDay()));
            scheduledDay.setEndTimeOfDay(request.getEndTimeOfDay() == null ? null : DateTimeUtils.dateFromTimeString(request.getEndTimeOfDay()));
            scheduledDay.setNetHours(request.getNetHours());
            scheduledDay.setAccumulatedObAsRegularDay(request.getAccumulatedObAsRegularDay());
            scheduledDay.setAccumulatedObAsHoliday(request.getAccumulatedObAsHoliday());
            scheduledDay.setOrderedOvertimeWeekday(request.getOrderedOvertimeAsWeekday());
            scheduledDay.setOrderedOvertimeWeekend(request.getOrderedOvertimeAsWeekend());
            scheduledDay.setCompensationTime(request.getCompensationTime());
            scheduledDay.setComment(request.getComment());

            Boat boat = new Boat();
            boat.setId(request.getBoat().getId());
            boat.setName(request.getBoat().getName());
            scheduledDay.setBoat(boat);
        }
    }
}
