// Archivo: Vikingo.java
package puppy.code;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Vikingo extends ReceptorAbstracto {

    // ATRIBUTOS DEL ITERATOR (GM2.2)
    private final ContenedorPociones suministros;
    private final IIteradorSuministros iteradorPociones;

    // Contador para mostrar las pociones restantes (se actualiza internamente)
    private int pocionesRestantes = 3;

    public Vikingo(Texture tex, Sound ss) {
        super(tex, ss);
        this.vidas = 5; // El Vikingo es el más resistente, inicia con más vidas

        // Inicializa el contenedor y obtiene el Iterador
        this.suministros = new ContenedorPociones();
        this.iteradorPociones = suministros.crearIterador();
    }

    @Override
    public void crear() {
        this.limites = new Rectangle();
        this.limites.x = 800 / 2 - 64 / 2;
        this.limites.y = 20;
        this.limites.width = 64;
        this.limites.height = 64;
    }

    // MÉTODO PARA USAR UNA POCIÓN (Lógica principal del Iterator)
    public boolean usarPocion() {
        // Llamamos al método que marca la poción como consumida y avanza.
        Object resultado = iteradorPociones.siguiente();

        if (resultado != null && (boolean) resultado) {
            // Si el iterador devolvió 'true', significa que se consumió una poción fresca.
            this.vidas += 1; // Le da 1 vida extra al Vikingo
            this.pocionesRestantes--; // Actualiza el contador visible
            return true;
        }
        return false; // No quedan más pociones o el iterador ya terminó.
    }

    // MÉTODOS DEL PATRÓN STRATEGY (GM2.3): Cambian el modo de sumar puntos

    // Activa la Estrategia de doble puntuación (Modo Furia/Power-up)
    public void activarFuria() {
        // Hereda de ReceptorAbstracto. Cambia el comportamiento de sumar puntos.
        this.setEstrategiaRecoleccion(new EstrategiaDoblePunto());
    }

    // Vuelve a la Estrategia normal
    public void desactivarFuria() {
        this.setEstrategiaRecoleccion(new EstrategiaNormal());
    }

    // Getter para el HUD
    public int getPocionesRestantes() {
        return this.pocionesRestantes;
    }
    protected void liberarRecursosUnicos() {
        // No hay recursos únicos para liberar en esta clase.
    }


}
