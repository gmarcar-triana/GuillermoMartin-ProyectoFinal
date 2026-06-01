package com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.controladores;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.modelo.Producto;
import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.services.ProductoService;

@Controller
public class MainController {

	@Autowired
	private ProductoService productoService;
		
	@GetMapping("/")
	public String cargarIndex(Model model) {
		
		List<Producto> productos;
		productos=productoService.obtenerProductoslimitadosAleatorios(10);
		model.addAttribute("listaProductos", productos);
		
		return "index";
		
	}
	
	@GetMapping("/productos")
	public String listadoProductos(@RequestParam(value = "sort", required = false) String sort, Model model) {

		List<Producto> productos = new java.util.ArrayList<>(productoService.obtenerTodosProductos());
		
		if ("asc".equalsIgnoreCase(sort)) {
			productos.sort(java.util.Comparator.comparingDouble(Producto::getPrecioFinal));
		} else if ("desc".equalsIgnoreCase(sort)) {
			productos.sort(java.util.Comparator.comparingDouble(Producto::getPrecioFinal).reversed());
		}
		
		model.addAttribute("listaProductos", productos);
		model.addAttribute("currentSort", sort != null ? sort : "default");

		return "productos";

	}
	
	@GetMapping("/productos/{nombre}")
	public String listarPorCategoria(@PathVariable("nombre") String nombre, Model model) {
		
		List<Producto> productos = productoService.obtenerProductosPorCategoria(nombre);
		
		model.addAttribute("listaProductos", productos);
		return "productos";
		
		
	}
	
	
	@GetMapping("/ofertas")
	public String listadoOfertas(Model model) {
		
		List<Producto> productos;
		productos=productoService.obtenerProductosEnOferta();
		model.addAttribute("listaProductos", productos);
		
		return "ofertas";
		
	}
	
	@GetMapping("/detalles/{id}")
	public String detallesProducto(@PathVariable("id") Long id, Model model) {
		
		Optional <Producto> producto = productoService.findById(id);
		List<Producto> productos;
		productos=productoService.obtenerProductoslimitadosAleatorios(5);

		if (producto.isPresent()) {
			model.addAttribute("producto", producto.get());
			model.addAttribute("listaProductos", productos);
			return "productDetails";
		}			
			
		return "redirect:/";
		
	}
	
	
}
