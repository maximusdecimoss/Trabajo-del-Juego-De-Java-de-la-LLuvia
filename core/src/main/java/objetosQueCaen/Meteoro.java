// Archivo: Meteoro.java (CORREGIDO)
package objetosQueCaen;

import com.badlogic.gdx.graphics.Texture;
import Gestores.GestorNiveles;
import puppy.code.ObjetoLluviosoAbstracto;
import Gestores.ReceptorAbstracto;

public class Meteoro extends ObjetoLluviosoAbstracto {

    public Meteoro(Texture textura, float posicionX) {
        super(textura, posicionX);
        this.velocidad = 550f;
    }

    @Override
    protected void aplicarEfectoEspecifico(ReceptorAbstracto receptor, GestorNiveles gestor) {
        receptor.dañar(gestor);
        receptor.sumarPuntos(-300);
        if (receptor.getPuntos() < 0) receptor.setPuntos(0);
    }
}

