package com.project.bank.config;

import com.project.bank.repository.UserRepository;
import com.project.bank.service.impl.BankUserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/img/**").permitAll()
                        .requestMatchers("/", "/about", "/terms", "/user/forgot-password", "/security-tips",
                                "/productAndService", "/applicationProcess", "/benefits",
                                "/openPositions", "/contact", "/loans", "/users/login",
                                "/users/register", "/savings", "/cards").permitAll()
                        .requestMatchers("/profile", "/user/withdraw", "/user/transfer",
                                "/account/saving", "/account/checking", "/user/request-new-product",
                                "/user/accounts", "/user/transactions", "/user/add-money",
                                "/user/add-money", "/logout", "/job/apply").hasRole("CLIENT")
                        .requestMatchers("/director/dashboard").hasRole("DIRECTOR")
                        .requestMatchers("/user/admin/login", "/user/admin/dashboard").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/users/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/", true)
                        .failureForwardUrl("/users/login-error")
                )
                .rememberMe(rememberMe -> rememberMe
                        .rememberMeParameter("remember-me")
                        .key("uniqueAndSecret")
                        .tokenValiditySeconds(86400) // 24 часа
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                )
                .sessionManagement(sessionManagement -> sessionManagement
                        .invalidSessionUrl("/users/login")
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(false)
                        .expiredUrl("/users/login")
                )
                .build();
    }

    @Bean
    public BankUserDetailsService bankUserDetailsService(UserRepository userRepository) {
        return new BankUserDetailsService(userRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
}




//package com.project.bank.config;
//
//import com.project.bank.repository.UserRepository;
//import com.project.bank.service.impl.BankUserDetailsService;
//import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class SecurityConfiguration {
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                .authorizeHttpRequests(
//                        // Setup which URLs are available to whom
//                        authorizeRequests ->
//                                authorizeRequests
//                                        // All static resources to common locations (css, js) are available to anyone
//                                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
//                                        // Specific folder for img
//                                        .requestMatchers("/img/**").permitAll()
//                                        // Some more resources for ALL users
//                                        .requestMatchers("/", "/users/login", "/users/register").permitAll()
//                                        // All other URLs should be authenticated
//                                        .anyRequest().authenticated()
//                )
//                .formLogin(formLogin ->
//                        formLogin
//                                // Where is our login form
//                                .loginPage("/users/login")
//                                // What is the name of the username parameter on the login POST request
//                                .usernameParameter("username")
//                                // What is the name of the password parameter on the login POST request
//                                .passwordParameter("password")
//                                // What will happen if the login is successful
//                                .defaultSuccessUrl("/", true)
//                                // What will happen if the login fails
//                                .failureForwardUrl("/users/login-error")
//                )
//                .logout(logout ->
//                        logout
//                                // What is the logout URL
//                                .logoutUrl("/users/logout")
//                                // Where to go after logout
//                                .logoutSuccessUrl("/")
//                                // Invalidate session after logout
//                                .invalidateHttpSession(true)
//                )
//                .build();
//    }
//
//    @Bean
//    public BankUserDetailsService bankUserDetailsService(UserRepository userRepository){
//        return new BankUserDetailsService(userRepository);
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return Pbkdf2PasswordEncoder
//                .defaultsForSpringSecurity_v5_8(); // използване на готов метод за настройка
//        // return new Pbkdf2PasswordEncoder();
//    }
//}
//
//
//
////package com.project.bank.config;
////
////import com.project.bank.repository.UserRepository;
////import com.project.bank.service.impl.BankUserDetailsService;
////import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
////import org.springframework.context.annotation.Bean;
////import org.springframework.context.annotation.Configuration;
////import org.springframework.security.config.annotation.web.builders.HttpSecurity;
////import org.springframework.security.crypto.password.PasswordEncoder;
////import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
////import org.springframework.security.web.SecurityFilterChain;
////
////@Configuration
////public class SecurityConfiguration {
////
////    @Bean
////    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
////        return httpSecurity
////                .authorizeHttpRequests(
////                        //Setup which URL-s are available to who
////                        authorizeRequests ->
////                                authorizeRequests
////                                        //all static resources to common locations (css, img, js) are available to anyone
////                                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
////                                        //some more resources for ALL users
////                                        .requestMatchers("/", "/users/login", "/users/register").permitAll()
////                                        //all other URL-s should be authenticated
////                                        .anyRequest().authenticated()
////                )
////                .formLogin(formLogin ->
////                        formLogin
////                                //where is our login form
////                                .loginPage("/users/login")
////                                //what is the name of the username parameter on the login POST request
////                                .usernameParameter("username")
////                                //what is the name of the password parameter on the login POST request
////                                .passwordParameter("password")
////                                //what will happen if the login is successful
////                                .defaultSuccessUrl("/")
////                                //what will happen if the login fails
////                                .failureForwardUrl("/users/login-error")
////                )
////
////                .logout(logout ->
////                        logout
////                                //what is the logout url
////                                .logoutUrl("/users/logout")
////                                //where to go after logout
////                                .logoutSuccessUrl("/")
////                                //invalidate session after logout
////                                .invalidateHttpSession(true)
////                )
////                .build();
////    }
////
////    @Bean
////    public BankUserDetailsService bankUserDetailsService(UserRepository userRepository){
////        return new BankUserDetailsService(userRepository);
////    }
////
////
////    @Bean
////    public PasswordEncoder passwordEncoder() {
////        return Pbkdf2PasswordEncoder
////                .defaultsForSpringSecurity_v5_8(); // използване на готов метод за настройка
////        // return new Pbkdf2PasswordEncoder();
////    }
////}
//
