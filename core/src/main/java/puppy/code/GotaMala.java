// Archivo: GotaMala.java
package puppy.code;

import com.badlogic.gdx.graphics.Texture;

/**
 * Representa la gota que quita vida en el Nivel 1.
 * (Hereda de ObjetoLluviosoAbstracto, cumple GM1.4/GM1.6)
 */
public class GotaMala extends ObjetoLluviosoAbstracto {

    public GotaMala(Texture textura, float posicionX) {
        super(textura, posicionX);
        // Podríamos hacer que las gotas malas caigan más rápido aquí:
        // this.velocidadCaida = 400.0F;
    }

    @Override
    public void aplicarEfecto(Tarro receptor) {
        // Efecto: El receptor recibe daño (pierde vida y activa invencibilidad temporal)
        receptor.dañar();

        // NOTA: La penalización progresiva de puntos (-10, -20, etc.)
        // debe ser implementada en el método 'dañar()' o uno nuevo en la clase Tarro.
    }
}
