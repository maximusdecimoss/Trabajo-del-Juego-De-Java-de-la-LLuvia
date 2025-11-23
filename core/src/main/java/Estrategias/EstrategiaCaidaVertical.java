// Archivo: EstrategiaCaidaVertical.java
package Estrategias;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import interfaces.IEstrategiaMovimiento;


public class EstrategiaCaidaVertical implements IEstrategiaMovimiento {

    @Override
    public void mover(Rectangle limites, float velocidadCaida, float factorVelocidad) {

        limites.y -= velocidadCaida * factorVelocidad * Gdx.graphics.getDeltaTime();
    }
}
