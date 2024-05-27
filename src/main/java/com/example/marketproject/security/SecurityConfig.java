package com.example.marketproject.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {

        security.authorizeHttpRequests(manager->{
            manager
                    .requestMatchers(
                            "/income",
                            "/file/**",
                            "/css/**",
                            "/product/**",
                            "/category/**",
                            "/basket/**",
                            "/auth/**",
                            "/error/**",
                            "/login/**",
                            "/admin/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated();
        });

        security.formLogin(login->{
            login.loginPage("/login").defaultSuccessUrl("/");
        });
        security.userDetailsService(userDetailService);
        security.csrf(AbstractHttpConfigurer::disable);

        return security.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();

    }

}
