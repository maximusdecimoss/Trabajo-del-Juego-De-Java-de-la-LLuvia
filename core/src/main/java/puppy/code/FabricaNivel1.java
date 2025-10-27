package puppy.code;

import com.badlogic.gdx.graphics.Texture;

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
