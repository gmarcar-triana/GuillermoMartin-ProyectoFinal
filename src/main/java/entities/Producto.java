package entities;

import java.time.LocalDate;

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
	private String nombre;
	private double precio;
	private String descripcion;
	private boolean stock;
	private String categoria;
	private LocalDate fechaCaducidad;
		
}

