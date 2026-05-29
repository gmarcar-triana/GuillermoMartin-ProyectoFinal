document.addEventListener('DOMContentLoaded', function () {
    var buscador = document.getElementById('buscador');
    var filas = document.querySelectorAll('.fila-producto');

    if (!buscador || filas.length === 0) return;

    buscador.addEventListener('input', function () {
        var termino = buscador.value.trim().toLocaleLowerCase();

        filas.forEach(function (fila) {
            var texto = fila.textContent.toLocaleLowerCase();
            if (termino === '' || texto.includes(termino)) {
                fila.style.display = '';
            } else {
                fila.style.display = 'none';
            }
        });

        var sinResultados = document.getElementById('sinResultados');
        if (sinResultados) {
            var visibles = Array.from(filas).some(function (f) { return f.style.display !== 'none'; });
            sinResultados.style.display = visibles ? 'none' : '';
        }
    });
});
