package se.karingotrafiken.timemanager.rest.service.breakdate;

import org.springframework.stereotype.Service;
import se.karingotrafiken.timemanager.rest.dto.BreakDateDTO;
import se.karingotrafiken.timemanager.rest.entitys.BreakDate;
import se.karingotrafiken.timemanager.rest.service.StrongEntityCrudService;

@Service
public interface BreakDateService extends StrongEntityCrudService<BreakDateDTO, BreakDate> {
}
