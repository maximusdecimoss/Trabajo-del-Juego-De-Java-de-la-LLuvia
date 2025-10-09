// Archivo: GotaBuena.java
package puppy.code;

import com.badlogic.gdx.graphics.Texture;

/**
 * Representa la gota que suma puntos en el Nivel 1.
 * (Hereda de ObjetoLluviosoAbstracto, cumple GM1.4/GM1.6)
 */
public class GotaBuena extends ObjetoLluviosoAbstracto {

    public GotaBuena(Texture textura, float posicionX) {
        super(textura, posicionX);
    }

    @Override
    public void aplicarEfecto(Tarro receptor) {
        // Efecto: Sumar 10 puntos
        receptor.sumarPuntos(10);
        // Si la Gota Buena tiene un sonido, se reproduciría aquí o en el Gestor
    }
}
