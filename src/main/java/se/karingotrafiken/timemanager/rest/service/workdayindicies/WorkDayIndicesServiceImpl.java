package se.karingotrafiken.timemanager.rest.service.workdayindicies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import se.karingotrafiken.timemanager.rest.dto.stored.WorkDayIndexDTO;
import se.karingotrafiken.timemanager.rest.entitys.Shift;
import se.karingotrafiken.timemanager.rest.entitys.WorkDayIndex;
import se.karingotrafiken.timemanager.rest.repository.WorkDayIndexRepository;
import se.karingotrafiken.timemanager.rest.service.AbstractCrudService;
import se.karingotrafiken.timemanager.rest.service.Translator;

// TODO: maby remove the WorkDayIndicesServiceImpl class and put it in shift service instead?
@Component
public class WorkDayIndicesServiceImpl extends AbstractCrudService<WorkDayIndexDTO, WorkDayIndex> implements WorkDayIndicesService {

    @Autowired
    private WorkDayIndexRepository workDayIndexRepository;

    @Override
    public void deleteByShiftId(long id) {
        workDayIndexRepository.deleteByShiftId(id);
    }

    @Override
    public CrudRepository<WorkDayIndex, Long> getRepository() {
        return workDayIndexRepository;
    }

    @Override
    public Translator<WorkDayIndexDTO, WorkDayIndex> getTranslator() {
        return new Translator<WorkDayIndexDTO, WorkDayIndex>() {
            @Override
            public WorkDayIndexDTO translateFromEntity(WorkDayIndex entity) {
                WorkDayIndexDTO dto = new WorkDayIndexDTO();
                dto.setId(entity.getId());
                dto.setDayIndex(entity.getDayIndex());
                dto.setShiftId(entity.getShift().getId());
                return dto;
            }

            @Override
            public WorkDayIndex translateFromDTO(WorkDayIndexDTO dto) {
                throw new RuntimeException("translateFromDTO() is not implemented in WorkDayIndicesService");
            }

            @Override
            public WorkDayIndex translateFromNestedDTO(WorkDayIndexDTO dto, Object parent) {
                if (parent instanceof Shift) {
                    Shift shift = (Shift) parent;

                    WorkDayIndex workDayIndex = new WorkDayIndex();
                    workDayIndex.setShift(shift);
                    workDayIndex.setId(dto.getId());
                    workDayIndex.setDayIndex(dto.getDayIndex());
                    return workDayIndex;

                } else
                    throw new RuntimeException(parent.getClass().getName() + " is not instance of Shift");
            }
        };
    }
}
