package se.karingotrafiken.timemanager.rest.service.breakdate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import se.karingotrafiken.timemanager.rest.dto.BreakDateDTO;
import se.karingotrafiken.timemanager.rest.dto.stored.ErrorMessageDTO;
import se.karingotrafiken.timemanager.rest.entitys.BreakDate;
import se.karingotrafiken.timemanager.rest.exceptions.ApiException;
import se.karingotrafiken.timemanager.rest.repository.BreakDateRepository;
import se.karingotrafiken.timemanager.rest.service.AbstractStrongEntityCrudService;
import se.karingotrafiken.timemanager.rest.service.Translator;
import se.karingotrafiken.timemanager.rest.utils.DateTimeUtils;

@Component
public class BreakDateServiceImpl extends AbstractStrongEntityCrudService<BreakDateDTO, BreakDate> implements BreakDateService {

    @Autowired
    private BreakDateRepository breakDateRepository;

    @Override
    public CrudRepository<BreakDate, Long> getRepository() {
        return breakDateRepository;
    }

    @Override
    public Translator<BreakDateDTO, BreakDate> getTranslator() {
        return new Translator<BreakDateDTO, BreakDate>() {
            @Override
            public BreakDateDTO translateFromEntity(BreakDate entity) {
                BreakDateDTO dto = new BreakDateDTO();
                dto.setId(entity.getId());
                dto.setBreakDateType(entity.getBreakDateType());
                dto.setMonth(entity.getMonth());
                dto.setDay(entity.getDay());
                return dto;
            }

            @Override
            public BreakDate translateFromDTO(BreakDateDTO dto) {
                if (!DateTimeUtils.isValidMonthAndDay(dto.getMonth(), dto.getDay()))
                    throw new ApiException(ErrorMessageDTO.ErrorCode.INVALID_DATE_FORMAT, "Ogiltig månad och dag");

                BreakDate entity = new BreakDate();
                entity.setId(dto.getId());
                entity.setBreakDateType(dto.getBreakDateType());
                entity.setMonth(dto.getMonth());
                entity.setDay(dto.getDay());
                return entity;
            }
        };
    }
}
