// Archivo: Roca.java
package objetosQueCaen;

import com.badlogic.gdx.graphics.Texture;
import Gestores.GestorNiveles;
import puppy.code.ObjetoLluviosoAbstracto;
import Gestores.ReceptorAbstracto;

/**
 * Ítem de Nivel 2. Causa daño DOBLE.
 */
public class Roca extends ObjetoLluviosoAbstracto {

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


