package com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.controladores;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.modelo.Producto;
import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.services.CarritoService;
import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.services.VentasService;

@ControllerAdvice
public class CarritoAdvice {

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private VentasService ventasService;

    @ModelAttribute("carritoItems")
    public Map<Producto, Integer> carritoItems() {
    	
        return carritoService.getProductsInCart();
        
    }

    @ModelAttribute("subtotalCarrito")
    public Double subtotalCarrito() {
        Map<Producto, Integer> carrito = carritoService.getProductsInCart();
        if (carrito != null) {
            return carrito.entrySet().stream()
                    .mapToDouble(e -> e.getKey().getPrecio() * (1 - e.getKey().getDescuento()) * e.getValue())
                    .sum();
        }
        return 0.0;
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
}
