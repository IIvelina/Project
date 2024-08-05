package com.project.bank.service.impl;


import com.project.bank.model.entity.Role;
import com.project.bank.model.entity.User;
import com.project.bank.model.enums.UserGenderEnum;
import com.project.bank.model.enums.UserRoleEnum;
import com.project.bank.model.serviceModel.UserServiceModel;
import com.project.bank.repository.UserRepository;
import com.project.bank.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleService roleService;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser() {
        UserServiceModel userServiceModel = new UserServiceModel();
        userServiceModel.setUsername("testuser");
        userServiceModel.setPassword("password");
        userServiceModel.setGender(UserGenderEnum.MALE);

        User user = new User();
        user.setUsername("testuser");
        user.setPassword("encodedPassword");
        user.setGender(UserGenderEnum.MALE);

        Role clientRole = new Role();
        clientRole.setRole(UserRoleEnum.CLIENT);

        when(modelMapper.map(userServiceModel, User.class)).thenReturn(user);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(roleService.findRoleByRoleName(UserRoleEnum.CLIENT)).thenReturn(clientRole);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(modelMapper.map(user, UserServiceModel.class)).thenReturn(userServiceModel);

        UserServiceModel registeredUser = userServiceImpl.registerUser(userServiceModel);

        assertEquals(userServiceModel, registeredUser);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testGenerateUniqueClientNumber() throws Exception {
        Set<String> existingClientNumbers = new HashSet<>();
        when(userRepository.existsByClientNumber(anyString())).thenAnswer(invocation -> {
            String clientNumber = invocation.getArgument(0);
            return existingClientNumbers.contains(clientNumber);
        });

        // Използваме reflection за достъп до private метода
        Method generateUniqueClientNumberMethod = UserServiceImpl.class.getDeclaredMethod("generateUniqueClientNumber");
        generateUniqueClientNumberMethod.setAccessible(true);

        String clientNumber1 = (String) generateUniqueClientNumberMethod.invoke(userServiceImpl);
        existingClientNumbers.add(clientNumber1);
        assertNotNull(clientNumber1);
        assertTrue(clientNumber1.startsWith("CN"));
        assertEquals(11, clientNumber1.length());

        String clientNumber2 = (String) generateUniqueClientNumberMethod.invoke(userServiceImpl);
        assertNotNull(clientNumber2);
        assertTrue(clientNumber2.startsWith("CN"));
        assertEquals(11, clientNumber2.length());
        assertNotEquals(clientNumber1, clientNumber2);
    }

    @Test
    void testFindByUsernameAndPassword() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("encodedPassword");

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(true);
        when(modelMapper.map(user, UserServiceModel.class)).thenReturn(new UserServiceModel());

        UserServiceModel userServiceModel = userServiceImpl.findByUsernameAndPassword("testuser", "password");

        assertNotNull(userServiceModel);
    }

    @Test
    void testFindById() {
        User user = new User();
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User foundUser = userServiceImpl.findById(1L);

        assertEquals(user, foundUser);
    }

    @Test
    void testExistsBySSN() {
        when(userRepository.existsBySsn("1234567890")).thenReturn(true);

        assertTrue(userServiceImpl.existsBySSN("1234567890"));
    }

    @Test
    void testExistsByIdCardNumber() {
        when(userRepository.existsByIdCardNumber("ID123456")).thenReturn(true);

        assertTrue(userServiceImpl.existsByIdCardNumber("ID123456"));
    }

    @Test
    void testExistsByEmail() {
        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);

        assertTrue(userServiceImpl.existsByEmail("test@example.com"));
    }

    @Test
    void testExistsByUsername() {
        when(userRepository.existsByUsername("testuser")).thenReturn(true);

        assertTrue(userServiceImpl.existsByUsername("testuser"));
    }

    @Test
    void testExistsByPhoneNumber() {
        when(userRepository.existsByPhoneNumber("1234567890")).thenReturn(true);

        assertTrue(userServiceImpl.existsByPhoneNumber("1234567890"));
    }

    @Test
    void testFindByUsername() {
        User user = new User();
        user.setUsername("testuser");

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        User foundUser = userServiceImpl.findByUsername("testuser");

        assertEquals(user, foundUser);
    }

    @Test
    void testSave() {
        User user = new User();
        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userServiceImpl.save(user);

        assertEquals(user, savedUser);
    }

    @Test
    void testGetUserByPhoneNumber() {
        User user = new User();
        user.setPhoneNumber("1234567890");

        when(userRepository.findByPhoneNumber("1234567890")).thenReturn(Optional.of(user));

        User foundUser = userServiceImpl.getUserByPhoneNumber("1234567890");

        assertEquals(user, foundUser);
    }

    @Test
    void testGetClients() {
        Role clientRole = new Role();
        clientRole.setRole(UserRoleEnum.CLIENT);
        User user = new User();
        user.setRoles(new HashSet<>(Collections.singletonList(clientRole)));

        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));

        assertEquals(Collections.singletonList(user), userServiceImpl.getClients());
    }


    @Test
    void testAddRoleToUser() {
        User user = new User();
        user.setRoles(new HashSet<>()); // Добавяме празен HashSet за ролите на потребителя

        Role adminRole = new Role();
        adminRole.setRole(UserRoleEnum.ADMIN); // Задаваме ролята чрез setter метод

        userServiceImpl.addRoleToUser(user, adminRole);

        assertTrue(user.getRoles().contains(adminRole));
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testDeleteUser() {
        User user = new User();

        userServiceImpl.delete(user);

        verify(userRepository, times(1)).delete(user);
    }
}