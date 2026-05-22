package com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.security;
/*
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.modelo.Admin;
import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.modelo.Cliente;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) {

		http.authorizeHttpRequests(
				(authz) -> authz
					.requestMatchers("/producto/**").authenticated()
					.requestMatchers("/", "/index", "/login", "/css/**", "/js/**", "/img/**").permitAll()
					.anyRequest()
					.authenticated())
					//.permitAll())
				  .requestCache(cache -> {
		              HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
		              requestCache.setMatchingRequestParameterName(null);
		              cache.requestCache(requestCache);
		          })
				.formLogin(form -> form
						.loginPage("/login")
						.defaultSuccessUrl("/producto/list", true)
						.permitAll()
				);

		http.csrf((csrf) -> {
			csrf.ignoringRequestMatchers("/h2/**");
		});
		http.headers((headers) -> headers.frameOptions((opts) -> opts.disable()));

		return http.build();
	}

	@Bean
	UserDetailsService userDetailsService() {
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

		UserDetails user = Cliente.builder()
				.username("user")
				.password("{noop}user")
				.roles(UserRole.USER.name())
				.build();

		UserDetails admin = Admin.builder()
				.username("admin")
				.password("{noop}admin")
				.roles(UserRole.ADMIN.name())
				.build();

		manager.createUser(user);
		manager.createUser(admin);

		return manager;
	}
	
}

*/