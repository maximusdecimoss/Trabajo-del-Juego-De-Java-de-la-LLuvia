package Estrategias;

import com.badlogic.gdx.graphics.Texture;
import interfaces.IFabricaObjetosLluviosos;
import objetosQueCaen.GotaBuena;
import objetosQueCaen.GotaMala;
import puppy.code.ObjetoLluviosoAbstracto;

public class FabricaNivel1 implements IFabricaObjetosLluviosos {
    private final Texture texMala;
    private final Texture texBuena;

    public FabricaNivel1(Texture texMala, Texture texBuena) {
        this.texMala = texMala;
        this.texBuena = texBuena;
    }

    @Override
    public ObjetoLluviosoAbstracto crearObjetoMalo(float posicionX) {
        return new GotaMala(this.texMala, posicionX);
    }

    @Override
    public ObjetoLluviosoAbstracto crearObjetoBueno(float posicionX) {
        return new GotaBuena(this.texBuena, posicionX);
    }
}
