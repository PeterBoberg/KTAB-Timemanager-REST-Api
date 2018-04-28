package se.karingotrafiken.timemanager.rest.service.holidays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import se.karingotrafiken.timemanager.rest.entitys.Holiday;
import se.karingotrafiken.timemanager.rest.repository.HolidayRepository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Component
public class HolidayServiceImpl implements HolidayService {

    @Autowired
    private HolidayRepository holidayRepository;

    @Override
    @Transactional
    public HashMap<Date, Holiday> getHolidaysBetween(Date fromDate, Date toDate) {
        List<Holiday> holidays = holidayRepository.findByDateBetween(fromDate, toDate);
        HashMap<Date, Holiday> holidayMap = new HashMap<>();
        holidays.forEach(holiday -> {
            holidayMap.put(holiday.getDate(), holiday);
        });
        return holidayMap;
    }
}
