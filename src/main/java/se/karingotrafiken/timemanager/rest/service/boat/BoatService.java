package se.karingotrafiken.timemanager.rest.service.boat;

import org.springframework.stereotype.Service;
import se.karingotrafiken.timemanager.rest.dto.stored.BoatDTO;
import se.karingotrafiken.timemanager.rest.entitys.Boat;
import se.karingotrafiken.timemanager.rest.service.StrongEntityCrudService;

@Service
public interface BoatService extends StrongEntityCrudService<BoatDTO, Boat> {

}
