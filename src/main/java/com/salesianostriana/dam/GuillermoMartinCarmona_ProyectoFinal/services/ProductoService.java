package com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.modelo.Producto;
import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.repositorios.ProductoRepositorio;
import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.services.base.BaseServicempl;


@Service
public class ProductoService extends BaseServicempl<Producto, Long, ProductoRepositorio>{
	
	public List<Producto> obtenerProductos() {
		
		List<Long> listaIds=repository.obtenerIds();
		listaIds=listaIds.stream().limit(10).collect(Collectors.toList());
		return repository.findAllById(listaIds);
				
	}
	
}
