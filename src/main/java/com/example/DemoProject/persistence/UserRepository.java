package com.example.DemoProject.persistence;

import com.example.DemoProject.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    UserEntity findByUsername(String username);
    Boolean existsByUsername(String username);
    UserEntity findByUsernameAndPassword(String username, String password);
}
