package puppy.code;

import com.badlogic.gdx.graphics.Texture;

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

    @Override
    protected void liberarRecursosUnicos() {
        // No hay recursos únicos para liberar
    }
}
