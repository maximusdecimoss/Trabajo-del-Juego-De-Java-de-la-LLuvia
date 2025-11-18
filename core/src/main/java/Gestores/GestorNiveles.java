package Gestores;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import objetosQueCaen.*;

public class GestorNiveles {
    private final Array<Texture> texturasReceptores;
    private final Sound sonidoHerido;
    private int nivelActual = 1;

    // Umbrales de puntos para cada nivel
    private static final int[] UMBRALES_NIVEL = {0, 30, 100, 200, 500};

    public GestorNiveles(Array<Texture> texturas, Sound sonidoHerido) {
        this.texturasReceptores = texturas;
        this.sonidoHerido = sonidoHerido;
    }

    public ReceptorAbstracto crearReceptor(int nivel) {
        switch (nivel) {
            case 1: return new Tarro(texturasReceptores.get(0), sonidoHerido);
            case 2: return new Paraguas(texturasReceptores.get(1), sonidoHerido);
            case 3: return new Persona(texturasReceptores.get(2), sonidoHerido);
            case 4: return new Perro(texturasReceptores.get(3), sonidoHerido);
            case 5: return new Vikingo(texturasReceptores.get(4), sonidoHerido);
            default: return new Tarro(texturasReceptores.get(0), sonidoHerido);
        }
    }

    public ReceptorAbstracto actualizarEstado(int puntos, ReceptorAbstracto receptorActual) {
        int nivelCalculado = calcularNivel(puntos);
        if (nivelCalculado != this.nivelActual) {
            this.nivelActual = nivelCalculado;
            ReceptorAbstracto nuevoReceptor = this.crearReceptor(nivelCalculado);
            nuevoReceptor.crear();
            nuevoReceptor.setPuntos(receptorActual.getPuntos());
            nuevoReceptor.setVidas(receptorActual.getVidas());
            nuevoReceptor.setTieneEscudo(receptorActual.tieneEscudo()); // Transferir estado del escudo
            // Liberar recursos del receptor anterior
            return nuevoReceptor;
        }
        return receptorActual;
    }

    private int calcularNivel(int puntos) {
        if (puntos >= UMBRALES_NIVEL[4]) return 5; // 10,000+ puntos
        if (puntos >= UMBRALES_NIVEL[3]) return 4; // 5,000+ puntos
        if (puntos >= UMBRALES_NIVEL[2]) return 3; // 1,000+ puntos
        if (puntos >= UMBRALES_NIVEL[1]) return 2; // 150+ puntos
        return 1; // 0-149 puntos
    }

    public boolean ganoJuego(int puntos) {
        return puntos >= 15000;
    }

    public int obtenerPenalizacionPorNivel() {
        return this.nivelActual * 10;
    }

    public int getNivelActual() {
        return this.nivelActual;
    }

    public int getVidasMaximas() {
        if (nivelActual == 5) return 5; // Vikingo
        return 3; // Otros receptores
    }
}
