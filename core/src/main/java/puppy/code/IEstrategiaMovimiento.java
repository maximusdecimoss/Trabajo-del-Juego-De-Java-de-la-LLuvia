// Archivo: IEstrategiaMovimiento.java
package puppy.code;
import com.badlogic.gdx.math.Rectangle;
/**
 * Define la interfaz del Patrón Strategy para la lógica de movimiento.
 */
public interface IEstrategiaMovimiento {

    void mover(Rectangle limites, float velocidadCaida, float factorVelocidad);
}
