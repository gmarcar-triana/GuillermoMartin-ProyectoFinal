package com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.controllers.producto;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.entities.Producto;
import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.services.producto.ProductoService;

@Controller
public class ProductoController {

	@Autowired
	private ProductoService productService;
	
	@GetMapping ({"/", "/list"})
	public String listadoProductos(Model model) {
		
		model.addAttribute("listaProductos", productService.getList());
		
		return "index";
		
	}
	
	@GetMapping ({"/listSort"})
	public String listadoProductosOrdenadoMax(Model model) {
		
		List<Producto> listaOrdenada = productService.getList();
		//metodo de la api para ordenar los precios de mayor a menor usando un comparador.
		listaOrdenada.sort(Comparator.comparing(Producto::getPrecio).reversed());
		
		model.addAttribute("listaProductos", listaOrdenada);
		
		return "index";
		
	}
	
	@GetMapping("/nuevoProducto")
	public String mosrtrarFormularioProducto(Model model) {
		
		model.addAttribute("producto", new Producto());
		
		return "productform";
		
	}
	
	@PostMapping("/nuevo/submit")
	public String procesaFormularioProducto(@ModelAttribute("producto") Producto producto) {
		
		productService.agregarProducto(producto);
		
		return "redirect:/";
	
	}
	
	@GetMapping({"/producto"})
	public String showFormProducto(Model model) {
		
		Producto producto = new Producto();
		model.addAttribute("productoForm", producto);
		
		return "formProducto";
		
	}
	
	
	//Metodos CRUD para la gestion de los productos
	
	@PostMapping ({"/addProducto"})
	public String formularioAgregarProducto(@ModelAttribute("productoForm")Producto producto, Model model) {
		
		model.addAttribute("producto", producto);
		
		return "agregar";
		
	}
	
	
	
	
	
	
	
	
}
