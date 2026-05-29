document.addEventListener('DOMContentLoaded', function () {
    var offcanvasEl = document.querySelector('#offcanvasCarrito');

    if (!offcanvasEl) return;

    var abrirCarrito = offcanvasEl.getAttribute('data-abrir-carrito') === 'true';

    if (abrirCarrito && typeof bootstrap !== 'undefined') {
        var bsOffcanvas = new bootstrap.Offcanvas(offcanvasEl);
        bsOffcanvas.show();
    }
});
