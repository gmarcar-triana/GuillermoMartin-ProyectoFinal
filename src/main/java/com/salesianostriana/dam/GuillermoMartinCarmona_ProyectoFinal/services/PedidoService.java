package com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.services;

import java.time.LocalDateTime;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.modelo.LineaPedido;
import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.modelo.Pedido;
import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.modelo.Producto;
import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.modelo.Usuario;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AnonymousAuthenticationToken;

import org.springframework.beans.factory.annotation.Autowired;
import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.repositorios.PedidoRepositorio;
import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.repositorios.LineaPedidoRepositorio;
import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.repositorios.UsuarioRepositorio;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PedidoService {

    @Autowired
    private PedidoRepositorio pedidoRepositorio;

    @Autowired
    private LineaPedidoRepositorio lineaPedidoRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private VentasService ventasService;

    private Pedido pedidoActual = null;

    public LineaPedido agregarLinea(Producto producto, int cantidad) {

        if (pedidoActual == null) {
            Usuario clienteObj = null;
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
                Object principal = auth.getPrincipal();
                if (principal instanceof Usuario) {
                    clienteObj = (Usuario) principal;
                }
            }

            pedidoActual = Pedido.builder()
                    .fecha(LocalDateTime.now())
                    .total(0.0)
                    .cliente(clienteObj)
                    .lineasVenta(new java.util.ArrayList<>())
                    .build();
        }

        LineaPedido lineaExistente = pedidoActual.getLineasVenta().stream()
                .filter(l -> l.getProducto().getCodigoProducto().equals(producto.getCodigoProducto()))
                .findFirst()
                .orElse(null);

        LineaPedido lineaGuardada;

        if (lineaExistente != null) {
            lineaExistente.setCantidad(lineaExistente.getCantidad() + cantidad);
            lineaGuardada = lineaExistente;
        } else {
            LineaPedido nuevaLinea = LineaPedido.builder()
                    .cantidad(cantidad)
                    .pedido(pedidoActual)
                    .producto(producto)
                    .build();
            pedidoActual.getLineasVenta().add(nuevaLinea);
            lineaGuardada = nuevaLinea;
        }

        double nuevoTotal = pedidoActual.getLineasVenta().stream()
                .mapToDouble(l -> l.getProducto().getPrecio() * (1 - l.getProducto().getDescuento()) * l.getCantidad())
                .sum();

        if (nuevoTotal > 0.0 && nuevoTotal <= 100.0) {
            nuevoTotal += ventasService.getPrecioEnvio();
        }

        pedidoActual.setTotal(nuevoTotal);

        return lineaGuardada;
    }
    
    

    public void restarLinea(Producto producto, int cantidad) {
        if (pedidoActual == null) return;

        LineaPedido lineaExistente = pedidoActual.getLineasVenta().stream()
                .filter(l -> l.getProducto().getCodigoProducto().equals(producto.getCodigoProducto()))
                .findFirst()
                .orElse(null);

        if (lineaExistente != null) {
            if (lineaExistente.getCantidad() > cantidad) {
                lineaExistente.setCantidad(lineaExistente.getCantidad() - cantidad);
            } else {
                pedidoActual.getLineasVenta().remove(lineaExistente);
            }
            
            double nuevoTotal = pedidoActual.getLineasVenta().stream()
                    .mapToDouble(l -> l.getProducto().getPrecio() * (1 - l.getProducto().getDescuento()) * l.getCantidad())
                    .sum();
            
            if (nuevoTotal > 0.0 && nuevoTotal <= 100.0) {
                nuevoTotal += ventasService.getPrecioEnvio();
            }
            
            pedidoActual.setTotal(nuevoTotal);
        }
    }

    public void cerrarPedido() {
        pedidoActual = null;
    }

    public void guardarPedido() {
        if (pedidoActual == null || pedidoActual.getLineasVenta().isEmpty()) {
            return;
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            String username = auth.getName();
            usuarioRepositorio.findByUsername(username).ifPresent(u -> {
                pedidoActual.setCliente(u);
            });
        }

        pedidoActual.setFecha(LocalDateTime.now());

        Pedido pedidoGuardado = pedidoRepositorio.save(pedidoActual);

        for (LineaPedido linea : pedidoActual.getLineasVenta()) {
            linea.setPedido(pedidoGuardado);
            lineaPedidoRepositorio.save(linea);
        }

        pedidoActual = null;
    }

    public Pedido getPedidoActual() {
        return pedidoActual;
    }
}
