// Escudo.java
package objetosQueCaen;

import com.badlogic.gdx.graphics.Texture;
import Gestores.GestorNiveles;
import Gestores.ReceptorAbstracto;
import puppy.code.ObjetoLluviosoAbstracto;

public class Escudo extends ObjetoLluviosoAbstracto {

    public Escudo(Texture textura, float posicionX) {
        super(textura, posicionX);
    }

    @Override
    protected void pasoEspecificoEfecto(ReceptorAbstracto receptor, GestorNiveles gestor) {
        receptor.setTieneEscudo(true);
        receptor.sumarPuntos(50);
    }
}
