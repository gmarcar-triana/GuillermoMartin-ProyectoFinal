package com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nombre;
	private String imagenUrl;
	
	public Categoria(String nombre, String imagenUrl) {
		this.nombre = nombre;
		this.imagenUrl = imagenUrl;
	}
	
}
