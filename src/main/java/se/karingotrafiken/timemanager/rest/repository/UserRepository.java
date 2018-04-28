package se.karingotrafiken.timemanager.rest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.karingotrafiken.timemanager.rest.entitys.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);

    boolean existsByUsername(String username);
}
