package com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

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

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.DecimalMin;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Producto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigoProducto;

	@NotEmpty(message = "El nombre no puede estar vacio")
	private String nombre;

	@DecimalMin(value = "0.01", message = "El precio debe ser mayor que 0")
	private double precio;

	private String descripcion;
	private boolean stock;

	@NotEmpty(message = "La categoria no puede estar vacia")
	private String categoria;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate fechaCaducidad;

	@DecimalMin(value = "0.0", message = "El descuento no puede ser negativo")
	private double descuento;

	private String imagenUrl;

	@jakarta.persistence.Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
	private boolean activo = true;

	@OneToMany(mappedBy = "producto", fetch = FetchType.EAGER)
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@Builder.Default
	private List<LineaPedido> lineasVenta = new ArrayList<>();

	public double getPrecioFinal() {
		return precio * (1.0 - descuento);
	}

	public boolean tieneDescuento() {
		return (descuento > 0);
	}

}