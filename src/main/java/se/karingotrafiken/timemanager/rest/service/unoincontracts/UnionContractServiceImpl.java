package se.karingotrafiken.timemanager.rest.service.unoincontracts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import se.karingotrafiken.timemanager.rest.dto.stored.ErrorMessageDTO;
import se.karingotrafiken.timemanager.rest.dto.stored.UnionContractDTO;
import se.karingotrafiken.timemanager.rest.entitys.ServiceCategory;
import se.karingotrafiken.timemanager.rest.entitys.UnionContract;
import se.karingotrafiken.timemanager.rest.exceptions.ApiException;
import se.karingotrafiken.timemanager.rest.repository.UnionContractRepository;
import se.karingotrafiken.timemanager.rest.service.AbstractStrongEntityCrudService;
import se.karingotrafiken.timemanager.rest.service.LogicalValidator;
import se.karingotrafiken.timemanager.rest.service.Translator;
import se.karingotrafiken.timemanager.rest.utils.DateTimeUtils;

import java.util.Date;
import java.util.List;

@Component
public class UnionContractServiceImpl extends AbstractStrongEntityCrudService<UnionContractDTO, UnionContract> implements UnionContractService {

    @Autowired
    private UnionContractRepository unionContractRepository;

    @Override
    public CrudRepository<UnionContract, Long> getRepository() {
        return unionContractRepository;
    }

    @Override
    public Translator<UnionContractDTO, UnionContract> getTranslator() {
        return new Translator<UnionContractDTO, UnionContract>() {
            @Override
            public UnionContractDTO translateFromEntity(UnionContract contract) {
                UnionContractDTO dto = new UnionContractDTO();
                dto.setId(contract.getId());
                dto.setName(contract.getName());
                dto.setServiceCategoryId(contract.getServiceCategory().getId());
                dto.setStartDate(DateTimeUtils.toDateString(contract.getStartDate()));
                dto.setEndDate(DateTimeUtils.toDateString(contract.getEndDate()));
                dto.setMonthlyWorkHours(contract.getMonthlyWorkHours());
                dto.setObRegularCoeff(contract.getObRegularCoeff());
                dto.setObHolidayCoeff(contract.getObHolidayCoeff());
                dto.setOvertimeRegularCoeff(contract.getOvertimeRegulaCoeff());
                dto.setOvertimeHolidayCoeff(contract.getOvertimeHolidayCoeff());
                return dto;
            }

            @Override
            public UnionContract translateFromDTO(UnionContractDTO dto) {
                ServiceCategory category = new ServiceCategory();
                category.setId(dto.getServiceCategoryId());

                UnionContract contract = new UnionContract();
                contract.setId(dto.getId());
                contract.setName(dto.getName());
                contract.setServiceCategory(category);
                contract.setStartDate(DateTimeUtils.dateFromDateString(dto.getStartDate()));
                contract.setEndDate(DateTimeUtils.dateFromDateString(dto.getEndDate()));
                contract.setMonthlyWorkHours(dto.getMonthlyWorkHours());
                contract.setObRegularCoeff(dto.getObRegularCoeff());
                contract.setObHolidayCoeff(dto.getObHolidayCoeff());
                contract.setOvertimeRegulaCoeff(dto.getOvertimeRegularCoeff());
                contract.setOvertimeHolidayCoeff(dto.getOvertimeHolidayCoeff());

                return contract;
            }
        };
    }


    @Override
    public LogicalValidator<UnionContractDTO> getLogicalValidator() {
        return dto -> {

            List<UnionContract> unionContracts = unionContractRepository.findByServiceCategoryIdOrderByStartDate(dto.getServiceCategoryId());
            unionContracts.forEach(contract -> {

                Date dtoStartDate = DateTimeUtils.dateFromDateString(dto.getStartDate());
                Date dtoEndDate = DateTimeUtils.dateFromDateString(dto.getEndDate());

                if (
                        (contract.getStartDate().before(dtoStartDate) && contract.getEndDate().after(dtoStartDate)) ||
                                (contract.getStartDate().before(dtoEndDate) && contract.getEndDate().after(dtoEndDate)) ||
                                (contract.getStartDate().after(dtoStartDate) && contract.getEndDate().before(dtoEndDate)) ||
                                (contract.getStartDate().before(dtoStartDate) && contract.getEndDate().after(dtoStartDate))) {
                    throw new ApiException(ErrorMessageDTO.ErrorCode.OVERLAPING_UNION_CONTRACTS, "Kan inte skapa kollektivavtal:"
                            + dto.getName() + " efterson det överlappar med " + contract.getName());
                }
            });

        };
    }
}
