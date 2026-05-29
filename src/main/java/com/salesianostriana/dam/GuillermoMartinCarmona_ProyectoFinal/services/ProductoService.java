package com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.services;

import java.util.Collections;
import java.util.List;
//import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.modelo.Producto;
import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.repositorios.ProductoRepositorio;
import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.services.base.BaseServicempl;


@Service
public class ProductoService extends BaseServicempl<Producto, Long, ProductoRepositorio>{
	
	@Autowired 
	private ProductoRepositorio productoRepositorio;
	
	public Page<Producto> buscarProductos(String termino, Pageable pageable) {
		
		return productoRepositorio.findByNombreContainingIgnoreCase(termino, pageable);
		
	}
	
	
	public List<Producto> obtenerTodosProductos() {
		
		return repository.findAll();
		
	}
	
	public List<Producto> obtenerProductosPorCategoria(String nombre) {
		
		return productoRepositorio.findByCategoria(nombre);
		
	}
	
	
	public List<Producto> obtenerProductoslimitadosAleatorios(int num) {
		
		List<Long> listaIds=repository.obtenerIds();
		Collections.shuffle(listaIds);
		listaIds=listaIds.stream().limit(num).collect(Collectors.toList());
		return repository.findAllById(listaIds);
				
	}
	
	public List<Producto> obtenerProductosEnOferta() {
		
		return repository.findProductosConDescuento();
		
	}
	
	/*
	public Producto obtenerIdProducto(Long id) {
		
		Optional<Producto> productoOptional = productoRepositorio.findById(id);
				
		return productoOptional.orElseThrow(() ->
				new IllegalArgumentException("El Producto no existe"));
		
	}
*/
	
}
