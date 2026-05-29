package com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.controladores;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.modelo.Producto;
import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.services.ProductoService;

@Controller
public class CatalogoController {
/*
	private ProductoService productoService;
	
	@GetMapping({ "/listSort" })
	public String listadoProductosOrdenadoMax(Model model) {

		List<Producto> listaOrdenada = productoService.findAll();
		// metodo de la api para ordenar los precios de mayor a menor usando un comparador.
		listaOrdenada.sort(Comparator.comparing(Producto::getPrecio).reversed());

		model.addAttribute("listaProductos", listaOrdenada);

		return "index";

	}

	*/
}
