package se.karingotrafiken.timemanager.rest.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import se.karingotrafiken.timemanager.rest.entitys.ServiceCategory;

@Repository
@Transactional
public interface ServiceCategoryRepository extends CrudRepository<ServiceCategory, Long> {

}
