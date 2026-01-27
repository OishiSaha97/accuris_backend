// UserRepository.java (Repository Interface)
package com.datasoft.bkash.ea.repository;

import com.datasoft.bkash.ea.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Find user by normalized email
    Optional<User> findByEmailNormalized(String emailNormalized);

    // Check if email exists
    boolean existsByEmailNormalized(String emailNormalized);

    // Check if phone exists
    boolean existsByPhone(String phone);

    // Find user by phone
    Optional<User> findByPhone(String phone);

    // Find user by email (non-normalized, if needed)
    Optional<User> findByEmail(String email);
}