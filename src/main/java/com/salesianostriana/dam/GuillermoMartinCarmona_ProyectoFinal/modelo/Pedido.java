package com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Pedido {

	@Id @GeneratedValue
	private Long codigoPedido;
	private LocalDateTime fecha;
	private double total;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cliente_id",
		foreignKey = @ForeignKey(name = "fk_pedido_cliente"))
	private Usuario cliente;
	
	@OneToMany(mappedBy = "pedido", fetch = FetchType.EAGER)
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@Builder.Default
	private List<LineaPedido> lineasVenta = new ArrayList<>();

	
	/*
	public void addToCliente(Cliente cliente) {
		this.cliente = cliente;
		cliente.getPedidos().add(this);
	}
	
	public void removeFromCliente(Cliente cliente) {
		cliente.getPedidos().remove(this);
		this.cliente = null;
	}
	*/
	
}

