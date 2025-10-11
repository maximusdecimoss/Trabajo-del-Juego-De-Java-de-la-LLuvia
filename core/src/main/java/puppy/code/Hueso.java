// Archivo: Hueso.java
package puppy.code;

import com.badlogic.gdx.graphics.Texture;

public class Hueso extends ObjetoLluviosoAbstracto {

    public Hueso(Texture textura, float posicionX) {
        super(textura, posicionX);
    }

    @Override
    public void aplicarEfecto(ReceptorAbstracto receptor, GestorNiveles gestor) {

        // 1. Bonificación de Puntos
        // Usa una fórmula de bonificación de Nivel (ej. 25 puntos por nivel)
        int nivelActual = gestor.getNivelActual();
        receptor.sumarPuntos(nivelActual * 25);

        // 2. Efecto de Velocidad (USA SINGLETON - GM2.1)
        // Aumenta la velocidad un 50% (factor 1.5f) por 3 segundos.
        GestorTiempo.getInstancia().aplicarEfectoTemporal(1.5f, 3.0f);
    }
    protected void liberarRecursosUnicos() {
        // No hay recursos únicos para liberar en esta clase.
    }
}
