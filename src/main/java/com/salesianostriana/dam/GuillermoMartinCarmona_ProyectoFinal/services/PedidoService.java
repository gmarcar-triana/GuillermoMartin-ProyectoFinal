package com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.modelo.LineaPedido;
import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.modelo.Pedido;
import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.modelo.Producto;
import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.repositorios.LineaPedidoRepositorio;
import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.repositorios.PedidoRepositorio;

/**
 * Servicio de ámbito de sesión que gestiona el Pedido en curso.
 * Un único Pedido por sesión acumula LineaPedidos. Al tramitar el pedido
 * se debe llamar a cerrarPedido() para resetear el estado.
 */
@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PedidoService {

    @Autowired
    private PedidoRepositorio pedidoRepositorio;

    @Autowired
    private LineaPedidoRepositorio lineaPedidoRepositorio;

    // Pedido en curso durante esta sesión
    private Pedido pedidoActual = null;

    /**
     * Añade o actualiza una LineaPedido en el Pedido de la sesión actual.
     * Si no existe Pedido, lo crea. Si ya hay una línea para ese producto,
     * actualiza la cantidad. Si no, crea una nueva línea.
     *
     * @param producto  Producto a añadir
     * @param cantidad  Cantidad a añadir (normalmente 1)
     * @return La LineaPedido creada o actualizada
     */
    public LineaPedido agregarLinea(Producto producto, int cantidad) {

        // 1. Asegurar que existe un Pedido en curso
        if (pedidoActual == null) {
            pedidoActual = Pedido.builder()
                    .fecha(LocalDateTime.now())
                    .total(0.0)
                    .cliente(null) // sin usuario por ahora
                    .build();
            pedidoActual = pedidoRepositorio.save(pedidoActual);
        }

        // 2. Buscar si ya existe una línea para este producto en el pedido actual
        LineaPedido lineaExistente = pedidoActual.getLineasVenta().stream()
                .filter(l -> l.getProducto().getCodigoProducto().equals(producto.getCodigoProducto()))
                .findFirst()
                .orElse(null);

        LineaPedido lineaGuardada;

        if (lineaExistente != null) {
            // 3a. Actualizar cantidad en la línea existente
            LineaPedido lineaActualizada = LineaPedido.builder()
                    .id(lineaExistente.getId())
                    .cantidad(lineaExistente.getCantidad() + cantidad)
                    .pedido(pedidoActual)
                    .producto(producto)
                    .build();
            lineaGuardada = lineaPedidoRepositorio.save(lineaActualizada);

            // Actualizar la lista en memoria
            pedidoActual.getLineasVenta().remove(lineaExistente);
            pedidoActual.getLineasVenta().add(lineaGuardada);
        } else {
            // 3b. Crear nueva línea
            LineaPedido nuevaLinea = LineaPedido.builder()
                    .cantidad(cantidad)
                    .pedido(pedidoActual)
                    .producto(producto)
                    .build();
            lineaGuardada = lineaPedidoRepositorio.save(nuevaLinea);
            pedidoActual.getLineasVenta().add(lineaGuardada);
        }

        // 4. Recalcular total del pedido y guardarlo
        double nuevoTotal = pedidoActual.getLineasVenta().stream()
                .mapToDouble(l -> l.getProducto().getPrecio() * (1 - l.getProducto().getDescuento()) * l.getCantidad())
                .sum();

        Pedido pedidoActualizado = Pedido.builder()
                .codigoPedido(pedidoActual.getCodigoPedido())
                .fecha(pedidoActual.getFecha())
                .total(nuevoTotal)
                .cliente(pedidoActual.getCliente())
                .lineasVenta(pedidoActual.getLineasVenta())
                .build();
        pedidoActual = pedidoRepositorio.save(pedidoActualizado);

        return lineaGuardada;
    }

    /**
     * Cierra el pedido actual (al tramitarlo). La próxima llamada a agregarLinea
     * creará un nuevo pedido.
     */
    public void cerrarPedido() {
        pedidoActual = null;
    }

    /**
     * Devuelve el pedido en curso o null si no hay ninguno.
     */
    public Pedido getPedidoActual() {
        return pedidoActual;
    }
}
