// Archivo: Roca.java
package puppy.code;

import com.badlogic.gdx.graphics.Texture;

/**
 * Ítem de Nivel 2. Causa daño DOBLE.
 * Hereda de ObjetoLluviosoFactorizado.
 */
public class Roca extends ObjetoLluviosoFactorizado {

    public Roca(Texture textura, float posicionX) {
        super(textura, posicionX);
    }

    @Override
    public void aplicarEfecto(ReceptorAbstracto receptor, GestorNiveles gestor) {
        // La Roca causa el daño estándar del nivel...
        receptor.dañar(gestor);

        // ... Y luego aplicamos una penalización extra (daño doble)
        int penalizacionExtra = gestor.obtenerPenalizacionPorNivel();
        receptor.sumarPuntos(-penalizacionExtra);

        // Aseguramos que los puntos no sean negativos
        if (receptor.getPuntos() < 0) {
            receptor.sumarPuntos(receptor.getPuntos() * -1);
        }
    }
}


