package com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.controllers;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.entities.Producto;
import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.services.ProductoService;


@Controller
public class ProductoController {

	@Autowired
<<<<<<< HEAD
	private ProductoService productService;
=======
	private ProductoService service;
>>>>>>> 576fe9eece71b1526a4a6c708248c188802c2357
	
	@GetMapping ({"/", "/list"})
	public String listadoProductos(Model model) {
		
<<<<<<< HEAD
		model.addAttribute("listaProductos", productService.getList());
=======
		model.addAttribute("listaProductos", service.getList());
		
>>>>>>> 576fe9eece71b1526a4a6c708248c188802c2357
		return "index";
		
	}
	
	@GetMapping ({"/listSort"})
	public String listadoProductosOrdenadoMax(Model model) {
		
<<<<<<< HEAD
		List<Producto> listaOrdenada = productService.getList();
=======
		List<Producto> listaOrdenada = service.getList();
>>>>>>> 576fe9eece71b1526a4a6c708248c188802c2357
		//metodo de la api para ordenar los precios de mayor a menor usando un comparador.
		listaOrdenada.sort(Comparator.comparing(Producto::getPrecio).reversed());
		
		model.addAttribute("listaProductos", listaOrdenada);
<<<<<<< HEAD
=======
		
>>>>>>> 576fe9eece71b1526a4a6c708248c188802c2357
		return "index";
		
	}
	
<<<<<<< HEAD
	@GetMapping("/nuevoProducto")
	public String mosrtrarFormularioProducto(Model model) {
		
		model.addAttribute("producto", new Producto());
		
		return "productform";
		
	}
	
	@PostMapping("/nuevo/submit")
	public String procesaFormularioProducto(@ModelAttribute("producto") Producto producto) {
		
		productService.agregarProducto(producto);
		
		return "redirect:/";
=======
	@GetMapping({"/producto"})
	public String showFormProducto(Model model) {
		
		Producto producto = new Producto();
		model.addAttribute("productoForm", producto);
		
		return "formProducto";
		
	}
	
	
	@PostMapping ({"/addProducto"})
	public String formularioAgregarProducto(@ModelAttribute("productoForm")Producto producto, Model model) {
		
		model.addAttribute("producto", producto);
		
		return "agregar";
>>>>>>> 576fe9eece71b1526a4a6c708248c188802c2357
		
	}
	
	
	
}
