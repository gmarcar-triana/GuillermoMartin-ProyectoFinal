package com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.entities;

import java.time.LocalDate;
<<<<<<< HEAD
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Producto {

	@Id @GeneratedValue
	private String codigo_producto;
	
=======

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity
public class Producto {

	@Id
	private String codigo_producto;
>>>>>>> 576fe9eece71b1526a4a6c708248c188802c2357
	private String nombre;
	private double precio;
	private String descripcion;
	private boolean stock;
	private String categoria;
	private LocalDate fechaCaducidad;
<<<<<<< HEAD
	
	private String imagenUrl;
	
	@OneToMany(mappedBy = "producto", fetch = FetchType.EAGER)
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@Builder.Default
	private List<LineaVenta> lineasVenta = new ArrayList<>();
		
}
=======
		
}

>>>>>>> 576fe9eece71b1526a4a6c708248c188802c2357
