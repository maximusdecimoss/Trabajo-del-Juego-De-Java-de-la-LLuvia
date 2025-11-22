// Lodo.java
package objetosQueCaen;

import com.badlogic.gdx.graphics.Texture;
import Gestores.GestorNiveles;
import Gestores.GestorTiempo;
import Gestores.ReceptorAbstracto;
import puppy.code.ObjetoLluviosoAbstracto;

public class Lodo extends ObjetoLluviosoAbstracto {

    public Lodo(Texture textura, float posicionX) {
        super(textura, posicionX);
    }

    @Override
    protected void pasoEspecificoEfecto(ReceptorAbstracto receptor, GestorNiveles gestor) {
        receptor.dañar(gestor);
        GestorTiempo.getInstancia().aplicarEfectoTemporal(0.5f, 5.0f);
    }
}
