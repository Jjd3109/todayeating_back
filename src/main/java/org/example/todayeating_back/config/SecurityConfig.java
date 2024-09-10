package org.example.todayeating_back.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenFilter jwtTokenFilter;

    public SecurityConfig(@Lazy JwtTokenFilter jwtTokenFilter) {
        this.jwtTokenFilter = jwtTokenFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCrypt Encoder 사용
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);
        http.sessionManagement(sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );
        http.formLogin(formLogin -> formLogin.disable());
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/api/v1/find/mapInfo/*").hasRole("USER")

                .requestMatchers("/api/v1/save/mapInfo").hasRole("USER")
                .requestMatchers("/api/v1/delete/mapInfo").hasRole("USER")
                .requestMatchers("/api/v1/save/room").hasRole("USER")
                .requestMatchers("/api/v1/find/memberInfo").hasRole("USER")
                .requestMatchers("/api/v1/update/memberInfo").hasRole("USER")
                .requestMatchers("/api/v1/save/ramc").hasRole("USER")
                .requestMatchers("/images/**").permitAll()
                .requestMatchers("/api/v1/save/member").permitAll()
                .requestMatchers("/api/v1/login/member").permitAll()
                .requestMatchers("/api/v1/find/rooms").permitAll()
                .requestMatchers("/api/v1/find/myRooms").hasRole("USER")
                .anyRequest().anonymous()
        );
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
