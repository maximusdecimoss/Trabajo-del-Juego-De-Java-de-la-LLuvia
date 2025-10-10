// Archivo: GestorTiempo.java
package puppy.code;

public class GestorTiempo {

    // 1. Instancia Única y Privada (Singleton)
    private static final GestorTiempo INSTANCIA = new GestorTiempo();

    // ATRIBUTOS
    private float factorVelocidadGlobal = 1.0f; // 1.0 = velocidad normal
    private float tiempoRestanteEfecto = 0.0f;  // Contador de duración de efectos

    // 2. Constructor Privado: No se puede crear con 'new' desde fuera.
    private GestorTiempo() {
        // Nada aquí
    }

    // 3. Método de Acceso Global: Única forma de obtener la instancia.
    public static GestorTiempo getInstancia() {
        return INSTANCIA;
    }

    // LÓGICA: Actualiza el tiempo y revierte el efecto si es necesario.
    public void actualizar(float delta) {
        if (this.tiempoRestanteEfecto > 0) {
            this.tiempoRestanteEfecto -= delta;

            if (this.tiempoRestanteEfecto <= 0) {
                this.factorVelocidadGlobal = 1.0f; // Vuelve a la velocidad normal
                this.tiempoRestanteEfecto = 0;
            }
        }
    }

    // MÉTODOS PÚBLICOS
    public float getFactorVelocidadGlobal() {
        return factorVelocidadGlobal;
    }

    public void aplicarEfectoTemporal(float factor, float duracion) {
        this.factorVelocidadGlobal = factor;
        this.tiempoRestanteEfecto = duracion;
    }
}
