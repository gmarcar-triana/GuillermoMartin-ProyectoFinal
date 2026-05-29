package com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.services;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.modelo.Producto;
import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.repositorios.ProductoRepositorio;
import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.services.base.BaseServicempl;

@Service
@Scope (value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CarritoService extends BaseServicempl<Producto, Long, ProductoRepositorio>{

	private Map<Producto, Integer> products = new HashMap <> ();
	
	public void addProducto (Producto p) {
		if (products.containsKey(p)) {
			products.replace(p, products.get(p)+1);
		}else {
			products.put(p, 1);
		}
	}
	
	public void removeProducto (Producto p) {
		if (products.containsKey(p)) {
			if (products.get(p) > 1)
				products.replace(p, products.get(p) - 1);
			else if (products.get(p) == 1) {
				products.remove(p);
			}	
		}
	}
	
	public void removeProductoPorId(Long id) {
	    if (this.products == null || id == null) {
	        return;
	    }

	    this.products.keySet().stream()
	            .filter(producto -> producto.getCodigoProducto().equals(id))
	            .findFirst()
	            .ifPresent(producto -> this.products.remove(producto));
	}
	
	
    public Map<Producto, Integer> getProductsInCart() {
        return Collections.unmodifiableMap(products);
    }
	
    public double calcularTotalCarrito() {
        if (products == null) {
            return 0.0;
        }

        return products.entrySet()
                .stream()
                .mapToDouble(entry -> entry.getKey().getPrecio()*entry.getValue())
                .sum();
    }
	
    public void limpiarCarrito() {
    	if(this.products != null) {
    		this.products.clear();
    	}
    }
	
	
	
	
}
