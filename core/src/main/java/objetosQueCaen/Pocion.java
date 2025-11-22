// Pocion.java
package objetosQueCaen;

import com.badlogic.gdx.graphics.Texture;
import Gestores.GestorNiveles;
import Gestores.ReceptorAbstracto;
import puppy.code.ObjetoLluviosoAbstracto;

public class Pocion extends ObjetoLluviosoAbstracto {

    public Pocion(Texture textura, float posicionX) {
        super(textura, posicionX);
    }

    @Override
    protected void pasoEspecificoEfecto(ReceptorAbstracto receptor, GestorNiveles gestor) {
        receptor.sumarPuntos(300);
    }
}
