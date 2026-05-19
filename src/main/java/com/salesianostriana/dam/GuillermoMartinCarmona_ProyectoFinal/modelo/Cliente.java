package com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.modelo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@SuppressWarnings("serial")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@DiscriminatorValue("C")
public class Cliente extends Usuario{
	
	@OneToMany(mappedBy = "cliente", fetch = FetchType.EAGER)
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@Builder.Default
	private List<Pedido> pedidos = new ArrayList<>();
	
	public Cliente(Long codigoUsuario, String username, String password, String nombre, String apellidos, String email,
			String telefono, List<Pedido> pedidos) {
		super(codigoUsuario, username, password, nombre, apellidos, email, telefono);
		this.pedidos = pedidos;
	}

	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_CLIENTE"));
    }
	
}