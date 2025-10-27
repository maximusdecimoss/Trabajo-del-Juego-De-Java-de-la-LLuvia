// Archivo: IEstrategiaMovimiento.java
package puppy.code;
import com.badlogic.gdx.math.Rectangle;
/**
 * Define la interfaz del Patrón Strategy para la lógica de movimiento.
 */
public interface IEstrategiaMovimiento {

    /**
     * Calcula y actualiza la posición del objeto.
     * @param limites El área del objeto a mover (Rectangle).
     * @param velocidadCaida La velocidad base del objeto.
     * @param factorVelocidad El factor de dificultad y Singleton.
     */
    void mover(Rectangle limites, float velocidadCaida, float factorVelocidad);
}
