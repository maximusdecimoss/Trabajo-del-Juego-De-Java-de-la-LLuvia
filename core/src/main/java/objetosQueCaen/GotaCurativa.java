// GotaCurativa.java
package objetosQueCaen;

import com.badlogic.gdx.graphics.Texture;
import Gestores.GestorNiveles;
import Gestores.ReceptorAbstracto;
import puppy.code.ObjetoLluviosoAbstracto;

public class GotaCurativa extends ObjetoLluviosoAbstracto {

    public GotaCurativa(Texture textura, float posicionX) {
        super(textura, posicionX);
    }

    @Override
    protected void pasoEspecificoEfecto(ReceptorAbstracto receptor, GestorNiveles gestor) {
        receptor.sumarPuntos(20);
        if (receptor.getVidas() < gestor.getVidasMaximas()) {
            receptor.setVidas(receptor.getVidas() + 1);
        }
    }
}
