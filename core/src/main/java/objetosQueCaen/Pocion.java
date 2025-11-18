// Archivo: Pocion.java
package objetosQueCaen;

import com.badlogic.gdx.graphics.Texture;
import Gestores.GestorNiveles;
import puppy.code.ObjetoLluviosoAbstracto;
import Gestores.ReceptorAbstracto;

public class Pocion extends ObjetoLluviosoAbstracto {

    public Pocion(Texture textura, float posicionX) {
        super(textura, posicionX);
    }

    @Override
    public void aplicarEfecto(ReceptorAbstracto receptor, GestorNiveles gestor) {
        // Bonificación de puntos alta
        receptor.sumarPuntos(300);

        // El Vikingo usa el Iterator para gestionar sus pociones internas.
        // NOTA: Esta poción de la lluvia solo da puntos, el jugador activa la poción real con [ESPACIO].
    }


}
