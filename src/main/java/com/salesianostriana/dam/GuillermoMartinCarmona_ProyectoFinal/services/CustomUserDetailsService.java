package com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.repositorios.UsuarioRepositorio;
import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.exception.NullUserLoginException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final UsuarioRepositorio usuarioRepositorio;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (username == null || username.trim().isEmpty()) {
			throw new NullUserLoginException("El nombre de usuario no puede estar vacio");
		}
		return usuarioRepositorio.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
	}
}
