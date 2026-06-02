document.addEventListener("DOMContentLoaded", function() {
    const numericInputs = [document.getElementById("precio"), document.getElementById("descuento")];
    
    numericInputs.forEach(function(input) {
        if (!input) return;
        
        input.addEventListener("input", function() {
            // Permite solo números y un punto decimal
            let value = this.value.replace(/[^0-9.]/g, '');
            
            // Controla que no haya más de un punto decimal
            const parts = value.split('.');
            if (parts.length > 2) {
                value = parts[0] + '.' + parts.slice(1).join('');
            }
            
            this.value = value;
        });
        
        input.addEventListener("keydown", function(e) {
            // Evita caracteres no numéricos habituales en inputs de tipo número (e, E, +, -, etc.)
            const invalidKeys = ["e", "E", "+", "-", "i", "I", "n", "f"];
            if (invalidKeys.includes(e.key)) {
                e.preventDefault();
            }
        });
    });
});
