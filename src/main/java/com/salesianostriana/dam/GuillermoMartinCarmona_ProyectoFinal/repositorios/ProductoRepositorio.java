package com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.modelo.Producto;

public interface ProductoRepositorio extends JpaRepository<Producto, Long>{

	@Query("select p.id from Producto p")
	public List<Long> obtenerIds();

	
}
