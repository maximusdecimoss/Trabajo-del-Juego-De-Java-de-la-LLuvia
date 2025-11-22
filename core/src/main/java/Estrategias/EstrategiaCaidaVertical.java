// Archivo: EstrategiaCaidaVertical.java
package Estrategias;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import interfaces.IEstrategiaMovimiento;

/**
 * Estrategia Concreta: Implementa la caída tradicional (arriba a abajo)
 * para la mayoría de los niveles.
 */
public class EstrategiaCaidaVertical implements IEstrategiaMovimiento {

    @Override
    public void mover(Rectangle limites, float velocidadCaida, float factorVelocidad) {
        // La lógica original de movimiento: solo el eje Y
        limites.y -= velocidadCaida * factorVelocidad * Gdx.graphics.getDeltaTime();
    }
}
