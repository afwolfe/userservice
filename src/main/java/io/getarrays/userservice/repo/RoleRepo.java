package io.getarrays.userservice.repo;

import io.getarrays.userservice.model.Role;
import io.getarrays.userservice.model.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {

  Role findByRoleEnum(RoleEnum roleEnum);

}
