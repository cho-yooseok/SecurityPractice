package com.example.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity // 생략
public class SecurityConfig {
    // SecurityFilterChain
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
         // HttpSecurity - 인증여부에 따른 요청을 허용, 불허용, 권한에 따른 요청을 허용, 불허용
         http
                 .authorizeHttpRequests(authz->authz
                         .requestMatchers("/api/**").authenticated()
                         .requestMatchers("/book/**").authenticated()
                         .anyRequest().permitAll()
         )
                 .formLogin(form->form
                         .loginPage("/ui/list")
                         .loginProcessingUrl("/login")
                         .defaultSuccessUrl("/ui/list", true)
          )
                 .logout(logout -> logout
                         .logoutUrl("/logout")
                         .logoutSuccessUrl("/ui/list")
                         .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                         .clearAuthentication(true)
                         .deleteCookies("JSESSIONID")
                         .invalidateHttpSession(true)
          );
        return http.build();
    }
}
