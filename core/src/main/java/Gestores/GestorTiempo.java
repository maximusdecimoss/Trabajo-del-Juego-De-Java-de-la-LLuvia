package Gestores;

/**
 * Implementa el Patrón Singleton (GM2.1) para gestionar el tiempo y la velocidad
 * de todos los objetos en el juego.
 */
public class GestorTiempo {

    // 1. INSTANCIA ÚNICA (Singleton)
    private static final GestorTiempo INSTANCIA = new GestorTiempo();

    // ATRIBUTOS
    // Se usa 'Global' para coincidir con el método que usan las otras clases.
    private float factorVelocidadGlobal = 1.0f;
    private float tiempoRestanteEfecto = 0.0f;

    // 2. CONSTRUCTOR PRIVADO
    private GestorTiempo() {
        // Inicialización simple
    }

    // 3. ACCESO PÚBLICO A LA INSTANCIA (Punto de acceso global)
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
    /**
     * Devuelve el factor de velocidad actual que aplica a todos los objetos.
     */
    public float getFactorVelocidadGlobal() { // <--- NOMBRE CORREGIDO
        return factorVelocidadGlobal;
    }

    public void aplicarEfectoTemporal(float factor, float duracion) {
        this.factorVelocidadGlobal = factor;
        this.tiempoRestanteEfecto = duracion;
    }
}
