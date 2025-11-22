// GloboAgua.java
package objetosQueCaen;

import com.badlogic.gdx.graphics.Texture;
import Gestores.GestorNiveles;
import Gestores.ReceptorAbstracto;
import puppy.code.ObjetoLluviosoAbstracto;

public class GloboAgua extends ObjetoLluviosoAbstracto {

    public GloboAgua(Texture textura, float posicionX) {
        super(textura, posicionX);
    }

    @Override
    protected void pasoEspecificoEfecto(ReceptorAbstracto receptor, GestorNiveles gestor) {
        receptor.dañar(gestor);
        int nivelActual = gestor.getNivelActual();
        int puntajeMinimoNivel = (nivelActual - 1) * 500;
        if (receptor.getPuntos() > puntajeMinimoNivel) {
            receptor.setPuntos(puntajeMinimoNivel);
        }
    }
}
