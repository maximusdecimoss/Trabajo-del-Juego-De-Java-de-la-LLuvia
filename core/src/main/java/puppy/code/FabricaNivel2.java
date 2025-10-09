// Archivo: FabricaNivel2.java
package puppy.code;

import com.badlogic.gdx.graphics.Texture;

/**
 * Fábrica Concreta para el Nivel 2.
 * Produce la Roca (Malo) y el Escudo (Bueno).
 */
public class FabricaNivel2 implements IFabricaObjetosLluviosos {

    private final Texture texRoca;
    private final Texture texEscudo;

    // Recibe las texturas del GestorGotas
    public FabricaNivel2(Texture texRoca, Texture texEscudo) {
        this.texRoca = texRoca;
        this.texEscudo = texEscudo;
    }

    @Override
    public ObjetoLluviosoAbstracto crearObjetoMalo(float posicionX) {
        // En Nivel 2, el objeto malo es la Roca
        return new Roca(this.texRoca, posicionX);
    }

    @Override
    public ObjetoLluviosoAbstracto crearObjetoBueno(float posicionX) {
        // En Nivel 2, el objeto bueno es el Escudo
        return new Escudo(this.texEscudo, posicionX);
    }
}
