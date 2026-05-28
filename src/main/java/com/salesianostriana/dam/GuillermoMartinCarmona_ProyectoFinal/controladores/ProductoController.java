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

import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.modelo.Producto;
import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.services.ProductoService;

@Controller
@RequestMapping("/admin")
public class ProductoController {

	@Autowired
	private ProductoService productoService;

	@GetMapping("/dashboard")
	public String enrutadorDashboard() {
		
		return "/admin/dashboard";
		
	}
	
	@GetMapping("/productosCrud")
	public String listadoProductos(Model model) {

		List<Producto> productos;
		
		productos=productoService.obtenerTodosProductos();
		model.addAttribute("listaProductos", productos);

		return "/admin/productoscrud";

	}

	@GetMapping({ "/listSort" })
	public String listadoProductosOrdenadoMax(Model model) {

		List<Producto> listaOrdenada = productoService.findAll();
		// metodo de la api para ordenar los precios de mayor a menor usando un comparador.
		listaOrdenada.sort(Comparator.comparing(Producto::getPrecio).reversed());

		model.addAttribute("listaProductos", listaOrdenada);

		return "index";

	}

	@GetMapping("/nuevoProducto")
	public String mosrtrarFormularioProducto(Model model) {

		model.addAttribute("producto", new Producto());

		return "/admin/productform";

	}

	@PostMapping("/nuevo/submit")
	public String procesaFormularioProducto(@ModelAttribute("producto") Producto producto) {

		productoService.save(producto);

		return "redirect:/";

	}

	@GetMapping({ "/producto" })
	public String showFormProducto(Model model) {

		Producto producto = new Producto();
		model.addAttribute("productoForm", producto);

		return "formProducto";

	}

	// Metodos CRUD para la gestion de los productos

	@PostMapping({ "/addProducto" })
	public String formularioAgregarProducto(@ModelAttribute("productoForm") Producto producto, Model model) {

		model.addAttribute("producto", producto);

		return "agregar";

	}

	@GetMapping("/editar/{id}")
	public String editarProductoFormulario(@PathVariable("id") Long id, Model model) {

		Optional<Producto> productoEditar = productoService.findById(id);

		if (productoEditar.isPresent()) {
			model.addAttribute("producto", productoEditar);
			return "/admin/productFormEdit";
		} else {
			return "redirect:/admin/productoscrud";
		}

	}

	@PostMapping("/editar/submit")
	public String procesarFormularioEdicionProducto(@ModelAttribute("producto") Producto p) {

		productoService.edit(p);

		return "redirect:/admin/productosCrud";

	}

	@GetMapping("/borrar/{id}")
	public String borrarProducto(@PathVariable("id") Long id) {

		Optional<Producto> aBorrar = productoService.findById(id);
		if (aBorrar.isPresent()) {
			productoService.delete(aBorrar.get());
		}
		
		return "redirect:/admin/productosCrud";

	}

}
