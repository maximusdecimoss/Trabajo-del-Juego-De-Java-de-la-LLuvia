// Archivo: Escudo.java
package puppy.code;

import com.badlogic.gdx.graphics.Texture;

/**
 * Ítem de Nivel 2. Restaura una vida al jugador.
 * Hereda de ObjetoLluviosoFactorizado.
 */
public class Escudo extends ObjetoLluviosoAbstracto {

    public Escudo(Texture textura, float posicionX) {
        super(textura, posicionX);
    }

// Archivo: Escudo.java (MÉTODO aplicarEfecto MODIFICADO)

    @Override
    public void aplicarEfecto(ReceptorAbstracto receptor, GestorNiveles gestor) {

        // 1. Activa el estado de protección
        receptor.tieneEscudo = true; // El Receptor ahora está protegido.

        // 2. Bonificación de puntos (para saber que lo recogió)
        receptor.sumarPuntos(50);

        // Opcional: Curar una vida, si la lógica lo permite.
        // if (receptor.getVidas() < 3) {
        //     receptor.setVidas(receptor.getVidas() + 1);
        // }
    }
}
