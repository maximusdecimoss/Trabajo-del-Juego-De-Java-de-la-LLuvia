package objetosQueCaen;

import com.badlogic.gdx.graphics.Texture;
import Gestores.GestorNiveles;
import puppy.code.ObjetoLluviosoAbstracto;
import Gestores.ReceptorAbstracto;

public class GotaCurativa extends ObjetoLluviosoAbstracto {
    public GotaCurativa(Texture textura, float posicionX) {
        super(textura, posicionX);
    }

    @Override
    protected void aplicarEfectoEspecifico(ReceptorAbstracto receptor, GestorNiveles gestor) {
        receptor.sumarPuntos(20);
        if (receptor.getVidas() < gestor.getVidasMaximas()) {
            receptor.setVidas(receptor.getVidas() + 1);
        }
    }

}
