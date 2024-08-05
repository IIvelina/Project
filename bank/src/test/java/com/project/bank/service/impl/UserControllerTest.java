package com.project.bank.service.impl;

import com.project.bank.model.dto.UserLoginDTO;
import com.project.bank.model.dto.UserRegisterDTO;
import com.project.bank.model.entity.Employee;
import com.project.bank.model.entity.User;
import com.project.bank.model.serviceModel.UserServiceModel;
import com.project.bank.service.EmployeeService;
import com.project.bank.service.UserService;
import com.project.bank.web.UserController;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserService userService;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private RedirectAttributes redirectAttributes;

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister() {
        String viewName = userController.register();
        assertEquals("registerEN", viewName);
    }

    @Test
    void testRegisterConfirm_WithErrors() {
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setPassword("password");
        userRegisterDTO.setConfirmPassword("differentPassword");

        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = userController.registerConfirm(userRegisterDTO, bindingResult, redirectAttributes);

        assertEquals("redirect:register", viewName);
        verify(redirectAttributes).addFlashAttribute("userRegisterDTO", userRegisterDTO);
        verify(redirectAttributes).addFlashAttribute("org.springframework.validation.BindingResult.userRegisterDTO", bindingResult);
    }

    @Test
    void testRegisterConfirm_WithoutErrors() {
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setPassword("password");
        userRegisterDTO.setConfirmPassword("password");

        when(bindingResult.hasErrors()).thenReturn(false);
        when(userService.existsBySSN(userRegisterDTO.getSSN())).thenReturn(false);
        when(userService.existsByIdCardNumber(userRegisterDTO.getCardIdNumber())).thenReturn(false);
        when(userService.existsByEmail(userRegisterDTO.getEmail())).thenReturn(false);
        when(userService.existsByUsername(userRegisterDTO.getUsername())).thenReturn(false);
        when(userService.existsByPhoneNumber(userRegisterDTO.getPhoneNumber())).thenReturn(false);

        UserServiceModel userServiceModel = new UserServiceModel();
        when(modelMapper.map(userRegisterDTO, UserServiceModel.class)).thenReturn(userServiceModel);

        String viewName = userController.registerConfirm(userRegisterDTO, bindingResult, redirectAttributes);

        assertEquals("redirect:login", viewName);
        verify(userService).registerUser(userServiceModel);
    }



    @Test
    void testLogin_WithError() {
        String viewName = userController.login("error", model, new UserLoginDTO());

        assertEquals("loginEN", viewName);
        verify(model).addAttribute("loginError", true);
    }

    @Test
    void testLoginConfirm_WithErrors() {
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = userController.loginConfirm(userLoginDTO, bindingResult, redirectAttributes, session);

        assertEquals("redirect:/users/login?error=true", viewName);
        verify(redirectAttributes).addFlashAttribute("userLoginDTO", userLoginDTO);
        verify(redirectAttributes).addFlashAttribute("org.springframework.validation.BindingResult.userLoginDTO", bindingResult);
    }

    @Test
    void testLoginConfirm_ValidUser() {
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setUsername("username");
        userLoginDTO.setPassword("password");

        UserServiceModel userServiceModel = new UserServiceModel();
        userServiceModel.setId(1L);
        userServiceModel.setUsername("username");

        when(bindingResult.hasErrors()).thenReturn(false);
        when(userService.findByUsernameAndPassword(userLoginDTO.getUsername(), userLoginDTO.getPassword())).thenReturn(userServiceModel);

        User currentUser = new User();
        currentUser.setId(1L);
        currentUser.setUsername("username");

        when(userService.findById(userServiceModel.getId())).thenReturn(currentUser);
        when(userService.getUserByUsername(userServiceModel.getUsername())).thenReturn(currentUser);

        Employee employee = new Employee();
        String businessEmail = "username_wave@financial.com";
        when(employeeService.findByBusinessEmail(businessEmail)).thenReturn(Optional.of(employee));

        String viewName = userController.loginConfirm(userLoginDTO, bindingResult, redirectAttributes, session);

        assertEquals("redirect:/", viewName);
        verify(session).setAttribute("currentUser", currentUser);
        verify(session).setAttribute("isAdmin", true);
    }

    @Test
    void testLoginConfirm_InvalidUser() {
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setUsername("username");
        userLoginDTO.setPassword("password");

        when(bindingResult.hasErrors()).thenReturn(false);
        when(userService.findByUsernameAndPassword(userLoginDTO.getUsername(), userLoginDTO.getPassword())).thenReturn(null);

        String viewName = userController.loginConfirm(userLoginDTO, bindingResult, redirectAttributes, session);

        assertEquals("redirect:/users/login?error=true", viewName);
        verify(redirectAttributes).addFlashAttribute("userLoginDTO", userLoginDTO);
    }

    @Test
    void testLoginError() {
        String viewName = userController.loginError(model);

        assertEquals("loginEN", viewName);
        verify(model).addAttribute("isFound", false);
    }

    @Test
    void testLogout() {
        String viewName = userController.logout();

        assertEquals("redirect:/", viewName);
    }


}