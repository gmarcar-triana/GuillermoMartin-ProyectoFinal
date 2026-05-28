package com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.seguridad;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
/*
import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.modelo.Cliente;
import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.modelo.Usuario;
*/
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private CustomSuccessHandler customSuccessHandler;
		
	public SecurityConfig(CustomSuccessHandler customSuccessHandler) {
		super();
		this.customSuccessHandler = customSuccessHandler;
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) {

		http.authorizeHttpRequests((authz) -> authz
					.requestMatchers("/admin/**").hasRole("ADMIN")
					.requestMatchers("/user/**").hasAnyRole("ADMIN", "USER")
					.requestMatchers("/", "/productos", "/ofertas", "/detalles/{id}", "/login").permitAll()					
					.anyRequest()
					.authenticated())
				.requestCache(cache -> {
		              HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
		              requestCache.setMatchingRequestParameterName(null);
		              cache.requestCache(requestCache);
		          })
				.formLogin(form -> form
				        .loginPage("/login")
				        .successHandler(customSuccessHandler)
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

		UserDetails user = User.builder().username("user").password("{noop}user").roles("USER").build();

		UserDetails admin = User.builder().username("admin").password("{noop}admin").roles("ADMIN").build();

		/*
		Usuario admin = new Cliente();
			admin.setUsername("admin");
			admin.setPassword("{noop}admin");
		*/
		
		manager.createUser(user);
		manager.createUser(admin);

		return manager;
	}

}
