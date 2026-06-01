package com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.services;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.modelo.LineaPedido;
import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.modelo.Pedido;
import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.modelo.Producto;
import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.repositorios.PedidoRepositorio;

@Service
public class VentasService {

    @Autowired
    private PedidoRepositorio pedidoRepositorio;

    private double precioEnvio = 4.99;

    public double getPrecioEnvio() {
        return precioEnvio;
    }

    public void setPrecioEnvio(double precioEnvio) {
        this.precioEnvio = precioEnvio;
    }

    public double getFacturacionTotal(List<Pedido> pedidos) {
        return pedidos.stream()
                .mapToDouble(Pedido::getTotal)
                .sum();
    }

    public long getNumeroPedidosTotales(List<Pedido> pedidos) {
        return pedidos.size();
    }

    public double getTicketMedio(List<Pedido> pedidos) {
        long totalPedidos = getNumeroPedidosTotales(pedidos);
        if (totalPedidos == 0) return 0.0;
        return getFacturacionTotal(pedidos) / totalPedidos;
    }

    public double getGastosEnvioFacturados(List<Pedido> pedidos) {
        return pedidos.stream()
                .mapToDouble(p -> {
                    double subtotal = p.getLineasVenta().stream()
                            .mapToDouble(LineaPedido::getSubtotal)
                            .sum();
                    return subtotal <= 100.0 ? precioEnvio : 0.0;
                })
                .sum();
    }

    public List<ProductoVendidoDto> getProductosMasVendidos(List<Pedido> pedidos, int limit) {
        List<LineaPedido> todasLineas = pedidos.stream()
                .flatMap(p -> p.getLineasVenta().stream())
                .collect(Collectors.toList());

        Map<Producto, Integer> cantidadesPorProducto = todasLineas.stream()
                .collect(Collectors.groupingBy(
                        LineaPedido::getProducto,
                        Collectors.summingInt(LineaPedido::getCantidad)
                ));

        return cantidadesPorProducto.entrySet().stream()
                .map(entry -> new ProductoVendidoDto(
                        entry.getKey().getNombre(),
                        entry.getKey().getCategoria(),
                        entry.getValue(),
                        entry.getValue() * entry.getKey().getPrecioFinal()
                ))
                .sorted((a, b) -> Integer.compare(b.getUnidadesVendidas(), a.getUnidadesVendidas()))
                .limit(limit)
                .collect(Collectors.toList());
    }

    public List<CategoriaVentasDto> getVentasPorCategoria(List<Pedido> pedidos) {
        List<LineaPedido> todasLineas = pedidos.stream()
                .flatMap(p -> p.getLineasVenta().stream())
                .collect(Collectors.toList());

        Map<String, Double> facturacionPorCategoria = todasLineas.stream()
                .collect(Collectors.groupingBy(
                        l -> l.getProducto().getCategoria(),
                        Collectors.summingDouble(LineaPedido::getSubtotal)
                ));

        double totalFacturado = facturacionPorCategoria.values().stream().mapToDouble(Double::doubleValue).sum();

        return facturacionPorCategoria.entrySet().stream()
                .map(entry -> {
                    double porcentaje = totalFacturado > 0 ? (entry.getValue() / totalFacturado) * 100 : 0.0;
                    return new CategoriaVentasDto(entry.getKey(), entry.getValue(), porcentaje);
                })
                .sorted((a, b) -> Double.compare(b.getTotalFacturado(), a.getTotalFacturado()))
                .collect(Collectors.toList());
    }

    public List<ClienteGastoDto> getClientesMayorGasto(List<Pedido> pedidos, int limit) {
        Map<String, List<Pedido>> pedidosPorCliente = pedidos.stream()
                .collect(Collectors.groupingBy(p -> {
                    if (p.getCliente() != null) {
                        return p.getCliente().getEmail();
                    } else {
                        return "anonimo@tienda.com";
                    }
                }));

        return pedidosPorCliente.entrySet().stream()
                .map(entry -> {
                    String email = entry.getKey();
                    String nombre = "Cliente Invitado";
                    if (!email.equals("anonimo@tienda.com")) {
                        Pedido primerPedido = entry.getValue().get(0);
                        if (primerPedido.getCliente() != null) {
                            nombre = primerPedido.getCliente().getNombre() + " " + primerPedido.getCliente().getApellidos();
                        }
                    }
                    double totalGasto = entry.getValue().stream().mapToDouble(Pedido::getTotal).sum();
                    int totalPedidos = entry.getValue().size();
                    return new ClienteGastoDto(nombre, email, totalGasto, totalPedidos);
                })
                .sorted((a, b) -> Double.compare(b.getTotalGasto(), a.getTotalGasto()))
                .limit(limit)
                .collect(Collectors.toList());
    }

    public static class ProductoVendidoDto {
        private String nombre;
        private String categoria;
        private int unidadesVendidas;
        private double totalRecaudado;

        public ProductoVendidoDto(String nombre, String categoria, int unidadesVendidas, double totalRecaudado) {
            this.nombre = nombre;
            this.categoria = categoria;
            this.unidadesVendidas = unidadesVendidas;
            this.totalRecaudado = totalRecaudado;
        }

        public String getNombre() { return nombre; }
        public String getCategoria() { return categoria; }
        public int getUnidadesVendidas() { return unidadesVendidas; }
        public double getTotalRecaudado() { return totalRecaudado; }
    }

    public static class CategoriaVentasDto {
        private String nombre;
        private double totalFacturado;
        private double porcentaje;

        public CategoriaVentasDto(String nombre, double totalFacturado, double porcentaje) {
            this.nombre = nombre;
            this.totalFacturado = totalFacturado;
            this.porcentaje = porcentaje;
        }

        public String getNombre() { return nombre; }
        public double getTotalFacturado() { return totalFacturado; }
        public double getPorcentaje() { return porcentaje; }
    }

    public static class ClienteGastoDto {
        private String nombre;
        private String email;
        private double totalGasto;
        private int totalPedidos;

        public ClienteGastoDto(String nombre, String email, double totalGasto, int totalPedidos) {
            this.nombre = nombre;
            this.email = email;
            this.totalGasto = totalGasto;
            this.totalPedidos = totalPedidos;
        }

        public String getNombre() { return nombre; }
        public String getEmail() { return email; }
        public double getTotalGasto() { return totalGasto; }
        public int getTotalPedidos() { return totalPedidos; }
    }
}
