// Archivo: GotaMala.java

package puppy.code;

import com.badlogic.gdx.graphics.Texture;

public class GotaMala extends ObjetoLluviosoAbstracto {

    public GotaMala(Texture textura, float posicionX) {
        super(textura, posicionX);
    }

    @Override
    // Implementa la nueva firma: recibe el receptor y el gestor de niveles
    public void aplicarEfecto(ReceptorAbstracto receptor, GestorNiveles gestor) {
        // Efecto: El receptor recibe daño. Le pasamos el gestor para que aplique
        // la penalización progresiva (GM1.7: restar Nivel*10 puntos).
        receptor.dañar(gestor);
    }

}
