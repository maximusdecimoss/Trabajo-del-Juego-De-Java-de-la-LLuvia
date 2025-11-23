// Archivo: EstrategiaMovimientoLateral.java (CORREGIDO)
package Estrategias;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.MathUtils;
import interfaces.IEstrategiaMovimiento;

public class EstrategiaMovimientoLateral implements IEstrategiaMovimiento {


    private int direccionInicial; // Dirección inicial aleatoria.

    public EstrategiaMovimientoLateral() {
        // Asignamos una dirección aleatoria al CREAR el objeto (+1 o -1)
        this.direccionInicial = MathUtils.randomBoolean() ? 1 : -1;
    }

    @Override
    public void mover(Rectangle limites, float velocidadCaida, float factorVelocidad) {
        float delta = Gdx.graphics.getDeltaTime();


        float factorOnda = (float) Math.cos(limites.y / 100f + Gdx.graphics.getDeltaTime() * 100);

        float velocidadHorizontal = velocidadCaida * factorVelocidad * delta * direccionInicial;


        limites.x += velocidadHorizontal;
        limites.y -= (velocidadCaida * factorVelocidad * delta * 0.1f);

        if (limites.x < 0 - limites.width) {
            limites.x = 800;
        } else if (limites.x > 800) {
            limites.x = 0 - limites.width;
        }
    }
}
