package se.karingotrafiken.timemanager.rest.service.schedules;

import org.springframework.stereotype.Service;
import se.karingotrafiken.timemanager.rest.dto.nonstored.request.ChangeScheduledDayRequest;
import se.karingotrafiken.timemanager.rest.dto.nonstored.request.CommentRequestDTO;
import se.karingotrafiken.timemanager.rest.dto.nonstored.request.SchedulingRequest;
import se.karingotrafiken.timemanager.rest.dto.nonstored.request.SwapScheduledDayRequest;
import se.karingotrafiken.timemanager.rest.dto.nonstored.response.CheckScheduleResponse;
import se.karingotrafiken.timemanager.rest.dto.stored.ScheduledDayDTO;

import java.util.List;

@Service
public interface ScheduleService {

    void createScheduleFrom(SchedulingRequest shiftPeriod);

    List<ScheduledDayDTO> getByEmployeeId(long employeeId);

    List<ScheduledDayDTO> getByEmployeeIdAndYearAndMonth(long employeeId, int year, int month);

    CheckScheduleResponse checkScheduledDaysBetween(String fromDate, String toDate, long employeeId);

    void swapScheduledDates(SwapScheduledDayRequest swapDayRequest);

    ScheduledDayDTO updateScheduledDay(long scheduledDayId, ChangeScheduledDayRequest changeRequest);

    ScheduledDayDTO getById(long id);

    ScheduledDayDTO resetDay(long id);

    ScheduledDayDTO addCommentForDay(long employeeId, CommentRequestDTO commentRequest);
}
