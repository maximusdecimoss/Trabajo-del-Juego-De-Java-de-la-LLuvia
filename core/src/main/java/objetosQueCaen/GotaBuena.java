// Archivo: GotaBuena.java

package objetosQueCaen;

import com.badlogic.gdx.graphics.Texture;
import Gestores.GestorNiveles;
import puppy.code.ObjetoLluviosoAbstracto;
import Gestores.ReceptorAbstracto;

public class GotaBuena extends ObjetoLluviosoAbstracto {

    public GotaBuena(Texture textura, float posicionX) {
        super(textura, posicionX);
    }

    @Override
    protected void aplicarEfectoEspecifico(ReceptorAbstracto receptor, GestorNiveles gestor) {
        receptor.sumarPuntos(10);
    }

}
