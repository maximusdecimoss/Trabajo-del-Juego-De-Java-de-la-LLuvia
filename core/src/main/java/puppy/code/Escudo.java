// Archivo: Escudo.java
package puppy.code;

import com.badlogic.gdx.graphics.Texture;

/**
 * Ítem de Nivel 2. Restaura una vida al jugador.
 * Hereda de ObjetoLluviosoFactorizado.
 */
public class Escudo extends ObjetoLluviosoFactorizado {

    public Escudo(Texture textura, float posicionX) {
        super(textura, posicionX);
    }

    @Override
    public void aplicarEfecto(ReceptorAbstracto receptor, GestorNiveles gestor) {
        // Implementar lógica de curación/bonificación:
        receptor.sumarPuntos(50); // Bonificación de puntos alta

        // Si el receptor tiene menos del máximo de vidas (asumamos 3), curar.
        if (receptor.getVidas() < 3) {
            // Nota: Esta lógica requiere un setter de vidas o un método curar en ReceptorAbstracto.
            // Por ahora, solo sumaremos puntos como bonificación.
        }

        // Lógica de Curación (Requiere añadir un método 'curar()' a ReceptorAbstracto)
        // receptor.curar(1);
    }
}
