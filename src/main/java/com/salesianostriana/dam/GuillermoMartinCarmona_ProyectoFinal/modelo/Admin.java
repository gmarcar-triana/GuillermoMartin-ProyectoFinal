package com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.modelo;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@SuppressWarnings("serial")
@Entity
@DiscriminatorValue("A")
public class Admin extends Usuario {

	public Admin(Long codigoUsuario, String username, String password, String nombre, String apellidos, String email,
			String telefono) {
		super(codigoUsuario, username, password, nombre, apellidos, email, telefono);
	}

	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }	
	
}