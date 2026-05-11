package services;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import entities.Producto;

@Service
public class ProductoService {

	public List<Producto> getList() {
		return Arrays.asList(
				new Producto("cod-101", "Agua", 1.20, "Botella de agua mineralizada", true, "Bebidas", LocalDate.of(2026, 6, 3)),
				new Producto("cod-102", "Pan", 0.95, "Barra de pan recién horneada", true, "Panadería", LocalDate.of(2026, 6, 3)),
				new Producto("cod-103", "Leche", 1.10, "Leche entera pasteurizada 1L", true, "Lácteos", LocalDate.of(2026, 6, 3)),
				new Producto("cod-104", "Huevos", 2.50, "Docena de huevos camperos", true, "Huevos y derivados", LocalDate.of(2026, 6, 3)),
				new Producto("cod-105", "Café", 3.80, "Paquete de café molido 250g", true, "Despensa", LocalDate.of(2026, 6, 3))
				);
	}
	
	
	
}
