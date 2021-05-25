package com.shreyas.messaging.repository;

import com.shreyas.messaging.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    String FIND_BY_NAME = "SELECT u FROM users u WHERE u.name = :name";

    @Query(value = FIND_BY_NAME, nativeQuery = true)
    Optional<User> findByName(@Param("name") String name);
}
