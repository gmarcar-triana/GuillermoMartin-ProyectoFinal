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

import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.modelo.Pedido;
import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.modelo.Producto;
import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.services.CarritoService;
import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.services.PedidoService;
import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.services.ProductoService;
import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.services.VentasService;
import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.exception.StockInsuficienteException;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CarritoController {

	@Autowired
	private CarritoService carritoService;

	@Autowired
	private ProductoService productoService;

	@Autowired
	private PedidoService pedidoService;

	@Autowired
	private VentasService ventasService;

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

	@ModelAttribute("subtotalCarrito")
	public Double subtotalCarrito() {
		Map<Producto, Integer> carrito = carritoService.getProductsInCart();
		double total = 0.0;
		if (carrito != null) {
			for (Producto p : carrito.keySet()) {
				total += p.getPrecio() * (1 - p.getDescuento()) * carrito.get(p);
			}
		}
		return total;
	}

	@ModelAttribute("gastosEnvio")
	public Double gastosEnvio() {
		Double subtotal = subtotalCarrito();
		if (subtotal > 0.0 && subtotal <= 100.0) {
			return ventasService.getPrecioEnvio();
		}
		return 0.0;
	}

	@ModelAttribute("totalCarrito")
	public Double totalCarrito() {
		Double subtotal = subtotalCarrito();
		if (subtotal > 0.0) {
			return subtotal + gastosEnvio();
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
	public String productoACarrito(@PathVariable("id") Long id, 
            HttpServletRequest request,
            RedirectAttributes redirectAttributes) {
		
		String referer = request.getHeader("Referer");
		try {
			Producto producto = productoService.findById(id)
					.orElseThrow(() -> new NoSuchElementException("Producto " + id + " no encontrado"));
			if (!producto.isStock()) {
				throw new StockInsuficienteException("El producto " + producto.getNombre() + " no tiene stock disponible");
			}
			carritoService.addProducto(producto);
			pedidoService.agregarLinea(producto, 1);
			redirectAttributes.addFlashAttribute("abrirCarrito", true);
		} catch (StockInsuficienteException e) {
			redirectAttributes.addFlashAttribute("errorStock", e.getMessage());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	
		return "redirect:" + (referer != null ? referer : "/");
		
	}

	@GetMapping("/borrarProducto/{id}")
	public String removeProductFromCartV1(@PathVariable("id") Long id, HttpServletRequest request) {
		carritoService.removeProductoPorId(id);
		Producto producto = productoService.findById(id).orElse(null);
		if (producto != null) {
			pedidoService.restarLinea(producto, Integer.MAX_VALUE);
		}
		String referer = request.getHeader("Referer");
		return "redirect:" + (referer != null ? referer : "/");
	}

	@GetMapping("/quitarProducto/{id}")
	public String quitarProducto(@PathVariable("id") Long id, HttpServletRequest request) {
		Producto producto = productoService.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Producto " + id + " no encontrado"));
		carritoService.removeProducto(producto);
		pedidoService.restarLinea(producto, 1);
		String referer = request.getHeader("Referer");
		return "redirect:" + (referer != null ? referer : "/");
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
	
	@GetMapping("/carrito/tramitar")
	public String tramitarCarritoDeCompra(RedirectAttributes redirectAttributes) {
		
		Pedido pedido = pedidoService.getPedidoActual();

		if (pedido != null && !pedido.getLineasVenta().isEmpty()) {
			pedidoService.guardarPedido();
			carritoService.limpiarCarrito();
			redirectAttributes.addFlashAttribute("mensajeExito", "¡Pedido realizado con éxito!");
		} else {
			redirectAttributes.addFlashAttribute("errorStock", "No hay productos en el carrito para tramitar.");
		}

		return "redirect:/";
	}
	
	

}
