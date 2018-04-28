package se.karingotrafiken.timemanager.rest.service;

import org.springframework.stereotype.Service;
import se.karingotrafiken.timemanager.rest.dto.DtoObject;
import se.karingotrafiken.timemanager.rest.entitys.DbEntity;

import java.util.List;

@Service
public interface StrongEntityCrudService<DTO extends DtoObject, Entity extends DbEntity> extends GenericCrudService<DTO, Entity> {

    List<DTO> getAll();
}
