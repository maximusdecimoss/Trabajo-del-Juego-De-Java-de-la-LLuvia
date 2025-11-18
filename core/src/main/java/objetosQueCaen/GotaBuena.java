// Archivo: GotaBuena.java

package objetosQueCaen;

import com.badlogic.gdx.graphics.Texture;
import Gestores.GestorNiveles;
import puppy.code.ObjetoLluviosoAbstracto;
import Gestores.ReceptorAbstracto;

public class GotaBuena extends ObjetoLluviosoAbstracto {

    public GotaBuena(Texture textura, float posicionX) {
        super(textura, posicionX);
    }

    @Override
    // Implementa la nueva firma: recibe el receptor y el gestor de niveles
    public void aplicarEfecto(ReceptorAbstracto receptor, GestorNiveles gestor) {
        // Efecto: Sumar 10 puntos (independiente del nivel)
        receptor.sumarPuntos(10);
    }

}
