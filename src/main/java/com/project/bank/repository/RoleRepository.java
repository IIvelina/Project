package com.project.bank.repository;

import com.project.bank.model.entity.Role;
import com.project.bank.model.enums.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRole(UserRoleEnum roleName);


}
