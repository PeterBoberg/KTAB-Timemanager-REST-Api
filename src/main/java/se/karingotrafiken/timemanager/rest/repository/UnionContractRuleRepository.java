package se.karingotrafiken.timemanager.rest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import se.karingotrafiken.timemanager.rest.entitys.UnionContractRule;

import java.util.List;

@Repository
@Transactional
public interface UnionContractRuleRepository extends CrudRepository<UnionContractRule, Long> {

    List<UnionContractRule> findByUnionContractId(long id);
}
