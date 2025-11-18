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
    public void aplicarEfecto(ReceptorAbstracto receptor, GestorNiveles gestor) {
        // 1. Aplica el daño normal (pierde 1 vida + penalización progresiva)
        receptor.dañar(gestor);

        // 2. Aplica una penalización de puntos masiva y fija por ser el jefe final.
        // Esto crea el "castigo" por el impacto del meteoro sin quitar más vidas.
        receptor.sumarPuntos(-300);

        // Asegurarse de que el puntaje no baje de 0
        if (receptor.getPuntos() < 0) {
            receptor.setPuntos(0);
        }
    }
}

