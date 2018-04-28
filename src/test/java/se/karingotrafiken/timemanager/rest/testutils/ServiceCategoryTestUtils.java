package se.karingotrafiken.timemanager.rest.testutils;

import se.karingotrafiken.timemanager.rest.dto.stored.ServiceCategoryDTO;
import se.karingotrafiken.timemanager.rest.entitys.ServiceCategory;

public class ServiceCategoryTestUtils {

    public static ServiceCategory commanderCategory() {
        ServiceCategory serviceCategory = new ServiceCategory();
        serviceCategory.setId(1L);
        serviceCategory.setName("Commander");
        return serviceCategory;
    }

    public static ServiceCategoryDTO commanderCategoryDto() {
        ServiceCategoryDTO serviceCategoryDTO = new ServiceCategoryDTO();
        serviceCategoryDTO.setId(commanderCategory().getId());
        return serviceCategoryDTO;
    }
}
