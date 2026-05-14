package com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.services.producto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.entities.Producto;

@Service
public class ProductoService {


	private List <Producto> listaProductos = new ArrayList <Producto>();
	
	
	public void agregarProducto (Producto p) {
		
		listaProductos.add(p);
	}
	
	public List<Producto> getList (){
		return listaProductos;
	}

	
	
	
}
