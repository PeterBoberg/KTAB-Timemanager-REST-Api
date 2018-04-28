package se.karingotrafiken.timemanager.rest.service.enums;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import se.karingotrafiken.timemanager.rest.dto.stored.ServiceCategoryDTO;
import se.karingotrafiken.timemanager.rest.entitys.ServiceCategory;
import se.karingotrafiken.timemanager.rest.repository.ServiceCategoryRepository;
import se.karingotrafiken.timemanager.rest.service.AbstractStrongEntityCrudService;
import se.karingotrafiken.timemanager.rest.service.Translator;

@Component
public class ServiceCategoryServiceImpl extends AbstractStrongEntityCrudService<ServiceCategoryDTO, ServiceCategory> implements ServiceCategoryService {

    @Autowired
    private ServiceCategoryRepository serviceCategoryRepository;

    @Override
    public CrudRepository<ServiceCategory, Long> getRepository() {
        return serviceCategoryRepository;
    }

    @Override
    public long countServiceCategories() {
        return serviceCategoryRepository.count();
    }

    @Override
    public Translator<ServiceCategoryDTO, ServiceCategory> getTranslator() {
        return new Translator<ServiceCategoryDTO, ServiceCategory>() {
            @Override
            public ServiceCategoryDTO translateFromEntity(ServiceCategory entity) {
                ServiceCategoryDTO serviceCategoryDTO = new ServiceCategoryDTO();
                serviceCategoryDTO.setId(entity.getId());
                serviceCategoryDTO.setName(entity.getName());
                return serviceCategoryDTO;
            }

            @Override
            public ServiceCategory translateFromDTO(ServiceCategoryDTO serviceCategoryDTO) {
                ServiceCategory serviceCategory = new ServiceCategory();
                serviceCategory.setId(serviceCategoryDTO.getId());
                serviceCategory.setName(serviceCategoryDTO.getName());
                return serviceCategory;
            }
        };
    }
}
