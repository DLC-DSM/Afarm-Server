package org.example.afarm.SecurityConfig;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.afarm.filter.LoginFilter;
import org.example.afarm.jwt.JWTFilter;
import org.example.afarm.jwt.JWTUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class config{

    private final AuthenticationConfiguration authenticationConfiguration;

    private final JWTUtil jwtUtil;

    private final ObjectMapper objectMapper;
    public config(AuthenticationConfiguration authenticationConfiguration, JWTUtil jwtUtil, ObjectMapper objectMapper) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtUtil = jwtUtil;
        this.objectMapper = objectMapper;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }



    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain afarmConfig(HttpSecurity http) throws Exception{

        http
                .csrf((auth) -> auth.disable());

        http
                .formLogin((auth) -> auth.disable());

        http
                .httpBasic((auth) -> auth.disable());


        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/","/login","/register","/user/register","/plantCho","PlantM/connect","PlantM/connect2").permitAll()
                        .requestMatchers("/my/**").hasAnyRole("USER")
                        .anyRequest().permitAll() // 이것 반드시 바꾸기.
                );

        http
                .addFilterAt(new JWTFilter(jwtUtil), LoginFilter.class);

        http
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration),jwtUtil,objectMapper), UsernamePasswordAuthenticationFilter.class);

        http
                .logout((auth)-> auth
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                );

        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

}
