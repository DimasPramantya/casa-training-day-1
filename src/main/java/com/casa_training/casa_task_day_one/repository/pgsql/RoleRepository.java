package com.casa_training.casa_task_day_one.repository.pgsql;

import com.casa_training.casa_task_day_one.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByName(String roleName);
}
