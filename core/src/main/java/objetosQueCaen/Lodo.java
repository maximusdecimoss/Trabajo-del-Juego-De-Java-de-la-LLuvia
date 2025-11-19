// Archivo: Lodo.java
package objetosQueCaen;

import com.badlogic.gdx.graphics.Texture;
import Gestores.GestorNiveles;
import Gestores.GestorTiempo;
import puppy.code.ObjetoLluviosoAbstracto;
import Gestores.ReceptorAbstracto;

public class Lodo extends ObjetoLluviosoAbstracto {

    public Lodo(Texture textura, float posicionX) {
        super(textura, posicionX);
    }

    @Override
    protected void aplicarEfectoEspecifico(ReceptorAbstracto receptor, GestorNiveles gestor) {
        receptor.dañar(gestor);
        GestorTiempo.getInstancia().aplicarEfectoTemporal(0.5f, 5.0f);
    }

}
