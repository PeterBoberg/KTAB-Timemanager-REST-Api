package se.karingotrafiken.timemanager.rest.service.boat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import se.karingotrafiken.timemanager.rest.dto.stored.BoatDTO;
import se.karingotrafiken.timemanager.rest.entitys.Boat;
import se.karingotrafiken.timemanager.rest.repository.BoatRepository;
import se.karingotrafiken.timemanager.rest.service.AbstractStrongEntityCrudService;
import se.karingotrafiken.timemanager.rest.service.Translator;

@Component
public class BoatServiceImpl extends AbstractStrongEntityCrudService<BoatDTO, Boat> implements BoatService {

    @Autowired
    private BoatRepository boatRepository;

    @Override
    public CrudRepository<Boat, Long> getRepository() {
        return boatRepository;
    }

    @Override
    public Translator<BoatDTO, Boat> getTranslator() {
        return new Translator<BoatDTO, Boat>() {
            @Override
            public BoatDTO translateFromEntity(Boat boat) {
                BoatDTO dto = new BoatDTO();
                dto.setId(boat.getId());
                dto.setName(boat.getName());
                return dto;
            }

            @Override
            public Boat translateFromDTO(BoatDTO dto) {
                Boat boat = new Boat();
                boat.setId(dto.getId());
                boat.setName(dto.getName());
                return boat;
            }
        };
    }
}
