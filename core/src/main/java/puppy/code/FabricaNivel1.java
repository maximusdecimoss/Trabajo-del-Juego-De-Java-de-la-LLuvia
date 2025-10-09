// Archivo: FabricaNivel1.java
package puppy.code;

import com.badlogic.gdx.graphics.Texture;

/**
 * Fábrica Concreta para el Nivel 1.
 * Solo produce GotaMala y GotaBuena.
 */
public class FabricaNivel1 implements IFabricaObjetosLluviosos {

    private final Texture texMala;
    private final Texture texBuena;

    // Recibe las texturas del GestorGotas
    public FabricaNivel1(Texture texMala, Texture texBuena) {
        this.texMala = texMala;
        this.texBuena = texBuena;
    }

    @Override
    public ObjetoLluviosoAbstracto crearObjetoMalo(float posicionX) {
        // En Nivel 1, el objeto malo es la GotaMala
        return new GotaMala(this.texMala, posicionX);
    }

    @Override
    public ObjetoLluviosoAbstracto crearObjetoBueno(float posicionX) {
        // En Nivel 1, el objeto bueno es la GotaBuena
        return new GotaBuena(this.texBuena, posicionX);
    }
}
