// Archivo: Roca.java
package objetosQueCaen;

import com.badlogic.gdx.graphics.Texture;
import Gestores.GestorNiveles;
import puppy.code.ObjetoLluviosoAbstracto;
import Gestores.ReceptorAbstracto;

/**
 * Ítem de Nivel 2. Causa daño DOBLE.
 */
public class Roca extends ObjetoLluviosoAbstracto {

    public Roca(Texture textura, float posicionX) {
        super(textura, posicionX);
    }

    @Override
    protected void aplicarEfectoEspecifico(ReceptorAbstracto receptor, GestorNiveles gestor) {
        receptor.dañar(gestor);
        receptor.sumarPuntos(-gestor.obtenerPenalizacionPorNivel());
        if (receptor.getPuntos() < 0) receptor.setPuntos(0);
    }


}


