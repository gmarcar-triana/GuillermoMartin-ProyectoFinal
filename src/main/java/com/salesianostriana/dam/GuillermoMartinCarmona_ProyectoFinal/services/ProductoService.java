package com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.services;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.modelo.Producto;
import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.repositorios.ProductoRepositorio;
import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.services.base.BaseServicempl;

@Service
public class ProductoService extends BaseServicempl<Producto, Long, ProductoRepositorio> {

	@Autowired
	private ProductoRepositorio productoRepositorio;

	public void desactivar(Long id) {
		productoRepositorio.findById(id).ifPresent(p -> {
			p.setActivo(false);
			productoRepositorio.save(p);
		});
	}

	public Page<Producto> buscarProductos(String termino, Pageable pageable) {
		return productoRepositorio.findByActivoTrueAndNombreContainingIgnoreCase(termino, pageable);
	}

	public List<Producto> obtenerTodosProductos() {
		return repository.findAll().stream()
				.filter(Producto::isActivo)
				.collect(Collectors.toList());
	}

	public List<Producto> obtenerProductosPorCategoria(String nombre) {
		return productoRepositorio.findByActivoTrueAndCategoria(nombre);
	}

	public List<Producto> obtenerProductoslimitadosAleatorios(int num) {
		List<Producto> activos = repository.findAll().stream()
				.filter(Producto::isActivo)
				.collect(Collectors.toList());
		Collections.shuffle(activos);
		return activos.stream().limit(num).collect(Collectors.toList());
	}

	public List<Producto> obtenerProductosEnOferta() {
		return repository.findAll().stream()
				.filter(p -> p.isActivo() && p.getDescuento() > 0)
				.collect(Collectors.toList());
	}

}
