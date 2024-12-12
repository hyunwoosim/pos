package com.mulook.pos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable());



        http.authorizeHttpRequests((authorize) ->
                                       authorize.requestMatchers("/**").permitAll()
        );

//        http.formLogin((formLogin)
//                       -> formLogin.loginPage("/login")
//            .defaultSuccessUrl("/")
//            .failureUrl("/fail")
//        );

//        http.logout(logout -> logout
//            .logoutUrl("/logout")
//            .logoutSuccessUrl("/")
//            .deleteCookies("JSESSIONID")
//        );

        return http.build();
    }

}