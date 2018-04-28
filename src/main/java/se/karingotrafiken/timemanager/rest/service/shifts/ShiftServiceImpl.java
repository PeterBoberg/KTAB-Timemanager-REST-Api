package se.karingotrafiken.timemanager.rest.service.shifts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import se.karingotrafiken.timemanager.rest.appmodel.common.DayType;
import se.karingotrafiken.timemanager.rest.dto.stored.*;
import se.karingotrafiken.timemanager.rest.entitys.Boat;
import se.karingotrafiken.timemanager.rest.entitys.Shift;
import se.karingotrafiken.timemanager.rest.exceptions.ApiException;
import se.karingotrafiken.timemanager.rest.repository.BoatRepository;
import se.karingotrafiken.timemanager.rest.repository.ShiftRepository;
import se.karingotrafiken.timemanager.rest.service.AbstractStrongEntityCrudService;
import se.karingotrafiken.timemanager.rest.service.LogicalValidator;
import se.karingotrafiken.timemanager.rest.service.Translator;
import se.karingotrafiken.timemanager.rest.service.enums.ServiceCategoryService;
import se.karingotrafiken.timemanager.rest.service.shiftdayprototype.ShiftDayPrototypeService;
import se.karingotrafiken.timemanager.rest.service.workdayindicies.WorkDayIndicesService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ShiftServiceImpl extends AbstractStrongEntityCrudService<ShiftDTO, Shift> implements ShiftService {

    @Autowired
    private ShiftRepository shiftRepository;


    @Autowired
    private ServiceCategoryService serviceCategoryService;

    @Autowired
    private ShiftDayPrototypeService shiftDayPrototypeService;

    @Autowired
    private WorkDayIndicesService workDayIndicesService;

    @Autowired
    private BoatRepository boatRepository;

    @Override
    public CrudRepository<Shift, Long> getRepository() {
        return shiftRepository;
    }

    // Overridden in this class because it needs to perform deletion of previous nested objects before updating to new ones
    @Override
    @Transactional
    public ShiftDTO update(ShiftDTO shiftDTO) {
        getLogicalValidator().validate(shiftDTO);
        Shift shift = getTranslator().translateFromDTO(shiftDTO);
        if (shiftRepository.exists(shift.getId())) {
            try {
                shiftRepository.save(shift);
                return getTranslator().translateFromEntity(shift);
            } catch (Exception e) {
                throw new ApiException(ErrorMessageDTO.ErrorCode.INVALID_OBJECT_MANIPULATION, "Kan inte uppdatera skiftet eftersom andra delar utav applikationen är beroende utav det");
            }
        } else
            throw new ApiException(ErrorMessageDTO.ErrorCode.RESOURCE_NOT_FOUND, "Kan inte uppdatera skifet med id" + shiftDTO.getId() + "eftersom det inte existerar");
    }

    @Override
    public Translator<ShiftDTO, Shift> getTranslator() {
        return new Translator<ShiftDTO, Shift>() {
            @Override
            public ShiftDTO translateFromEntity(Shift shift) {
                BoatDTO boatDTO = new BoatDTO();
                boatDTO.setId(shift.getBoat().getId());
                boatDTO.setName(shift.getBoat().getName());

                ShiftDTO shiftDTO = new ShiftDTO();
                shiftDTO.setId(shift.getId());
                shiftDTO.setName(shift.getName());
                shiftDTO.setBoat(boatDTO);
                shiftDTO.setLengthInDays(shift.getLengthInDays());
                shiftDTO.setWorkDayIndices(workDayIndicesService.getTranslator().translateFromEntitySet(shift.getWorkDayIndices()));
                shiftDTO.setShiftDayPrototypes(shiftDayPrototypeService.getTranslator().translateFromEntitySet(shift.getShiftDayPrototypes()));
                return shiftDTO;
            }

            @Override
            public Shift translateFromDTO(ShiftDTO shiftDTO) {
                Boat boat = boatRepository.findOne(shiftDTO.getBoat().getId());
                if (boat == null)
                    throw new ApiException(ErrorMessageDTO.ErrorCode.RESOURCE_NOT_FOUND, "Båt med id " + shiftDTO.getBoat().getId() + " existerar inte");

                Shift shift = new Shift();
                shift.setId(shiftDTO.getId());
                shift.setName(shiftDTO.getName());
                shift.setBoat(boat);
                shift.setLengthInDays(shiftDTO.getLengthInDays());
                shift.setWorkDayIndices(workDayIndicesService.getTranslator().translateFromSetOfNestedDTOs(shiftDTO.getWorkDayIndices(), shift));
                shift.setShiftDayPrototypes(shiftDayPrototypeService.getTranslator().translateFromSetOfNestedDTOs(shiftDTO.getShiftDayPrototypes(), shift));
                return shift;
            }
        };
    }

    @Override
    public LogicalValidator<ShiftDTO> getLogicalValidator() {
        return shiftDTO -> {

            // Make share that there is exactly one shift day prototype for all cases
            List<DayType> dayTypes = new ArrayList<>(Arrays.asList(
                    DayType.MONDAY,
                    DayType.TUESDAY,
                    DayType.WEDNESDAY,
                    DayType.THURSDAY,
                    DayType.FRIDAY,
                    DayType.SATURDAY,
                    DayType.SUNDAY,
                    DayType.HOLIDAY,
                    DayType.START_DAY,
                    DayType.END_DAY
            ));

            // Remove existing name from day names list
            shiftDTO.getShiftDayPrototypes().forEach(prototype -> dayTypes.remove(prototype.getDayType()));

            // If day types is not empty after checking, it means that day prototypes are missing for some days
            if (!dayTypes.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                dayTypes.forEach(dayType -> sb.append(dayType).append(" "));
                throw new ApiException(ErrorMessageDTO.ErrorCode.INVALID_SHIFT_CONFIGURATION, "Kan inte skapa skift, följande dagsprototyper saknas " + sb);
            }

            // Check to see if accumulated ob hours are defined for all service categories
            List<ServiceCategoryDTO> serviceCategories = serviceCategoryService.getAll();

            for (ShiftDayPrototypeDTO shiftDayPrototype : shiftDTO.getShiftDayPrototypes()) {
                List<ServiceCategoryDTO> compareList = new ArrayList<>();
                serviceCategories.forEach(serviceCategory -> compareList.add(serviceCategory.clone()));

                for (AccumulatedObHoursDTO accumulatedObHoursDTO : shiftDayPrototype.getAccumulatedObHours()) {
                    ServiceCategoryDTO foundCategory = null;
                    for (ServiceCategoryDTO serviceCategory : compareList)
                        if (accumulatedObHoursDTO.getServiceCategoryId() == serviceCategory.getId())
                            foundCategory = serviceCategory;

                    if (foundCategory != null)
                        compareList.remove(foundCategory);
                }

                // If compare list is not empty for each day prototypes, it means that accumulated ob hours are not set for some service categories
                if (!compareList.isEmpty()) {
                    StringBuilder sb = new StringBuilder();
                    compareList.forEach(serviceCategory -> sb.append(serviceCategory.getName()).append(" "));
                    throw new ApiException(ErrorMessageDTO.ErrorCode.INVALID_SHIFT_CONFIGURATION, "Kan inte skapa skift, " +
                            "accumulerade OB timmar är inte angivna för följande arbetskategorier "
                            + sb.toString().trim() + ", på dags prototyp " + shiftDayPrototype.getDayType());
                }
            }
        };
    }
}
