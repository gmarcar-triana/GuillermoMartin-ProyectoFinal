package com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.controladores;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;

import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.modelo.Producto;
import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.modelo.Pedido;
import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.repositorios.PedidoRepositorio;
import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.services.ProductoService;

@Controller
@RequestMapping("/admin")
public class ProductoController {

	@Autowired
	private ProductoService productoService;

	@Autowired
	private PedidoRepositorio pedidoRepositorio;

	@GetMapping("/dashboard")
	public String enrutadorDashboard() {
		
		return "admin/dashboard";
		
	}

	@GetMapping("/controlPedidos")
	public String controlPedidos(Model model) {
		List<Pedido> pedidos = pedidoRepositorio.findAll();
		double totalVentas = pedidos.stream().mapToDouble(Pedido::getTotal).sum();
		model.addAttribute("listaPedidos", pedidos);
		model.addAttribute("totalVentas", totalVentas);
		return "admin/controlPedidos";
	}

	@GetMapping("/productosCrud")
	public String listadoProductos(Model model) {

		List<Producto> productos;
		
		productos=productoService.obtenerTodosProductos();
		model.addAttribute("listaProductos", productos);

		return "admin/productosCrud";

	}

	@GetMapping({ "/listSort" })
	public String listadoProductosOrdenadoMax(Model model) {

		List<Producto> listaOrdenada = productoService.findAll();
		// metodo de la api para ordenar los precios de mayor a menor usando un comparador.
		listaOrdenada.sort(Comparator.comparing(Producto::getPrecio).reversed());

		model.addAttribute("listaProductos", listaOrdenada);

		return "index";

	}


	// Metodos CRUD para la gestion de los productos
	@GetMapping("/nuevoProducto")
	public String mosrtrarFormularioProducto(Model model) {

		model.addAttribute("producto", new Producto());

		return "admin/productform";

	}

	@PostMapping("/nuevo/submit")
	public String procesaFormularioProducto(@Valid @ModelAttribute("producto") Producto producto, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "admin/productform";
		}

		productoService.save(producto);

		return "redirect:/admin/productosCrud";

	}
	
	@GetMapping("/editar/{id}")
	public String editarProductoFormulario(@PathVariable("id") Long id, Model model) {

		Optional<Producto> productoEditar = productoService.findById(id);
	
		if (productoEditar.isPresent()) {
			model.addAttribute("producto", productoEditar.get());
			return "admin/productform";
		} else {
			return "redirect:/admin/productosCrud";
		}

	}

	@GetMapping("/borrar/{id}")
	public String borrarProducto(@PathVariable("id") Long id) {

		productoService.desactivar(id);
		
		return "redirect:/admin/productosCrud";

	}

}
