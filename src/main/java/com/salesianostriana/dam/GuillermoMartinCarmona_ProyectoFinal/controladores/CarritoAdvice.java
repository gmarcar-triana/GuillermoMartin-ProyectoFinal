package com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.controladores;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.modelo.Producto;
import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.services.CarritoService;

/**
 * ControllerAdvice global: inyecta los datos del carrito en el modelo
 * de TODOS los controladores de la aplicación.
 * Así el fragment general.html (navbar + offcanvas) siempre tiene los
 * datos del carrito disponibles, independientemente de qué controlador
 * sirva la página.
 */
@ControllerAdvice
public class CarritoAdvice {

    @Autowired
    private CarritoService carritoService;

    /**
     * Mapa Producto → cantidad para listar items en el offcanvas del carrito.
     */
    @ModelAttribute("carritoItems")
    public Map<Producto, Integer> carritoItems() {
        return carritoService.getProductsInCart();
    }

    /**
     * Total del carrito con descuentos aplicados.
     */
    @ModelAttribute("totalCarrito")
    public Double totalCarrito() {
        Map<Producto, Integer> carrito = carritoService.getProductsInCart();
        if (carrito != null) {
            return carrito.entrySet().stream()
                    .mapToDouble(e -> e.getKey().getPrecio() * (1 - e.getKey().getDescuento()) * e.getValue())
                    .sum();
        }
        return 0.0;
    }

    /**
     * Número total de unidades en el carrito (para el badge del icono).
     */
    @ModelAttribute("cantidadCarrito")
    public int cantidadCarrito() {
        Map<Producto, Integer> carrito = carritoService.getProductsInCart();
        if (carrito != null) {
            return carrito.values().stream().mapToInt(Integer::intValue).sum();
        }
        return 0;
    }
}
