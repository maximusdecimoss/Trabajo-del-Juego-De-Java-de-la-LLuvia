// Archivo: EstrategiaMovimientoLateral.java (CORREGIDO)
package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.MathUtils;
import interfaces.IEstrategiaMovimiento;

/**
 * Estrategia Concreta: Implementa el movimiento lateral de un lado a otro
 * para los objetos del Nivel 3, USANDO UNA ONDA TEMPORAL (coseno) para que sea impredecible.
 */
public class EstrategiaMovimientoLateral implements IEstrategiaMovimiento {

    // Las variables ya NO son STATIC, así que no se comparten.
    private int direccionInicial; // Dirección inicial aleatoria.

    public EstrategiaMovimientoLateral() {
        // Asignamos una dirección aleatoria al CREAR el objeto (+1 o -1)
        this.direccionInicial = MathUtils.randomBoolean() ? 1 : -1;
    }

    @Override
    public void mover(Rectangle limites, float velocidadCaida, float factorVelocidad) {
        float delta = Gdx.graphics.getDeltaTime();


        float factorOnda = (float) Math.cos(limites.y / 100f + Gdx.graphics.getDeltaTime() * 100);

        // La velocidad real de movimiento horizontal es fija y se multiplica por la onda
        // y por la dirección inicial del objeto.
        float velocidadHorizontal = velocidadCaida * factorVelocidad * delta * direccionInicial;

        // 2. Mover lateralmente y verticalmente (Muy lento)
        limites.x += velocidadHorizontal;
        limites.y -= (velocidadCaida * factorVelocidad * delta * 0.1f); // Movimiento vertical lento

        // 3. Lógica de reaparición (Nueva Regla de Nivel 3)
        // La pantalla es 800 de ancho
        if (limites.x < 0 - limites.width) {
            limites.x = 800; // Aparece por la derecha
        } else if (limites.x > 800) {
            limites.x = 0 - limites.width; // Aparece por la izquierda
        }
    }
}
