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
    public void aplicarEfecto(ReceptorAbstracto receptor, GestorNiveles gestor) {
        // Suma 20 puntos (nivel 2, más valioso que GotaBuena)
        receptor.sumarPuntos(20);
        // Restaura 1 vida si no está al máximo
        if (receptor.getVidas() < gestor.getVidasMaximas()) {
            receptor.setVidas(receptor.getVidas() + 1);
        }
    }


}
