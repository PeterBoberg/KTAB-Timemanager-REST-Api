package se.karingotrafiken.timemanager.rest.service.shiftdayprototype;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import se.karingotrafiken.timemanager.rest.dto.stored.AccumulatedObHoursDTO;
import se.karingotrafiken.timemanager.rest.dto.stored.ShiftDayPrototypeDTO;
import se.karingotrafiken.timemanager.rest.entitys.AccumulatedObHours;
import se.karingotrafiken.timemanager.rest.entitys.ServiceCategory;
import se.karingotrafiken.timemanager.rest.entitys.Shift;
import se.karingotrafiken.timemanager.rest.entitys.ShiftDayPrototype;
import se.karingotrafiken.timemanager.rest.repository.ShiftDayPrototypeRepository;
import se.karingotrafiken.timemanager.rest.service.AbstractCrudService;
import se.karingotrafiken.timemanager.rest.service.LogicalValidator;
import se.karingotrafiken.timemanager.rest.service.Translator;
import se.karingotrafiken.timemanager.rest.utils.DateTimeUtils;

import java.util.List;

// TODO: Maby remove the ShiftDayPrototypeServiceImpl class and put in in shift service instead?
@Component
public class ShiftDayPrototypeServiceImpl extends AbstractCrudService<ShiftDayPrototypeDTO, ShiftDayPrototype> implements ShiftDayPrototypeService {


    @Autowired
    private ShiftDayPrototypeRepository shiftDayPrototypeRepository;

    @Override
    @Transactional
    public List<ShiftDayPrototypeDTO> getByShiftId(long shiftId) {
        List<ShiftDayPrototype> shiftDaysForShiftPrototype = shiftDayPrototypeRepository.findByShiftId(shiftId);
        return getTranslator().translateFromEntityList(shiftDaysForShiftPrototype);
    }

    @Override
    public CrudRepository<ShiftDayPrototype, Long> getRepository() {
        return shiftDayPrototypeRepository;
    }

    @Override
    public Translator<ShiftDayPrototypeDTO, ShiftDayPrototype> getTranslator() {
        return new Translator<ShiftDayPrototypeDTO, ShiftDayPrototype>() {
            @Override
            public ShiftDayPrototypeDTO translateFromEntity(ShiftDayPrototype shiftDayPrototype) {
                ShiftDayPrototypeDTO prototypeDTO = new ShiftDayPrototypeDTO();
                prototypeDTO.setId(shiftDayPrototype.getId());
                prototypeDTO.setShiftId(shiftDayPrototype.getShift().getId());
                prototypeDTO.setStartTimeOfDay(DateTimeUtils.toTimestring(shiftDayPrototype.getStartTimeOfDay()));
                prototypeDTO.setEndTimeOfDay(DateTimeUtils.toTimestring(shiftDayPrototype.getEndTimeOfDay()));
                prototypeDTO.setNetHours(shiftDayPrototype.getNetHours());
                prototypeDTO.setDayType(shiftDayPrototype.getDayType());
                for (AccumulatedObHours hours : shiftDayPrototype.getAccumulatedObHours()) {
                    AccumulatedObHoursDTO accumulatedObHoursDTO = new AccumulatedObHoursDTO();
                    accumulatedObHoursDTO.setId(hours.getId());
                    accumulatedObHoursDTO.setShiftDayPrototypeId(shiftDayPrototype.getId());
                    accumulatedObHoursDTO.setAccumulatedHours(hours.getAccumulatedHours());
                    accumulatedObHoursDTO.setServiceCategoryId(hours.getServiceCategory().getId());
                    prototypeDTO.getAccumulatedObHours().add(accumulatedObHoursDTO);

                }
                return prototypeDTO;
            }

            @Override
            public ShiftDayPrototype translateFromDTO(ShiftDayPrototypeDTO shiftDayPrototypeDTO) {
                throw new RuntimeException("translateFromDTO() not implemented in ShiftDayPrototypeService");
            }

            @Override
            public ShiftDayPrototype translateFromNestedDTO(ShiftDayPrototypeDTO shiftDayPrototypeDTO, Object parent) {
                if (parent instanceof Shift) {
                    Shift shift = (Shift) parent;

                    ShiftDayPrototype prototype = new ShiftDayPrototype();
                    prototype.setId(shiftDayPrototypeDTO.getId());
                    prototype.setShift(shift);
                    prototype.setStartTimeOfDay(DateTimeUtils.dateFromTimeString(shiftDayPrototypeDTO.getStartTimeOfDay()));
                    prototype.setEndTimeOfDay(DateTimeUtils.dateFromTimeString(shiftDayPrototypeDTO.getEndTimeOfDay()));
                    prototype.setNetHours(shiftDayPrototypeDTO.getNetHours());
                    prototype.setDayType(shiftDayPrototypeDTO.getDayType());
                    for (AccumulatedObHoursDTO hoursDTO : shiftDayPrototypeDTO.getAccumulatedObHours()) {
                        AccumulatedObHours accumulatedObHours = new AccumulatedObHours();
                        accumulatedObHours.setId(hoursDTO.getId());
                        accumulatedObHours.setAccumulatedHours(hoursDTO.getAccumulatedHours());

                        ServiceCategory serviceCategory = new ServiceCategory();
                        serviceCategory.setId(hoursDTO.getServiceCategoryId());

                        accumulatedObHours.setServiceCategory(serviceCategory);
                        accumulatedObHours.setShiftDayPrototype(prototype);

                        prototype.getAccumulatedObHours().add(accumulatedObHours);
                    }
                    return prototype;

                } else {
                    throw new RuntimeException(parent.getClass().getName() + "is not instance of Shift");
                }
            }
        };

    }

    @Override
    public LogicalValidator<ShiftDayPrototypeDTO> getLogicalValidator() {
        return shiftDayPrototypeDTO -> {

        };
    }
}
