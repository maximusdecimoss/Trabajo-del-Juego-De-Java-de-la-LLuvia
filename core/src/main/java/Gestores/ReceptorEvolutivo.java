package Gestores;

import Estrategias.EstrategiaDoblePunto;
import Estrategias.EstrategiaNormal;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

/**
 * ÚNICA clase del jugador. Evoluciona por niveles y en nivel 5 tiene habilidades de Vikingo.
 */
public class ReceptorEvolutivo extends ReceptorAbstracto {

    // ATRIBUTOS DEL VIKINGO (nivel 5)
    private int pocionesRestantes = 0;  // Empieza en 0, se activan en nivel 5

    public ReceptorEvolutivo(Texture texturaInicial, Sound sonidoHerido) {
        super(texturaInicial, sonidoHerido);
        this.estrategiaRecoleccion = new EstrategiaNormal(); // Furia desactivada al inicio
    }

    @Override
    public void crear() {
        limites = new Rectangle(800 / 2 - 64 / 2, 20, 64, 64);
    }

    public void actualizarTextura(Texture nuevaTextura) {
        this.imagen = nuevaTextura;
    }

    // =============== MÉTODO QUE LLAMA GestorNiveles EN NIVEL 5 ===============
    public void activarModoVikingo() {
        this.vidas = 5;                // 5 vidas
        this.pocionesRestantes = 3;    // 3 pociones disponibles
    }

    // =============== HABILIDADES DEL VIKINGO ===============
    public boolean usarPocion() {
        if (pocionesRestantes > 0) {
            vidas++;
            pocionesRestantes--;
            return true;
        }
        return false;
    }

    public void activarFuria() {
        setEstrategiaRecoleccion(new EstrategiaDoblePunto());
    }

    public void desactivarFuria() {
        setEstrategiaRecoleccion(new EstrategiaNormal());
    }

    public int getPocionesRestantes() {
        return pocionesRestantes;
    }
}
