package se.karingotrafiken.timemanager.rest.service;

import org.springframework.stereotype.Service;
import se.karingotrafiken.timemanager.rest.dto.DtoObject;
import se.karingotrafiken.timemanager.rest.entitys.DbEntity;

@Service
public interface GenericCrudService<DTO extends DtoObject, Entity extends DbEntity> {

    DTO create(DTO dto);

    DTO getById(long id);

    DTO update(DTO dto);

    void deleteById(long id);

    Translator<DTO, Entity> getTranslator();

    LogicalValidator<DTO> getLogicalValidator();
}

