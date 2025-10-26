// Archivo: FabricaNivel5.java
package puppy.code;

import com.badlogic.gdx.graphics.Texture;

public class FabricaNivel5 implements IFabricaObjetosLluviosos {

    private final Texture texMeteoro;
    private final Texture texPocion;

    public FabricaNivel5(Texture texMeteoro, Texture texPocion) {
        this.texMeteoro = texMeteoro;
        this.texPocion = texPocion;
    }

    @Override
    public ObjetoLluviosoAbstracto crearObjetoMalo(float posicionX) {
        return new Meteoro(this.texMeteoro, posicionX);
    }

    @Override
    public ObjetoLluviosoAbstracto crearObjetoBueno(float posicionX) {
        return new Pocion(this.texPocion, posicionX);
    }
}
