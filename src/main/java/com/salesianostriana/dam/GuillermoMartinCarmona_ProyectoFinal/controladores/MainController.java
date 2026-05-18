package com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.modelo.Producto;
import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.services.ProductoService;

@Controller
public class MainController {

	@Autowired
	private ProductoService productoService;
	
	
	@GetMapping("/")
	public String cargarIndex(Model model) {
		
		List<Producto> productos;
		
		productos=productoService.obtenerProductos();
		model.addAttribute("listaProductos", productos);
		
		return "index";
		
	}
	
	
	
}
