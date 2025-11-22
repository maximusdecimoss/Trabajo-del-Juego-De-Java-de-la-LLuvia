// Roca.java
package objetosQueCaen;

import com.badlogic.gdx.graphics.Texture;
import Gestores.GestorNiveles;
import Gestores.ReceptorAbstracto;
import puppy.code.ObjetoLluviosoAbstracto;

public class Roca extends ObjetoLluviosoAbstracto {

    public Roca(Texture textura, float posicionX) {
        super(textura, posicionX);
    }

    @Override
    protected void pasoEspecificoEfecto(ReceptorAbstracto receptor, GestorNiveles gestor) {
        receptor.dañar(gestor);
        receptor.sumarPuntos(-gestor.obtenerPenalizacionPorNivel());
        if (receptor.getPuntos() < 0) receptor.setPuntos(0);
    }
}
