// Archivo: IIteradorSuministros.java
package interfaces;

/**
 * Define la interfaz del Patrón Iterator (GM2.2).
 * Permite la navegación sobre una colección de objetos (pociones) sin exponer
 * su representación interna.
 */
public interface IIteradorSuministros {

    /**
     * Verifica si aún quedan pociones disponibles.
     * @return true si hay más elementos, false si se llegó al final.
     */
    boolean tieneSiguiente();

    /**
     Devuelve el elemento actual y avanza el índice al siguiente.
     En este caso, devuelve un marcador de poción o null.
     @return El siguiente objeto de la colección.
     */
    Object siguiente();
}
