package se.karingotrafiken.timemanager.rest.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import se.karingotrafiken.timemanager.rest.dto.DtoObject;
import se.karingotrafiken.timemanager.rest.entitys.DbEntity;

import java.util.ArrayList;
import java.util.List;

@Component
public abstract class AbstractStrongEntityCrudService<DTO extends DtoObject, Entity extends DbEntity> extends AbstractCrudService<DTO, Entity> implements StrongEntityCrudService<DTO, Entity> {

    @Transactional
    public List<DTO> getAll() {
        List<DTO> list = new ArrayList<>();
        getRepository().findAll().forEach(entity -> list.add(getTranslator().translateFromEntity(entity)));
        return list;
    }
}
