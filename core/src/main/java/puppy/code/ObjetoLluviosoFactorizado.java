// Archivo: ObjetoLluviosoFactorizado.java
package puppy.code;

import com.badlogic.gdx.graphics.Texture;

/**
 * Clase padre abstracta para ítems especiales (Rocas, Escudos, Nubes, etc.).
 * Hereda la lógica básica de caída de ObjetoLluviosoAbstracto.
 * (Diseño Factorizado para niveles > 1)
 */
public abstract class ObjetoLluviosoFactorizado extends ObjetoLluviosoAbstracto {

    // En el constructor, simplemente llamamos al constructor del padre
    public ObjetoLluviosoFactorizado(Texture textura, float posicionX) {
        super(textura, posicionX);
    }

    // NOTA: Este objeto NO NECESITA re-declarar 'aplicarEfecto' o 'actualizar',
    // ya que hereda las firmas abstractas y concretas de ObjetoLluviosoAbstracto.
    // Sus clases hijas (Roca, Escudo) simplemente implementarán 'aplicarEfecto'.
}
