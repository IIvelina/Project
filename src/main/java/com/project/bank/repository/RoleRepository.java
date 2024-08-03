package com.project.bank.repository;

import com.project.bank.model.entity.Role;
import com.project.bank.model.enums.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRole(UserRoleEnum roleName);


}
