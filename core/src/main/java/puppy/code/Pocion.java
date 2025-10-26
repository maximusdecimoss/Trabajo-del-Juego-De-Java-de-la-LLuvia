// Archivo: Pocion.java
package puppy.code;

import com.badlogic.gdx.graphics.Texture;

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

    @Override
    protected void liberarRecursosUnicos() { } // Template Method (GM2.2)
}
