package se.karingotrafiken.timemanager.rest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.karingotrafiken.timemanager.rest.appmodel.common.BreakDateType;
import se.karingotrafiken.timemanager.rest.entitys.BreakDate;

@Repository
public interface BreakDateRepository extends CrudRepository<BreakDate, Long> {

    BreakDate findFirstByBreakDateType(BreakDateType breakDateType);
}
