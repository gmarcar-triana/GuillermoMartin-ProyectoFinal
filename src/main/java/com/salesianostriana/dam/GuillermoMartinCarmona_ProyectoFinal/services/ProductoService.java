package com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.modelo.Producto;
import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.repositorios.ProductoRepositorio;
import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.services.base.BaseServicempl;


@Service
public class ProductoService extends BaseServicempl<Producto, Long, ProductoRepositorio>{


	private List <Producto> listaProductos = new ArrayList <Producto>();
	
	
	public void agregarProducto(Producto p) {
		
		listaProductos.add(p);
	}
	
	public List<Producto> getList(){
		return listaProductos;
	}

	
	
	
}
