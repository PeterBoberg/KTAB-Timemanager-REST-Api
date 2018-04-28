package se.karingotrafiken.timemanager.rest.service.unoincontracts;

import org.springframework.stereotype.Service;
import se.karingotrafiken.timemanager.rest.dto.stored.UnionContractDTO;
import se.karingotrafiken.timemanager.rest.entitys.UnionContract;
import se.karingotrafiken.timemanager.rest.service.StrongEntityCrudService;

@Service
public interface UnionContractService extends StrongEntityCrudService<UnionContractDTO, UnionContract> {
}
