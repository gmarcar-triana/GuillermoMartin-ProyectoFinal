package com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.services;

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
public class ProductoService extends BaseServicempl<Producto, Long, ProductoRepositorio>{
	
	@Autowired 
	private ProductoRepositorio productoRepositorio;
	
	public Page<Producto> buscarProductos(String termino, Pageable pageable) {
		return productoRepositorio.findByNombreContainingIgnoreCase(termino, pageable);
	}
	
	
	
	public List<Producto> obtenerProductos() {
		
		List<Long> listaIds=repository.obtenerIds();
		listaIds=listaIds.stream().limit(10).collect(Collectors.toList());
		return repository.findAllById(listaIds);
				
	}
	
}
