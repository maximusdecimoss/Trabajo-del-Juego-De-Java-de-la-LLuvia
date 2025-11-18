// Archivo: IEstrategiaRecoleccion.java
package interfaces;

/**
 * Interfaz del Patrón Strategy (GM2.3). Define el contrato para la acción
 * de sumar puntos.
 */
public interface IEstrategiaRecoleccion {

    /**
     * Define la fórmula para calcular el puntaje a sumar por el ítem.
     * @param puntajeBase El valor base del ítem (ej. 10, 25, 50).
     * @return El puntaje final a sumar.
     */
    int sumarPuntos(int puntajeBase);
}
