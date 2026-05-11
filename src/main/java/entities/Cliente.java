package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity
public class Cliente {

	@Id
	private String codigo_cliente;
	private String nombre;
	private String apellidos;
	private String email;
	private String telefono;
	
}
