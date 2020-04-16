package com.apap.tu08.repository;

import com.apap.tu08.model.UserRoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleDB extends JpaRepository<UserRoleModel, Long> {
    UserRoleModel findByUsername(String username);
}
