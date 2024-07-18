package com.project.bank.service;

import com.project.bank.model.entity.Role;
import com.project.bank.model.enums.UserRoleEnum;

public interface RoleService {
    void initRoles();

    Role findRoleByRoleName(UserRoleEnum roleName);

}
