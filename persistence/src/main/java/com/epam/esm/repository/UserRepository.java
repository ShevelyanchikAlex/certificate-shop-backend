package com.epam.esm.repository;

import com.epam.esm.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Finds User by email
     *
     * @param email Email
     * @return User
     */
    Optional<User> findByEmail(String email);

    /**
     * Exists User by Email
     *
     * @param email Email
     * @return true if exists, otherwise false
     */
    boolean existsUserByEmail(String email);
}
