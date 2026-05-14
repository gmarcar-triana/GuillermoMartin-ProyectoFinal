package com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.entities;

<<<<<<< HEAD
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
=======
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
>>>>>>> 576fe9eece71b1526a4a6c708248c188802c2357
@Entity
public class Cliente {

	@Id
<<<<<<< HEAD
	private Long codigo_cliente;
=======
	private String codigo_cliente;
>>>>>>> 576fe9eece71b1526a4a6c708248c188802c2357
	private String nombre;
	private String apellidos;
	private String email;
	private String telefono;
	
<<<<<<< HEAD
	@OneToMany(mappedBy = "cliente", fetch = FetchType.EAGER)
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@Builder.Default
	private List<Pedido> pedidos = new ArrayList<>();
	
}
=======
}
>>>>>>> 576fe9eece71b1526a4a6c708248c188802c2357
