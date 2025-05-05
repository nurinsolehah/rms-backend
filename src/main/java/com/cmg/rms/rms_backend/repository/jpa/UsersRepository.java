package com.cmg.rms.rms_backend.repository.jpa;

import com.cmg.rms.rms_backend.model.Users;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {

  Optional<Users> findByUsername(String username);
}
