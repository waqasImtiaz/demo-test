package com.airfranceklm.test.repository;

import com.airfranceklm.test.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for
 * crud operations
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

  User findByEmail(String email);
}
