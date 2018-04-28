package se.karingotrafiken.timemanager.rest.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import se.karingotrafiken.timemanager.rest.dto.DtoObject;
import se.karingotrafiken.timemanager.rest.dto.stored.ErrorMessageDTO;
import se.karingotrafiken.timemanager.rest.entitys.DbEntity;
import se.karingotrafiken.timemanager.rest.exceptions.ApiException;


@Component
public abstract class AbstractCrudService<DTO extends DtoObject, Entity extends DbEntity> implements GenericCrudService<DTO, Entity> {

    @Override
    @Transactional
    public DTO create(DTO dto) {
        try {
            getLogicalValidator().validate(dto);
            Entity entity = getRepository().save(getTranslator().translateFromDTO(dto));
            return getTranslator().translateFromEntity(entity);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            throw new ApiException(ErrorMessageDTO.ErrorCode.DUPLICATE_ENTRY, "Kan inte skapa nytt objekt efter som det redan existerar");
        }
    }

    @Override
    @Transactional
    public DTO getById(long id) {
        Entity entity = getRepository().findOne(id);
        if (entity == null)
            throw new ApiException(ErrorMessageDTO.ErrorCode.RESOURCE_NOT_FOUND, "Det sökta objektet kunde inte hittas");
        return getTranslator().translateFromEntity(entity);
    }

    @Override
    @Transactional
    public DTO update(DTO dto) {
        getLogicalValidator().validate(dto);
        Entity entity = getTranslator().translateFromDTO(dto);
        if (getRepository().exists(entity.getId())) {
            try {
                getRepository().save(entity);
                return getTranslator().translateFromEntity(entity);
            } catch (Exception e) {
                throw new ApiException(ErrorMessageDTO.ErrorCode.INVALID_OBJECT_MANIPULATION, "Kan inte uppdatera det aktuella objektet efter som andra delar utav applikationen är beroende av det");
            }
        } else
            throw new ApiException(ErrorMessageDTO.ErrorCode.RESOURCE_NOT_FOUND, "Kan inte uppdatera, det sökta objektet exsistetrar inte");
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        if (getRepository().exists(id)) {
            try {
                getRepository().delete(id);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ApiException(ErrorMessageDTO.ErrorCode.INVALID_OBJECT_MANIPULATION, "Kan inte ta bort det aktuella objektet efter som andra delar utav applikationen är beroende av det");
            }
        } else
            throw new ApiException(ErrorMessageDTO.ErrorCode.RESOURCE_NOT_FOUND, "Kan inte ta bort, det sökta objektet exsistetrar inte");
    }


    public abstract CrudRepository<Entity, Long> getRepository();

    @Override
    public abstract Translator<DTO, Entity> getTranslator();

    @Override
    public LogicalValidator<DTO> getLogicalValidator() {
        return dto -> {
            // The default implementation does no validation at all
        };
    }
}
