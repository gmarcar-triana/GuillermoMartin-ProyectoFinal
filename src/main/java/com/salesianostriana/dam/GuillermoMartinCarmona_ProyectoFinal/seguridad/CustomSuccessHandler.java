package com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.seguridad;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.java.Log;

@Component
@Log
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	private final String ROLE_USER_URL = "/user/dashboard";
	private final String ROLE_ADMIN_URL = "/admin/dashboard";
	private final String ROLE_DEFAULT_URL = "/login?error=Error en el rol asignado";

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		log.info("Authentication: " + authentication.toString());

		// 1. Comprobar si el usuario intentaba acceder a una URL protegida antes del login
		HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
		SavedRequest savedRequest = requestCache.getRequest(request, response);

		String redirectUrl;

		if (savedRequest != null) {
			// Hay una URL guardada: redirigir a donde el usuario quería ir
			redirectUrl = savedRequest.getRedirectUrl();
			log.info("Redirigiendo a la URL guardada: " + redirectUrl);
			requestCache.removeRequest(request, response); // limpiar la sesión
		} else {
			// No hay URL guardada: usar la URL por defecto según el rol
			String role = getMaxRole(authentication.getAuthorities());
			log.info("Max role: " + role);
			redirectUrl = determineTargetUrl(role);
			log.info("Redirigiendo a URL por rol: " + redirectUrl);
		}

		if (response.isCommitted()) {
			log.info("Can't redirect");
			return;
		}

		redirectStrategy.sendRedirect(request, response, redirectUrl);
	}

	private String getMaxRole(Collection<? extends GrantedAuthority> collection) {
		List<GrantedAuthority> authoritiesList = new ArrayList<>(collection);

		if (authoritiesList.isEmpty()) {
			return "ROLE_DEFAULT";
		}

		return authoritiesList
			.stream()
			.map(GrantedAuthority::getAuthority)
			.filter(a -> a.startsWith("ROLE_"))
			.sorted((role1, role2) ->
				role_weight.getOrDefault(role2, Integer.MIN_VALUE)
					- role_weight.getOrDefault(role1, Integer.MIN_VALUE))
			.findFirst()
			.get();
	}

	private String determineTargetUrl(String role) {
		return switch(role) {
			case "ROLE_ADMIN" -> ROLE_ADMIN_URL;
			case "ROLE_USER" -> ROLE_USER_URL;
			default -> ROLE_DEFAULT_URL;
		};
	}

	private static Map<String, Integer> role_weight = Map.of(
			"ROLE_ADMIN", 10,
			"ROLE_USER", 1
			);
}