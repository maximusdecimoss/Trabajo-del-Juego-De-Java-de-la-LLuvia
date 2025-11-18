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
    public void aplicarEfecto(ReceptorAbstracto receptor, GestorNiveles gestor) {

        // 1. Daño y Penalización (pierde vida y penaliza puntos)
        receptor.dañar(gestor);

        // 2. Efecto de Lentitud (USA SINGLETON - GM2.1)
        // Ralentiza un 50% (factor 0.5f) por 5 segundos.
        GestorTiempo.getInstancia().aplicarEfectoTemporal(0.5f, 5.0f);
    }


}
