// Archivo: FabricaNivel5.java
package Estrategias;

import com.badlogic.gdx.graphics.Texture;
import interfaces.IFabricaObjetosLluviosos;
import objetosQueCaen.Meteoro;
import objetosQueCaen.Pocion;
import puppy.code.ObjetoLluviosoAbstracto;

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
