package se.karingotrafiken.timemanager.rest.service.shiftdayprototype;

import org.springframework.stereotype.Service;
import se.karingotrafiken.timemanager.rest.dto.stored.ShiftDayPrototypeDTO;
import se.karingotrafiken.timemanager.rest.entitys.ShiftDayPrototype;
import se.karingotrafiken.timemanager.rest.service.GenericCrudService;

import java.util.List;

@Service
public interface ShiftDayPrototypeService extends GenericCrudService<ShiftDayPrototypeDTO, ShiftDayPrototype> {

    List<ShiftDayPrototypeDTO> getByShiftId(long shiftId);
}
