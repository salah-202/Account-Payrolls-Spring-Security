package com.example.accountpayrolls.configuration;

import com.example.accountpayrolls.exceptions.RestAccessDenied;
import com.example.accountpayrolls.exceptions.RestAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return new RestAuthenticationEntryPoint();
    }
    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new RestAccessDenied();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .exceptionHandling(auth->auth.authenticationEntryPoint(authenticationEntryPoint())
                        .accessDeniedHandler(accessDeniedHandler()))
                .authorizeHttpRequests(configure ->
                        configure.requestMatchers(HttpMethod.PUT,"api/admin/user/role").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE,"api/admin/user/{email}").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET,"api/admin/user").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT,"api/admin/user/access").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST,"api/acct/payments").hasRole("ACCOUNTANT")
                                .requestMatchers(HttpMethod.PUT,"api/acct/payments").hasRole("ACCOUNTANT")
                                .requestMatchers(HttpMethod.GET,"api/empl/payment").hasRole("USER")
                                .requestMatchers(HttpMethod.GET,"api/security/events").hasRole("AUDITOR")
                                .requestMatchers(HttpMethod.POST,"api/auth/changepass").authenticated()
                                .requestMatchers(HttpMethod.POST,"api/auth/signup").permitAll()
                )
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(csrf -> csrf.disable())
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
