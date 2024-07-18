package com.project.bank.service.impl;

import com.project.bank.model.entity.Role;
import com.project.bank.model.enums.UserRoleEnum;
import com.project.bank.repository.RoleRepository;
import com.project.bank.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void initRoles() {
        if (roleRepository.count() != 0) {
            return;
        }

        Arrays.stream(UserRoleEnum.values())
                .forEach(userRoleEnum -> {
                    Role role = new Role();
                    role.setRole(userRoleEnum);
                    roleRepository.save(role);
                });
    }

    @Override
    public Role findRoleByRoleName(UserRoleEnum roleName) {
        return roleRepository.findByRole(roleName);
    }


}
