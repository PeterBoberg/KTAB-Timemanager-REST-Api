package se.karingotrafiken.timemanager.rest.service.workdayindicies;

import org.springframework.stereotype.Service;
import se.karingotrafiken.timemanager.rest.dto.stored.WorkDayIndexDTO;
import se.karingotrafiken.timemanager.rest.entitys.WorkDayIndex;
import se.karingotrafiken.timemanager.rest.service.GenericCrudService;

@Service
public interface WorkDayIndicesService extends GenericCrudService<WorkDayIndexDTO, WorkDayIndex> {

    void deleteByShiftId(long id);
}
