package com.institute.instituteserver.repository;

import com.institute.instituteserver.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(@Email @NotBlank String email);

    Boolean existsByEmail(@Email @NotBlank String email);
}
