document.addEventListener('DOMContentLoaded', function () {
    var modalEl = document.querySelector('#modalConfirmarBorrado');
    if (!modalEl) return;

    var modal = new bootstrap.Modal(modalEl);
    var btnConfirmar = document.querySelector('#btnConfirmarBorrado');
    var nombreProductoEl = document.querySelector('#modalNombreProducto');
    var urlDestino = '';

    document.querySelectorAll('.btn-borrar-producto').forEach(function (btn) {
        btn.addEventListener('click', function (e) {
            e.preventDefault();
            urlDestino = btn.getAttribute('data-url');
            var nombre = btn.getAttribute('data-nombre');
            if (nombreProductoEl) nombreProductoEl.textContent = nombre;
            modal.show();
        });
    });

    if (btnConfirmar) {
        btnConfirmar.addEventListener('click', function () {
            if (urlDestino) {
                window.location.href = urlDestino;
            }
        });
    }
});
