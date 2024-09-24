package com.kirana.register.kirana_store_register.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.kirana.register.kirana_store_register.model.User;
import java.util.Optional;

/**
 * Repository interface for managing User entities.
 */
public interface UserRepository extends MongoRepository<User, String> {

  /**
   * Finds a User by their username.
   *
   * @param username The username to search for.
   * @return An Optional containing the User if found, otherwise empty.
   */
  Optional<User> findByUsername(String username);
}
