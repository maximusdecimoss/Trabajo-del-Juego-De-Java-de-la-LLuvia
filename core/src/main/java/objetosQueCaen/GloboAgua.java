// Archivo: GloboAgua.java (Crea este archivo)
package objetosQueCaen;

import com.badlogic.gdx.graphics.Texture;
import Gestores.GestorNiveles;
import puppy.code.ObjetoLluviosoAbstracto;
import Gestores.ReceptorAbstracto;

/**
 * Ítem de Nivel 3 (Globo de Agua). Causa daño normal y resetea los puntos al mínimo del nivel actual.
 * Hereda de ObjetoLluviosoFactorizado.
 */
public class GloboAgua extends ObjetoLluviosoAbstracto {

    public GloboAgua(Texture textura, float posicionX) {
        super(textura, posicionX);
    }

    @Override
    public void aplicarEfecto(ReceptorAbstracto receptor, GestorNiveles gestor) {

        // 1. Causa el daño normal (pierde vida y penaliza GM1.7)
        receptor.dañar(gestor);

        // 2. Lógica de penalización de racha (Nueva regla)
        int nivelActual = gestor.getNivelActual();

        // Asumiendo que PUNTOS_POR_NIVEL es 500 (ver GestorNiveles)
        final int PUNTOS_POR_NIVEL = 500;

        // El puntaje mínimo para estar en el nivel actual
        int puntajeMinimoNivel = (nivelActual - 1) * PUNTOS_POR_NIVEL;

        // Resetea los puntos al valor base del nivel (si es mayor)
        if (receptor.getPuntos() > puntajeMinimoNivel) {
            receptor.setPuntos(puntajeMinimoNivel);
        }

    }

}

