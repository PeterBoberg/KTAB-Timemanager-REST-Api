package se.karingotrafiken.timemanager.rest.service.holidays;

import org.springframework.stereotype.Service;
import se.karingotrafiken.timemanager.rest.entitys.Holiday;

import java.util.Date;
import java.util.HashMap;

@Service
public interface HolidayService {

    HashMap<Date, Holiday> getHolidaysBetween(Date startDate, Date longDate);
}
