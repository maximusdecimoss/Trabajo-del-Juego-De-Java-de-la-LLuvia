// Archivo: ContenedorPociones.java
package puppy.code;

import interfaces.IContenedorSuministros;
import interfaces.IIteradorSuministros;

/**
 * Contenedor de las pociones de vida del Vikingo.
 * Implementa IContenedorSuministros y define la lógica de la navegación interna.
 */
public class ContenedorPociones implements IContenedorSuministros {

    // El suministro del Vikingo: 3 pociones disponibles (true = disponible)
    private final boolean[] pociones = {true, true, true};
    private final int MAX_POCIONES = 3;

    // Constructor vacío (no necesario, pero buena práctica)
    public ContenedorPociones() {
        // Inicialización
    }

    /**
     * Devuelve la implementación específica de IIteradorSuministros (el iterador real).
     */
    @Override
    public IIteradorSuministros crearIterador() {
        return new IteradorPociones();
    }

    /**
     * Clase interna que implementa la lógica del iterador (GM2.2).
     */
    private class IteradorPociones implements IIteradorSuministros {

        private int indiceActual = 0; // Índice de la próxima poción a verificar

        @Override
        public boolean tieneSiguiente() {
            // Verifica si el índice aún está dentro de los límites del array
            return indiceActual < MAX_POCIONES;
        }

        @Override
        public Object siguiente() {
            if (this.tieneSiguiente()) {

                // 1. Verificar si la poción en el índice actual está disponible
                boolean pocionDisponible = pociones[indiceActual];

                // 2. Si estaba disponible, la marcamos como USADA (false)
                if (pocionDisponible) {
                    pociones[indiceActual] = false;
                }

                // 3. Avanzar al siguiente índice
                indiceActual++;

                // 4. Devolver el estado de la poción consumida (true si fue útil, false si ya estaba usada)
                return pocionDisponible;
            }
            return null; // No quedan más pociones
        }
    }
}
