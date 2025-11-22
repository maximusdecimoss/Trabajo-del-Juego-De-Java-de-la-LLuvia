// Meteoro.java
package objetosQueCaen;

import com.badlogic.gdx.graphics.Texture;
import Gestores.GestorNiveles;
import Gestores.ReceptorAbstracto;
import puppy.code.ObjetoLluviosoAbstracto;

public class Meteoro extends ObjetoLluviosoAbstracto {

    public Meteoro(Texture textura, float posicionX) {
        super(textura, posicionX);
        this.velocidad = 550f;
    }

    @Override
    protected void pasoEspecificoEfecto(ReceptorAbstracto receptor, GestorNiveles gestor) {
        receptor.dañar(gestor);
        receptor.sumarPuntos(-300);
        if (receptor.getPuntos() < 0) receptor.setPuntos(0);
    }
}
