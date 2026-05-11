package controllers;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import entities.Producto;
import services.ProductoService;

@Controller
public class ProductoController {

	@Autowired
	private ProductoService service;
	
	@GetMapping ({"/", "/list"})
	public String listadoProductos(Model model) {
		
		model.addAttribute("listaProductos", service.getList());
		return "index";
		
	}
	
	@GetMapping ({"/listSort"})
	public String listadoProductosOrdenadoMax(Model model) {
		
		List<Producto> listaOrdenada = service.getList();
		//metodo de la api para ordenar los precios de mayor a menor usando un comparador.
		listaOrdenada.sort(Comparator.comparing(Producto::getPrecio).reversed());
		
		model.addAttribute("listaProductos", listaOrdenada);
		return "index";
		
	}
	
	
	
	
}
