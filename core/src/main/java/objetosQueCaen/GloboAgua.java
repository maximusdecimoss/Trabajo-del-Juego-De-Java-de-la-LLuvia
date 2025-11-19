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
    protected void aplicarEfectoEspecifico(ReceptorAbstracto receptor, GestorNiveles gestor) {
        receptor.dañar(gestor);
        int nivelActual = gestor.getNivelActual();
        int puntajeMinimoNivel = (nivelActual - 1) * 500;
        if (receptor.getPuntos() > puntajeMinimoNivel) {
            receptor.setPuntos(puntajeMinimoNivel);
        }
    }

}

