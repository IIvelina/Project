package com.project.bank.service.impl;
import com.project.bank.model.entity.Role;
import com.project.bank.model.enums.UserRoleEnum;
import com.project.bank.repository.RoleRepository;
import com.project.bank.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testInitRoles() {
        when(roleRepository.count()).thenReturn(0L);

        roleService.initRoles();

        verify(roleRepository, times(UserRoleEnum.values().length)).save(any(Role.class));
    }

    @Test
    void testInitRoles_RolesAlreadyInitialized() {
        when(roleRepository.count()).thenReturn((long) UserRoleEnum.values().length);

        roleService.initRoles();

        verify(roleRepository, never()).save(any(Role.class));
    }

    @Test
    void testFindRoleByRoleName() {
        UserRoleEnum roleName = UserRoleEnum.CLIENT;
        Role role = new Role();
        role.setRole(roleName);

        when(roleRepository.findByRole(roleName)).thenReturn(role);

        Role foundRole = roleService.findRoleByRoleName(roleName);

        assertEquals(role, foundRole);
        verify(roleRepository, times(1)).findByRole(roleName);
    }

    @Test
    void testFindRoleByName_RoleFound() {
        UserRoleEnum roleName = UserRoleEnum.CLIENT;
        Role role = new Role();
        role.setRole(roleName);

        when(roleRepository.findByRole(roleName)).thenReturn(role);

        Role foundRole = roleService.findRoleByName(roleName);

        assertEquals(role, foundRole);
        verify(roleRepository, times(1)).findByRole(roleName);
    }

    @Test
    void testFindRoleByName_RoleNotFound() {
        UserRoleEnum roleName = UserRoleEnum.CLIENT;

        when(roleRepository.findByRole(roleName)).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            roleService.findRoleByName(roleName);
        });

        assertEquals("Role not found: " + roleName, exception.getMessage());
        verify(roleRepository, times(1)).findByRole(roleName);
    }
}