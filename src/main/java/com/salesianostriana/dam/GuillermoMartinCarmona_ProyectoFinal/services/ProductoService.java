package com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.services;

import java.time.LocalDate;
<<<<<<< HEAD
import java.util.ArrayList;
=======
>>>>>>> 576fe9eece71b1526a4a6c708248c188802c2357
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.entities.Producto;

@Service
public class ProductoService {

<<<<<<< HEAD

	private List <Producto> listaProductos = new ArrayList <Producto>();
	
	/*
	public List<Producto> getList() {
		return Arrays.asList(
				new Producto(101, "Agua", 1.20, "Botella de agua mineralizada", true, "Bebidas", LocalDate.of(2026, 6, 3)),
				new Producto(102, "Pan", 0.95, "Barra de pan recién horneada", true, "Panadería", LocalDate.of(2026, 6, 3)),
				new Producto(103, "Leche", 1.10, "Leche entera pasteurizada 1L", true, "Lácteos", LocalDate.of(2026, 6, 3)),
				new Producto(104, "Huevos", 2.50, "Docena de huevos camperos", true, "Huevos y derivados", LocalDate.of(2026, 6, 3)),
				new Producto(105, "Café", 3.80, "Paquete de café molido 250g", true, "Despensa", LocalDate.of(2026, 6, 3))
				);
	}
	*/
	
	
	public void agregarProducto (Producto p) {
		
		listaProductos.add(p);
	}
	
	public List<Producto> getList (){
		return listaProductos; 
	}

=======
	public List<Producto> getList() {
		return Arrays.asList(
				new Producto("cod-101", "Agua", 1.20, "Botella de agua mineralizada", true, "Bebidas", LocalDate.of(2026, 6, 3)),
				new Producto("cod-102", "Pan", 0.95, "Barra de pan recién horneada", true, "Panadería", LocalDate.of(2026, 6, 3)),
				new Producto("cod-103", "Leche", 1.10, "Leche entera pasteurizada 1L", true, "Lácteos", LocalDate.of(2026, 6, 3)),
				new Producto("cod-104", "Huevos", 2.50, "Docena de huevos camperos", true, "Huevos y derivados", LocalDate.of(2026, 6, 3)),
				new Producto("cod-105", "Café", 3.80, "Paquete de café molido 250g", true, "Despensa", LocalDate.of(2026, 6, 3))
				);
	}
>>>>>>> 576fe9eece71b1526a4a6c708248c188802c2357
	
	
	
}
