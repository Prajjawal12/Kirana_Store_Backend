package com.kirana.register.kirana_store_register.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.kirana.register.kirana_store_register.model.User;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
  Optional<User> findByUsername(String username);
}
