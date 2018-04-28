package se.karingotrafiken.timemanager.rest.service.enums;

import org.springframework.stereotype.Service;
import se.karingotrafiken.timemanager.rest.dto.stored.ServiceCategoryDTO;
import se.karingotrafiken.timemanager.rest.entitys.ServiceCategory;
import se.karingotrafiken.timemanager.rest.service.StrongEntityCrudService;

@Service
public interface ServiceCategoryService extends StrongEntityCrudService<ServiceCategoryDTO, ServiceCategory> {

    long countServiceCategories();
}
