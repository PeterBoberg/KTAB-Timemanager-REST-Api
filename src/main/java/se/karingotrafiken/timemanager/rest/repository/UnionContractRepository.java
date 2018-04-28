package se.karingotrafiken.timemanager.rest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import se.karingotrafiken.timemanager.rest.entitys.UnionContract;

import java.util.List;

@Repository
@Transactional
public interface UnionContractRepository extends CrudRepository<UnionContract, Long> {

    List<UnionContract> findByServiceCategoryIdOrderByStartDate(long id);
}
