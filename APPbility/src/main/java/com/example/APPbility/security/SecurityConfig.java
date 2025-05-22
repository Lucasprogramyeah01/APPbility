package com.example.APPbility.security;

import com.example.APPbility.security.exceptionHandling.JwtAccessDeniedHandler;
import com.example.APPbility.security.exceptionHandling.JwtAuthenticationEntryPoint;
import com.example.APPbility.security.jwt.access.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;
    private final JwtAccessDeniedHandler accessDeniedHandler;

    @Bean
    AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {

        AuthenticationManagerBuilder authenticationManagerBuilder =
            http.getSharedObject(AuthenticationManagerBuilder.class);

        AuthenticationManager authenticationManager =
            authenticationManagerBuilder.authenticationProvider(authenticationProvider())
            .build();

        return authenticationManager;
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider p = new DaoAuthenticationProvider();

        p.setUserDetailsService(userDetailsService);
        p.setPasswordEncoder(passwordEncoder);
        return p;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable());
        http.cors(withDefaults());
        http.sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.exceptionHandling(excepz -> excepz
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
        );
        http.authorizeHttpRequests(authz -> authz

                //PERMIT ALL
                .requestMatchers("/h2-console/**", "/swagger-ui/**", "/v3/api-docs/**",
                    "/uploads/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/user/", "/user/{id}", "/pais/", "/pais/{id}",
                    "/continente/", "/continente/{id}", "/nivel/" /*"/tag/", "/tag/{id}",*/).permitAll()
                .requestMatchers(HttpMethod.POST, "/auth/register", "/auth/login", "/auth/refresh/token",
                    "/activate/account/","/error").permitAll()

                //ADMIN
                .requestMatchers("/me/admin").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/continente/", "/pais/").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/continente/{id}", "/pais/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/continente/{id}", "/pais/{id}").hasRole("ADMIN")

                //USER
                .requestMatchers("/me").hasRole("USER")
                /*.requestMatchers(HttpMethod.POST, "/talento/").hasRole("USER")
                .requestMatchers(HttpMethod.PUT, "/talento/{id}").hasRole("USER")*/

                .anyRequest().authenticated()).httpBasic(withDefaults()); // Habilita Basic Auth);

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));

        return http.build();
    }

}
