// Archivo: FabricaNivel3.java
package puppy.code;

import com.badlogic.gdx.graphics.Texture;

public class FabricaNivel3 implements IFabricaObjetosLluviosos {

    private final Texture texturaGloboAgua;
    private final Texture texturaMoneda;

    public FabricaNivel3(Texture texGlobo, Texture texMoneda) {
        this.texturaGloboAgua = texGlobo;
        this.texturaMoneda = texMoneda;
    }

    @Override
    public ObjetoLluviosoAbstracto crearObjetoMalo(float posicionX) {
        // La clase GloboAgua debe ser accesible
        return new GloboAgua(this.texturaGloboAgua, posicionX);
    }

    @Override
    public ObjetoLluviosoAbstracto crearObjetoBueno(float posicionX) {
        // La clase Moneda debe ser accesible
        return new Moneda(this.texturaMoneda, posicionX);
    }
}
