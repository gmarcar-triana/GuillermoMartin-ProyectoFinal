package com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.modelo.Categoria;

public interface CategoriaRepositorio extends JpaRepository<Categoria, Long> {

	public List<Categoria> findByNombre(String nombre);
	
}
