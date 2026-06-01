package com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.modelo;


import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.exception.EmailInvalidoException;

@SuppressWarnings("serial")
@Data
@Entity
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Usuario implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long codigoUsuario;
	
	private String username;
	private String password;
	
	private String nombre;
	private String apellidos;
	private String email;
	private String telefono;
	
	private String roles;


	public Usuario(Long codigoUsuario, String username, String password, String nombre, String apellidos, String email,
			String telefono, String roles) {
		this.codigoUsuario = codigoUsuario;
		this.username = username;
		this.password = password;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.telefono = telefono;
		this.roles = roles;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}	
	
	public void setEmail(String email) {
		if (email != null && !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
			throw new EmailInvalidoException("El formato del correo electronico no es valido");
		}
		this.email = email;
	}
}