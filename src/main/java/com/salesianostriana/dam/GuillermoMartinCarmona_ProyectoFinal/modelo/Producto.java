package com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long codigoProducto;
	
	private String nombre;
	private double precio;
	private String descripcion;
	private boolean stock;
	private String categoria;
	private LocalDate fechaCaducidad;
	
	private String imagenUrl;
	
	@OneToMany(mappedBy = "producto", fetch = FetchType.EAGER)
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@Builder.Default
	private List<LineaPedido> lineasVenta = new ArrayList<>();
		
}