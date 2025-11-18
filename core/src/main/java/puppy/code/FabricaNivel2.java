package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import interfaces.IFabricaObjetosLluviosos;
import objetosQueCaen.GotaCurativa;
import objetosQueCaen.Roca;

public class FabricaNivel2 implements IFabricaObjetosLluviosos {
    private final Texture texMala;
    private final Texture texBuena;

    public FabricaNivel2(Texture texMala, Texture texBuena) {
        this.texMala = texMala;
        this.texBuena = texBuena;
    }

    @Override
    public ObjetoLluviosoAbstracto crearObjetoMalo(float posicionX) {
        return new Roca(this.texMala, posicionX);
    }

    @Override
    public ObjetoLluviosoAbstracto crearObjetoBueno(float posicionX) {
        return new GotaCurativa(this.texBuena, posicionX);
    }
}
