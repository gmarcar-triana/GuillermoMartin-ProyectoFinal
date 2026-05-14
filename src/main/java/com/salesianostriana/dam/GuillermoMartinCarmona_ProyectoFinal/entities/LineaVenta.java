package com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.entities;

import jakarta.persistence.Entity;
<<<<<<< HEAD
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class LineaVenta {

	@Id @GeneratedValue
	private Long id;
	private int cantidad;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "codigo_pedido",
		foreignKey = @ForeignKey(name = "fk_lineaventa_pedido"))
	private Pedido pedido;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "producto_id",
		foreignKey = @ForeignKey(name = "fk_lineaventa_producto"))
	private Producto producto;

	public void addToPedido(Pedido pedido) {
		this.pedido = pedido;
		pedido.getLineasVenta().add(this);
	}

	public void removeFromPedido(Pedido pedido) {
		pedido.getLineasVenta().remove(this);
		this.pedido = null;
	}

	public void addToProducto(Producto producto) {
		this.producto = producto;
		producto.getLineasVenta().add(this);
	}

	public void removeFromProducto(Producto producto) {
		producto.getLineasVenta().remove(this);
		this.producto = null;
	}
=======
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
	
>>>>>>> 576fe9eece71b1526a4a6c708248c188802c2357
}
