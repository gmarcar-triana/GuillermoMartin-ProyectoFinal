package com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;

import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.modelo.Admin;
import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.modelo.Cliente;
import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.modelo.Usuario;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private AuthenticationSuccessHandler autentication;
	
	@Bean
	InMemoryUserDetailsManager userDetailsService() {
		Usuario admin = Admin.builder()
				.username("admin")
				.password("{noop}admin")
				.build();
		
		Usuario cliente = Cliente.builder()
				.username("user")
				.password("{noop}user")
				.build();
		
		
		return new InMemoryUserDetailsManager(admin, cliente);
	}
	
	
	
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    	RequestCache requesCache = new NullRequestCache();
    	
    	http.authorizeHttpRequests(
    			(authz) -> authz
					.requestMatchers("/css/**", "/js/**", "/error").permitAll()
					.requestMatchers("/admin/**").hasRole("ADMIN")
					.anyRequest().authenticated())
				.requestCache(cache -> cache.requestCache(requesCache))
				.formLogin((loginz) -> loginz
					.loginPage("/login")
					.successHandler(autentication)
					.permitAll()
		);
    	
        return http.build();
    }
    
    
}