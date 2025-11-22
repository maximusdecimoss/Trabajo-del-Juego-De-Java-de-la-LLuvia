// Hueso.java
package objetosQueCaen;

import com.badlogic.gdx.graphics.Texture;
import Gestores.GestorNiveles;
import Gestores.GestorTiempo;
import Gestores.ReceptorAbstracto;
import puppy.code.ObjetoLluviosoAbstracto;

public class Hueso extends ObjetoLluviosoAbstracto {

    public Hueso(Texture textura, float posicionX) {
        super(textura, posicionX);
    }

    @Override
    protected void pasoEspecificoEfecto(ReceptorAbstracto receptor, GestorNiveles gestor) {
        receptor.sumarPuntos(gestor.getNivelActual() * 25);
        GestorTiempo.getInstancia().aplicarEfectoTemporal(1.5f, 3.0f);
    }
}
