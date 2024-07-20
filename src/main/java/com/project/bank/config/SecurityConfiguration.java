package com.project.bank.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfiguration {

    //todo

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                .authorizeHttpRequests(
//                        //Setup which URL-s are available to who
//                        authorizeRequests ->
//                                authorizeRequests
//                                        //all static resources to common locations (css, img, js) are available to anyone
//                                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
//                                        //some more resources for all users
//                                        .requestMatchers("/", "/users/login", "/users/register").permitAll()
//                                        //all other URL-s should be authenticated
//                                        .anyRequest().authenticated()
//                )
//                .formLogin(formLogin ->
//                        formLogin
//                                //where is our login form
//                                .loginPage("/users/login")
//                                //what is the name of the username parameter on the login POST request
//                                .usernameParameter("username")
//                                //what is the name of the password parameter on the login POST request
//                                .passwordParameter("password")
//                                //what will happen if the login is successful
//                                .defaultSuccessUrl("/")
//                                //what will happen if the login fails
//                                .failureForwardUrl("users/login-error")
//                )
//
//                .logout(logout ->
//                        logout
//                                //what is the logout url
//                                .logoutUrl("/users/logout")
//                                //where to go after logout
//                                .logoutSuccessUrl("/")
//                                //invalidate session after logout
//                                .invalidateHttpSession(true)
//                )
//                .build();
//    }
}
