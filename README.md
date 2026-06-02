# Gourmet Market - Proyecto Final

Este proyecto es una aplicación web de comercio electrónico especializada en la venta de productos gourmet. Está desarrollada con Spring Boot, Thymeleaf para la visualización del frontend y Spring Security para el control de accesos.

## Funcionalidades principales

### Zona Pública (Clientes)
- **Catálogo de productos**: Visualización de productos organizados por categorías (aceites, quesos, embutidos, vinos, etc.) con sus respectivos precios, descuentos aplicados y disponibilidad de stock.
- **Detalle de producto**: Ficha individual con descripción ampliada y fecha de caducidad.
- **Carrito de compra**: Gestión dinámica de la cesta (añadir, restar cantidad, vaciar o eliminar productos directamente).
- **Cálculo de envío**: Gastos de envío gratuitos para compras superiores a 100€ (coste base de 4,99€ en caso contrario).
- **Tramitación del pedido**: Creación e inserción de pedidos al procesar el carrito (requiere inicio de sesión).

### Zona de Administración (Admin)
- **Panel de control (Dashboard)**: Métricas de negocio como facturación total de la tienda, número de pedidos realizados y ticket medio.
- **Control de pedidos**: Listado completo de los pedidos del sistema con ventanas emergentes (modales) para ver el desglose de productos y datos del cliente.
- **Análisis de ventas**: Estadísticas avanzadas que muestran el reparto de facturación por categorías y los productos más vendidos.

## Tecnologías utilizadas
- **Backend**: Java, Spring Boot, Spring Data JPA, Spring Security.
- **Frontend**: HTML5, Thymeleaf, Bootstrap 5, Bootstrap Icons, JavaScript.
- **Base de datos**: H2 Database (en memoria, autocompletada con datos de prueba mediante `import.sql`).

## Cómo ejecutar la aplicación
1. Importar el proyecto como proyecto Maven existente en tu IDE (Spring Tool Suite / Eclipse).
2. Ejecutar la aplicación ejecutando la clase principal `GuillermoMartinCarmonaProyectoFinalApplication.java` (o mediante el comando `mvnw spring-boot:run`).
3. Acceder en el navegador a: `http://localhost:9000`

## Credenciales de prueba
La base de datos viene precargada con los siguientes perfiles de acceso:

* **Administrador**:
  * **Usuario**: `admin`
  * **Contraseña**: `admin`
* **Cliente**:
  * **Usuario**: `user`
  * **Contraseña**: `user`
