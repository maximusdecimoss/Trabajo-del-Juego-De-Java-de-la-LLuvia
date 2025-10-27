package puppy.code;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

/**
 * Clase concreta para el Nivel 1. Hereda la lógica central del jugador.
 */
public class Tarro extends ReceptorAbstracto {

    public Tarro(Texture tex, Sound ss) {
        super(tex, ss); // Llama al constructor del padre (ReceptorAbstracto)
    }

    @Override
    public void crear() {
        // Inicialización específica del Tarro (el Tarro empieza abajo)
        this.limites = new Rectangle(368f, 20f, ANCHO_RECEPTOR, ALTO_RECEPTOR);
        this.limites.x = 800 / 2 - 64 / 2;
        this.limites.y = 20;
        this.limites.width = 64;
        this.limites.height = 64;
    }
    protected void liberarRecursosUnicos() {
        // No hay recursos únicos para liberar en esta clase.
    }

    @Override
    public void liberarRecursos() {
        // Tarro (o el Receptor) es responsable de liberar sus propios assets.
        this.imagen.dispose();
        this.sonidoHerido.dispose();
    }

    // NOTA: Todos los demás métodos (dañar, sumarPuntos, isGameOver, etc.)
    // son heredados de ReceptorAbstracto, ¡así que no necesitas escribirlos aquí!
}
