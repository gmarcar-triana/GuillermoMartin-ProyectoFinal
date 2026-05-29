package com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.modelo.Usuario;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findByUsername(String username);

}
