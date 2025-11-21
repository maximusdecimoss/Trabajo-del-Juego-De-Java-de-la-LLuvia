package Gestores;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class GestorNiveles {
    private final Array<Texture> texturasReceptores;
    private final Sound sonidoHerido;
    private int nivelActual = 1;

    private static final int[] UMBRALES_NIVEL = {0, 30, 100, 200, 500, 15000};

    public GestorNiveles(Array<Texture> texturas, Sound sonidoHerido) {
        this.texturasReceptores = texturas;
        this.sonidoHerido = sonidoHerido;
    }

    public ReceptorAbstracto crearReceptor(int nivel) {
        Texture textura = texturasReceptores.get(nivel - 1);
        return new ReceptorEvolutivo(textura, sonidoHerido);
    }

    public ReceptorAbstracto actualizarEstado(int puntos, ReceptorAbstracto receptorActual) {
        int nuevoNivel = calcularNivel(puntos);
        if (nuevoNivel != nivelActual) {
            nivelActual = nuevoNivel;
            ReceptorEvolutivo receptor = (ReceptorEvolutivo) receptorActual;
            Texture nuevaTextura = texturasReceptores.get(nuevoNivel - 1);
            receptor.actualizarTextura(nuevaTextura);

            if (nuevoNivel == 5) {
                receptor.activarModoVikingo();; // Vikingo especial
            }
        }
        return receptorActual;
    }

    private int calcularNivel(int puntos) {
        for (int i = UMBRALES_NIVEL.length - 1; i >= 0; i--) {
            if (puntos >= UMBRALES_NIVEL[i]) {
                return i + 1;
            }
        }
        return 1;
    }

    public boolean ganoJuego(int puntos) {
        return puntos >= 15000;
    }

    public int obtenerPenalizacionPorNivel() {
        return nivelActual * 10;
    }

    public int getNivelActual() {
        return nivelActual;
    }

    public int getVidasMaximas() {
        return nivelActual == 5 ? 5 : 3;
    }
}
