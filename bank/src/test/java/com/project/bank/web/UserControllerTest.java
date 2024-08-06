package com.project.bank.web;

import com.project.bank.model.dto.UserLoginDTO;
import com.project.bank.model.dto.UserRegisterDTO;
import com.project.bank.model.entity.User;
import com.project.bank.model.serviceModel.UserServiceModel;
import com.project.bank.service.EmployeeService;
import com.project.bank.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserService userService;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private HttpSession session;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private RedirectAttributes redirectAttributes;

    @Mock
    private Model model;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterConfirm_ValidRegistration() {
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setUsername("testUser");
        userRegisterDTO.setPassword("password");
        userRegisterDTO.setConfirmPassword("password");
        userRegisterDTO.setEmail("test@example.com");
        userRegisterDTO.setSSN("123-45-6789");
        userRegisterDTO.setCardIdNumber("ID123456");
        userRegisterDTO.setPhoneNumber("1234567890");

        UserServiceModel userServiceModel = new UserServiceModel();

        when(bindingResult.hasErrors()).thenReturn(false);
        when(modelMapper.map(userRegisterDTO, UserServiceModel.class)).thenReturn(userServiceModel);
        when(userService.existsBySSN(anyString())).thenReturn(false);
        when(userService.existsByIdCardNumber(anyString())).thenReturn(false);
        when(userService.existsByEmail(anyString())).thenReturn(false);
        when(userService.existsByUsername(anyString())).thenReturn(false);
        when(userService.existsByPhoneNumber(anyString())).thenReturn(false);

        String viewName = userController.registerConfirm(userRegisterDTO, bindingResult, redirectAttributes);
        assertEquals("redirect:login", viewName);
        verify(userService, times(1)).registerUser(userServiceModel);
    }

    @Test
    void testRegisterConfirm_InvalidRegistration() {
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setPassword("password");
        userRegisterDTO.setConfirmPassword("password");

        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = userController.registerConfirm(userRegisterDTO, bindingResult, redirectAttributes);
        assertEquals("redirect:register", viewName);
        verify(userService, never()).registerUser(any(UserServiceModel.class));
    }

    @Test
    void testLoginConfirm_ValidLogin() {
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setUsername("testUser");
        userLoginDTO.setPassword("password");
        UserServiceModel userServiceModel = new UserServiceModel();
        userServiceModel.setId(1L);
        userServiceModel.setUsername("testUser");

        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");

        when(bindingResult.hasErrors()).thenReturn(false);
        when(userService.findByUsernameAndPassword("testUser", "password")).thenReturn(userServiceModel);
        when(userService.findById(1L)).thenReturn(user);
        when(userService.getUserByUsername("testUser")).thenReturn(user);
        when(employeeService.findByBusinessEmail("testUser_wave@financial.com")).thenReturn(Optional.empty());

        String viewName = userController.loginConfirm(userLoginDTO, bindingResult, redirectAttributes, session);
        assertEquals("redirect:/", viewName);
        verify(session, times(1)).setAttribute("currentUser", user);
        verify(session, times(1)).setAttribute("isAdmin", false);
    }

    @Test
    void testLoginConfirm_InvalidLogin() {
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setUsername("testUser");
        userLoginDTO.setPassword("wrongPassword");

        when(bindingResult.hasErrors()).thenReturn(false);
        when(userService.findByUsernameAndPassword("testUser", "wrongPassword")).thenReturn(null);

        String viewName = userController.loginConfirm(userLoginDTO, bindingResult, redirectAttributes, session);
        assertEquals("redirect:/users/login?error=true", viewName);
        verify(session, never()).setAttribute(anyString(), any());
    }

    @Test
    void testProfile() {
        User user = new User();
        user.setUsername("testUser");

        org.springframework.security.core.userdetails.User principal = new org.springframework.security.core.userdetails.User(
                "testUser", "password", new ArrayList<>()
        );

        when(userService.findByUsername("testUser")).thenReturn(user);

        String viewName = userController.profile(model, principal);
        assertEquals("profileEN", viewName);
        verify(model, times(1)).addAttribute("currentUser", user);
    }

    @Test
    void testLogout() {
        String viewName = userController.logout();
        assertEquals("redirect:/", viewName);
    }

    @Test
    void testLoginWithErrors() {
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setUsername("testUser");

        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = userController.loginConfirm(userLoginDTO, bindingResult, redirectAttributes, session);
        assertEquals("redirect:/users/login?error=true", viewName);
    }

    @Test
    void testLoginError() {
        String viewName = userController.loginError(model);
        assertEquals("loginEN", viewName);
        verify(model, times(1)).addAttribute("isFound", false);
    }

    @Test
    void testRegisterWithExistingSSN() {
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setUsername("testUser");
        userRegisterDTO.setPassword("password");
        userRegisterDTO.setConfirmPassword("password");
        userRegisterDTO.setEmail("test@example.com");
        userRegisterDTO.setSSN("123-45-6789");
        userRegisterDTO.setCardIdNumber("ID123456");
        userRegisterDTO.setPhoneNumber("1234567890");

        when(bindingResult.hasErrors()).thenReturn(false);
        when(userService.existsBySSN("123-45-6789")).thenReturn(true);

        String viewName = userController.registerConfirm(userRegisterDTO, bindingResult, redirectAttributes);
        assertEquals("redirect:register", viewName);
        verify(userService, never()).registerUser(any(UserServiceModel.class));
    }

    @Test
    void testRegisterWithExistingEmail() {
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setUsername("testUser");
        userRegisterDTO.setPassword("password");
        userRegisterDTO.setConfirmPassword("password");
        userRegisterDTO.setEmail("test@example.com");
        userRegisterDTO.setSSN("123-45-6789");
        userRegisterDTO.setCardIdNumber("ID123456");
        userRegisterDTO.setPhoneNumber("1234567890");

        when(bindingResult.hasErrors()).thenReturn(false);
        when(userService.existsByEmail("test@example.com")).thenReturn(true);

        String viewName = userController.registerConfirm(userRegisterDTO, bindingResult, redirectAttributes);
        assertEquals("redirect:register", viewName);
        verify(userService, never()).registerUser(any(UserServiceModel.class));
    }

    @Test
    void testRegisterWithExistingUsername() {
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setUsername("testUser");
        userRegisterDTO.setPassword("password");
        userRegisterDTO.setConfirmPassword("password");
        userRegisterDTO.setEmail("test@example.com");
        userRegisterDTO.setSSN("123-45-6789");
        userRegisterDTO.setCardIdNumber("ID123456");
        userRegisterDTO.setPhoneNumber("1234567890");

        when(bindingResult.hasErrors()).thenReturn(false);
        when(userService.existsByUsername("testUser")).thenReturn(true);

        String viewName = userController.registerConfirm(userRegisterDTO, bindingResult, redirectAttributes);
        assertEquals("redirect:register", viewName);
        verify(userService, never()).registerUser(any(UserServiceModel.class));
    }

    @Test
    void testRegisterWithExistingPhoneNumber() {
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setUsername("testUser");
        userRegisterDTO.setPassword("password");
        userRegisterDTO.setConfirmPassword("password");
        userRegisterDTO.setEmail("test@example.com");
        userRegisterDTO.setSSN("123-45-6789");
        userRegisterDTO.setCardIdNumber("ID123456");
        userRegisterDTO.setPhoneNumber("1234567890");

        when(bindingResult.hasErrors()).thenReturn(false);
        when(userService.existsByPhoneNumber("1234567890")).thenReturn(true);

        String viewName = userController.registerConfirm(userRegisterDTO, bindingResult, redirectAttributes);
        assertEquals("redirect:register", viewName);
        verify(userService, never()).registerUser(any(UserServiceModel.class));
    }
}