// Archivo: Hueso.java
package objetosQueCaen;

import com.badlogic.gdx.graphics.Texture;
import Gestores.GestorNiveles;
import Gestores.GestorTiempo;
import puppy.code.ObjetoLluviosoAbstracto;
import Gestores.ReceptorAbstracto;

public class Hueso extends ObjetoLluviosoAbstracto {

    public Hueso(Texture textura, float posicionX) {
        super(textura, posicionX);
    }

    @Override
    protected void aplicarEfectoEspecifico(ReceptorAbstracto receptor, GestorNiveles gestor) {
        receptor.sumarPuntos(gestor.getNivelActual() * 25);
        GestorTiempo.getInstancia().aplicarEfectoTemporal(1.5f, 3.0f);
    }
}
