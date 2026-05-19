package com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(
        		(authz) -> authz
                	.requestMatchers("/", "/index", "/productos/**", "/login", "/registro").permitAll()
                	.requestMatchers("/css/**", "/js/**", "/img/**").permitAll()
                	.requestMatchers("/admin/**").hasRole("ADMIN")
                	.requestMatchers("/compra/**", "/carrito/**").hasAnyRole("CLIENTE", "ADMIN")
                	.anyRequest().authenticated())
        		.requestCache(cache -> {
		            HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
		            requestCache.setMatchingRequestParameterName(null);
		            cache.requestCache(requestCache);
        		})
        		.formLogin(form -> form
	                .loginPage("/login")
	                .defaultSuccessUrl("/", false) 
	                .permitAll()
        		);

        http.csrf(csrf -> {
            csrf.ignoringRequestMatchers("/h2/**");
        });
        http.headers(headers -> headers.frameOptions(opts -> opts.disable()));

        return http.build();
    }
}