package com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.controladores;

import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.modelo.Producto;
import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.services.CarritoService;
import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.services.PedidoService;
import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.services.ProductoService;

@Controller
public class CarritoController {

	@Autowired
	private CarritoService carritoService;

	@Autowired
	private ProductoService productoService;

	@Autowired
	private PedidoService pedidoService;

	public CarritoController(CarritoService carritoService, ProductoService productoService,
			PedidoService pedidoService) {
		super();
		this.carritoService = carritoService;
		this.productoService = productoService;
		this.pedidoService = pedidoService;
	}

	
	@GetMapping("/carrito")
	public String mostrarCarrito(Model model) {
		
		return "carrito";
		
	}
	
	@ModelAttribute("carritoItems")
	public Map<Producto, Integer> carritoItems() {
		
		return carritoService.getProductsInCart();
		
	}

	@ModelAttribute("totalCarrito")
	public Double totalCarrito() {
		
		Map<Producto, Integer> carrito = carritoService.getProductsInCart();
		double total = 0.0;
		if (carrito != null) {
			for (Producto p : carrito.keySet()) {
				total += p.getPrecio() * (1 - p.getDescuento()) * carrito.get(p);
			}
			return total;
		}
		return 0.0;
		
	}

	@ModelAttribute("cantidadCarrito")
	public int cantidadCarrito() {
		
		Map<Producto, Integer> carrito = carritoService.getProductsInCart();
		if (carrito != null) {
			return carrito.values().stream().mapToInt(Integer::intValue).sum();
		}
		return 0;
		
	}

	@GetMapping("/agregarProducto/{id}")
	public String productoACarrito(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {

		Producto producto = productoService.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Producto " + id + " no encontrado"));

		carritoService.addProducto(producto);
		pedidoService.agregarLinea(producto, 1);
		redirectAttributes.addFlashAttribute("abrirCarrito", true);

		return "redirect:/";
	}

	@GetMapping("/borrarProducto/{id}")
	public String removeProductFromCartV1(@PathVariable("id") Long id) {
		
		carritoService.removeProductoPorId(id);
		return "redirect:/";
		
	}

	@GetMapping("/carrito/vaciar")
	public String vaciarYNuevaCompra() {
		
		Map<Producto, Integer> carrito = carritoService.getProductsInCart();

		if (carrito != null) {
			carritoService.limpiarCarrito();
			pedidoService.cerrarPedido();
		}

		return "redirect:/";
	}

}
