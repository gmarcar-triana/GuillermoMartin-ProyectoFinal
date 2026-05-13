package com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity
public class LineaVenta {

	@Id
	private Long id;
	private int cantidad;
	
	@ManyToOne
	private Pedido pedido;
	
	@ManyToOne
	private Producto producto;
	
}
