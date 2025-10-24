package client.asta_lsi2.config;

import client.asta_lsi2.models.Role;
import client.asta_lsi2.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    public SecurityConfig(CustomUserDetailsService userDetailsService ) {
        this.userDetailsService = userDetailsService;
    }

    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    //     return http.csrf(csrf -> csrf.disable())// à activer en prod
    //             .authorizeHttpRequests(
    //                     authorizeRequests -> authorizeRequests
    //                             .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
    //                             .requestMatchers("/","/login", "/register/**", "/dashboard/**", "/apprenti/deleteapprenti/**")
    //                             .permitAll()
    //                             .requestMatchers("/apprenti/**").hasRole(Role.APPRENTI.name())
    //                             .requestMatchers("/maitre/**").hasRole(Role.MAITRE_APPRENTISSAGE.name())
    //                             .requestMatchers("/api/**").hasAnyRole(Role.APPRENTI.name(), Role.MAITRE_APPRENTISSAGE.name())
    //                             .anyRequest()
    //                             .authenticated()
    //             )
    //             .build();
    // }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())// à activer en prod
                .authorizeHttpRequests(
                        authorizeRequests -> authorizeRequests
                                .requestMatchers("/**").permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http,PasswordEncoder passwordEncoder) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder= http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);

        return authenticationManagerBuilder.build();
    }

}