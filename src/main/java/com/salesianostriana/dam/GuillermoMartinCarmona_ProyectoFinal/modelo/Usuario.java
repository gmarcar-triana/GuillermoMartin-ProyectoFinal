package com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.modelo;

import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@Entity
@Builder
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


	public Usuario(Long codigoUsuario, String username, String password, String nombre, String apellidos, String email,
			String telefono) {
		this.codigoUsuario = codigoUsuario;
		this.username = username;
		this.password = password;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.telefono = telefono;
	}
	
	
	
	
}