package com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.controladores;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.modelo.Pedido;
import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.repositorios.PedidoRepositorio;
import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.services.ProductoService;
import com.salesianostriana.dam.GuillermoMartinCarmona_ProyectoFinal.services.VentasService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AnalisisVentasController {

    @Autowired
    private VentasService ventasService;

    @Autowired
    private PedidoRepositorio pedidoRepositorio;

    @Autowired
    private ProductoService productoService;

    @GetMapping("/admin/analisisVentas")
    public String verAnalisisVentas(
            @RequestParam(value = "fecha", required = false)
            @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE)
            java.time.LocalDate fecha,
            Model model) {
        
        List<Pedido> todosPedidos = pedidoRepositorio.findAll();

        if (fecha != null) {
            todosPedidos = todosPedidos.stream()
                    .filter(p -> p.getFecha() != null && p.getFecha().toLocalDate().equals(fecha))
                    .collect(java.util.stream.Collectors.toList());
            model.addAttribute("fechaSeleccionada", fecha);
        }

        model.addAttribute("facturacionTotal", ventasService.getFacturacionTotal(todosPedidos));
        model.addAttribute("totalPedidos", ventasService.getNumeroPedidosTotales(todosPedidos));
        model.addAttribute("ticketMedio", ventasService.getTicketMedio(todosPedidos));
        model.addAttribute("gastosEnvio", ventasService.getGastosEnvioFacturados(todosPedidos));
        model.addAttribute("precioEnvio", ventasService.getPrecioEnvio());
        model.addAttribute("productosMasVendidos", ventasService.getProductosMasVendidos(todosPedidos, 5));
        model.addAttribute("ventasPorCategoria", ventasService.getVentasPorCategoria(todosPedidos));
        model.addAttribute("clientesMayorGasto", ventasService.getClientesMayorGasto(todosPedidos, 5));
        
        List<Pedido> ultimosPedidos = todosPedidos.stream()
                .sorted((a, b) -> b.getFecha().compareTo(a.getFecha()))
                .limit(5)
                .collect(java.util.stream.Collectors.toList());
        model.addAttribute("ultimosPedidos", ultimosPedidos);
        model.addAttribute("productosSinStock", productoService.obtenerTodosProductos().stream()
                .filter(p -> !p.isStock())
                .collect(java.util.stream.Collectors.toList()));

        return "admin/analisisVentas";
    }

    @PostMapping("/admin/analisisVentas/envio")
    public String cambiarPrecioEnvio(@RequestParam("precio") double precio, RedirectAttributes redirectAttributes) {
        ventasService.setPrecioEnvio(precio);
        redirectAttributes.addFlashAttribute("mensajeExito", "¡Gastos de envío actualizados a " + String.format("%.2f", precio) + " €!");
        return "redirect:/admin/analisisVentas";
    }
}
