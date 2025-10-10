// Archivo: ContenedorPociones.java
package puppy.code;

/**
 * Clase que contiene y gestiona la colección de pociones de vida (Suministros).
 * Implementa IContenedorSuministros para devolver un objeto Iterator (GM2.2).
 */
public class ContenedorPociones implements IContenedorSuministros {

    // El suministro del Vikingo: 3 pociones (true = poción disponible)
    private final boolean[] pociones = {true, true, true};
    private final int MAX_POCIONES = 3;

    public int getCantidadMaxima() {
        return MAX_POCIONES;
    }

    /**
     * Devuelve la implementación específica de IIteradorSuministros.
     */
    @Override
    public IIteradorSuministros crearIterador() {
        // Devuelve una nueva instancia del iterador interno
        return new IteradorPociones();
    }

    /**
     * Clase interna privada que implementa la lógica del iterador.
     * Es la responsable de la navegación.
     */
    private class IteradorPociones implements IIteradorSuministros {

        private int indiceActual = 0; // Inicia en la primera poción

        @Override
        public boolean tieneSiguiente() {
            // Revisa si el índice está dentro de los límites del array de pociones
            return indiceActual < MAX_POCIONES;
        }

        @Override
        public Object siguiente() {
            // Solo avanza y consume si hay un siguiente elemento
            if (this.tieneSiguiente()) {

                // 1. Obtener el valor actual (true o false)
                boolean pocionDisponible = pociones[indiceActual];

                // 2. Marcar la poción como USADA (la lógica de "consumo" se hace aquí)
                if (pocionDisponible) {
                    pociones[indiceActual] = false;
                }

                // 3. Avanzar al siguiente índice (la "tarjeta de visita" se mueve)
                indiceActual++;

                // 4. Devolver un marcador de la acción
                return pocionDisponible; // Devuelve TRUE si se consumió, FALSE si ya estaba usada
            }
            return null; // Si no hay más, devuelve nulo.
        }
    }
}
