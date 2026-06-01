package com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.repositorios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.modelo.Producto;

public interface ProductoRepositorio extends JpaRepository<Producto, Long> {

	@Query("select p.codigoProducto from Producto p where p.activo = true")
	public List<Long> obtenerIds();

	@Query("SELECT p FROM Producto p WHERE p.descuento > 0 AND p.activo = true")
	public List<Producto> findProductosConDescuento();

	public List<Producto> findByActivoTrueAndCategoria(String nombre);

	public List<Producto> findByActivoTrueAndNombreContainingIgnoreCase(String nombre);

	public Page<Producto> findByActivoTrueAndNombreContainingIgnoreCase(String nombre, Pageable pageable);

	public List<Producto> findAllByActivoTrue();

}
