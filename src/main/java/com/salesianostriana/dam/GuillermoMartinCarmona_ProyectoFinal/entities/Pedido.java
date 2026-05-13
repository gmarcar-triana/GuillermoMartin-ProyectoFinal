package com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity
public class Pedido {

	@Id
	private String codigo_pedido;
	private LocalDateTime fecha;
	private double total;
	
	@ManyToOne
	private Cliente cliente;
	
}

