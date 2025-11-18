// Archivo: Moneda.java (Crea este archivo)
package objetosQueCaen;

import com.badlogic.gdx.graphics.Texture;
import Gestores.GestorNiveles;
import puppy.code.ObjetoLluviosoAbstracto;
import Gestores.ReceptorAbstracto;

/**
 * Ítem de Nivel 3 (Moneda). Otorga puntos basados en el nivel actual (GM1.7).
 * Hereda de ObjetoLluviosoFactorizado.
 */
public class Moneda extends ObjetoLluviosoAbstracto {

    public Moneda(Texture textura, float posicionX) {
        // La llamada a super() debe funcionar sin errores
        super(textura, posicionX);
    }



    @Override
    public void aplicarEfecto(ReceptorAbstracto receptor, GestorNiveles gestor) {
        // La recompensa se basa en el nivel actual (GM1.7)
        int nivelActual = gestor.getNivelActual();
        int puntosGanados = nivelActual * 20; // 20 puntos base por nivel

        receptor.sumarPuntos(puntosGanados);
    }
}
