package com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.seguridad;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.java.Log;

@Component
@Log
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	private final String ROLE_USER_URL = "/";
	private final String ROLE_ADMIN_URL = "/admin/dashboard";
	private final String ROLE_DEFAULT_URL = "/login?error=Error en el rol asignado";

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		log.info("Authentication: " + authentication.toString());

		String role = getMaxRole(authentication.getAuthorities());
		log.info("Max role: " + role);
		String redirectUrl = determineTargetUrl(role);
		log.info("Redirigiendo a URL por rol: " + redirectUrl);

		HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
		requestCache.removeRequest(request, response);

		if (response.isCommitted()) {
			log.info("Can't redirect");
			return;
		}

		redirectStrategy.sendRedirect(request, response, redirectUrl);
	}

	private String getMaxRole(Collection<? extends GrantedAuthority> collection) {
		if (collection == null || collection.isEmpty()) {
			return "ROLE_DEFAULT";
		}
		for (GrantedAuthority authority : collection) {
			String auth = authority.getAuthority();
			if (auth != null && (auth.equals("ROLE_ADMIN") || auth.equals("ADMIN"))) {
				return "ROLE_ADMIN";
			}
		}
		for (GrantedAuthority authority : collection) {
			String auth = authority.getAuthority();
			if (auth != null && (auth.equals("ROLE_CLIENTE") || auth.equals("CLIENTE") || auth.equals("ROLE_USER") || auth.equals("USER"))) {
				return "ROLE_CLIENTE";
			}
		}
		return "ROLE_DEFAULT";
	}

	private String determineTargetUrl(String role) {
		return switch(role) {
			case "ROLE_ADMIN" -> ROLE_ADMIN_URL;
			case "ROLE_CLIENTE" -> "/";
			case "ROLE_USER" -> "/";
			default -> ROLE_DEFAULT_URL;
		};
	}

	private static Map<String, Integer> role_weight = Map.of(
			"ROLE_ADMIN", 10,
			"ROLE_CLIENTE", 1,
			"ROLE_USER", 1
			);
}