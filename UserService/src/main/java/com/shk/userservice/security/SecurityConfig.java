//package com.shk.userservice.security;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class SecurityConfig {
//
//    private final JwtAuthenticationFilter JwtAuthenticationFilter;
//    private final AuthenticationProvider authenticationProvider;
//
//
////    @Bean
////    @Order(1)
////        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////            http
////                    .cors(Customizer.withDefaults())
////                    .csrf(Customizer.withDefaults())
////                    .authorizeHttpRequests((authorize) -> authorize
////                            .requestMatchers("/api/v1/**").permitAll()
////                            //.anyRequest().authenticated()
////                    )
////                    .sessionManagement(session -> session
////                            .maximumSessions(1)
////                    )
////                    .sessionManagement((session) -> session
////                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
////                    )
////                    .authenticationProvider(authenticationProvider)
////                    .addFilterBefore(JwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
////                    //.formLogin(Customizer.withDefaults());
////
////            return http.build();
////        }
//
//
//}
