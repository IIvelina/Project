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
                                "/logout", "/job/apply").hasRole("CLIENT")
                        .requestMatchers("/director/dashboard").hasRole("DIRECTOR")
                        .requestMatchers("/user/admin/login", "/user/admin/dashboard").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/users/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/users/login?error=true")
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

