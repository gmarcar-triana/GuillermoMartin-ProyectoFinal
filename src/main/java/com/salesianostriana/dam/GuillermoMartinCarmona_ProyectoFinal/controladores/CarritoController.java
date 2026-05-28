package com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.controladores;

import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.modelo.Producto;
import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.services.CarritoService;
import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.services.ProductoService;

public class CarritoController {

	@Autowired
	private CarritoService carritoService;
	
	@Autowired
	private ProductoService productoService;

	public CarritoController(CarritoService carritoService, ProductoService productoService) {
		super();
		this.carritoService = carritoService;
		this.productoService = productoService;
	}
	
	@GetMapping ("/carrito")
	public String mostrarCarrito (Model model) {
		return "carrito";
	}
	
	@GetMapping("/agregarProducto/{id}")
    public String productoACarrito(@PathVariable("id") Long id, Model model) {
        
        Producto producto = productoService.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Producto "+id+" no encontrado"));
        
        carritoService.addProducto(producto);
        	    		 	
        return "redirect:/";
    }
	
    @GetMapping("/borrarProducto/{id}")
    public String removeProductFromCartV1(@PathVariable("id") Long id) {
    	carritoService.removeProductoPorId(id);
        return "redirect:/carrito";
    }
    
    @ModelAttribute("totalCarrito")
    public Double totalCarrito () {
    	
    	Map <Producto,Integer> carrito=carritoService.getProductsInCart();
    	double total=0.0;
    	if (carrito !=null) {
        	for (Producto p: carrito.keySet()) {
        		total+=p.getPrecio()*carrito.get(p);
        	}
        	return total;
    	}   	
    	return 0.0;
    }

    @GetMapping("/carrito/tramitar")
    public String tramitarPedido(Model model) {
        Map<Producto, Integer> carrito = carritoService.getProductsInCart();
        
        if (carrito == null || carrito.isEmpty()) {
            return "redirect:/";
        }
        
        model.addAttribute("products", carrito);
        
        
        return "ticket";
    }

    @GetMapping("/carrito/vaciar-y-nueva-compra")
    public String vaciarYNuevaCompra() {
        java.util.Map<Producto, Integer> carrito = carritoService.getProductsInCart();
        
        if (carrito != null) {
            carritoService.limpiarCarrito();
        }
        
        return "redirect:/";
    }

    
    
    
    
	
	
	
}
