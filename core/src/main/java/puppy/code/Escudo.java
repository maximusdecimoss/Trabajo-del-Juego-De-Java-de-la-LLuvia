package puppy.code;

import com.badlogic.gdx.graphics.Texture;

public class Escudo extends ObjetoLluviosoAbstracto {
    public Escudo(Texture textura, float posicionX) {
        super(textura, posicionX);
    }

    @Override
    public void aplicarEfecto(ReceptorAbstracto receptor, GestorNiveles gestor) {
        receptor.setTieneEscudo(true); // Activa el escudo
        receptor.sumarPuntos(50); // Suma puntos por recoger el escudo
    }

}
