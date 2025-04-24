package edu.icet.ecom.config;

import org.springframework.security.authentication.AuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    final private UserDetailsService userDetailsService;
@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    return  http
            .csrf(customizer-> customizer.disable())
            .authorizeHttpRequests(request-> request.anyRequest().authenticated())
            .httpBasic(Customizer.withDefaults())
            .sessionManagement(session ->
                 session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .build();
      }
@Bean
    public AuthenticationProvider authenticationProvider(){
         DaoAuthenticationProvider provider =new DaoAuthenticationProvider();
         provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
         provider.setUserDetailsService(userDetailsService);
         return provider;
}


//      @Bean
//    public UserDetailsService userDetailsService() {
//          UserDetails user1= User
//                  .withDefaultPasswordEncoder()
//                  .username("nissa")
//                  .password("123")
//                  .roles("USER")
//                  .build();
//
//          UserDetails user2= User
//                  .withDefaultPasswordEncoder()
//                  .username("nuwan")
//                  .password("456")
//                  .roles("ADMIN")
//                  .build();
//
//    return new InMemoryUserDetailsManager(user2,user1);
//      }



}

