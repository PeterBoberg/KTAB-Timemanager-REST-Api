package se.karingotrafiken.timemanager.rest.service.shifts;

import org.springframework.stereotype.Service;
import se.karingotrafiken.timemanager.rest.dto.stored.ShiftDTO;
import se.karingotrafiken.timemanager.rest.entitys.Shift;
import se.karingotrafiken.timemanager.rest.service.StrongEntityCrudService;

@Service
public interface ShiftService extends StrongEntityCrudService<ShiftDTO, Shift>{
}
