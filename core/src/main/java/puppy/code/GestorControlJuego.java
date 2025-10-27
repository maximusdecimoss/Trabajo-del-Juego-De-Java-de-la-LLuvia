package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont; // <--- Necesario para el font
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.audio.Music; // <--- NECESARIO PARA musicaLluvia
import com.badlogic.gdx.audio.Sound; // <--- Necesario si usas sonido (no en este fragmento, pero por si acaso)
// ... (Otros imports de Strategy y Vikingo) ...

// Asegúrate de importar estas clases también para que el código compile:
import puppy.code.Vikingo;
import puppy.code.EstrategiaNormal;
/**
 * Clase responsable de la lógica del juego (Game Logic) y las condiciones de fin de juego,
 * liberando al GameLluvia.render() de ciclos y condicionales (GM1.6).
 */
public class GestorControlJuego {

    private ReceptorAbstracto jugador;
    private GestorGotas gestorGotas;
    private GestorNiveles gestorNiveles;
    private Music musicaLluvia;
    private BitmapFont font;

    // Constructor: Recibe todas las referencias necesarias
    public GestorControlJuego(ReceptorAbstracto jugador, GestorGotas gestorGotas, GestorNiveles gestorNiveles, Music musicaLluvia, BitmapFont font) {
        this.jugador = jugador;
        this.gestorGotas = gestorGotas;
        this.gestorNiveles = gestorNiveles;
        this.musicaLluvia = musicaLluvia;
        this.font = font;
    }

    // MÉTODO ÚNICO: Contiene TODA la lógica que antes estaba en el else del render().
    public void actualizarYRenderizarLogica(SpriteBatch batch) {

        // 1. CONDICIONES DE FIN DE JUEGO
        if (gestorNiveles.ganoJuego(jugador.getPuntos())) {
            font.draw(batch, "¡VICTORIA! Puntos Máximos Alcanzados", 250, 240);
            this.musicaLluvia.stop();
            return; // Termina la actualización de lógica

        } else if (jugador.isGameOver()) {
            font.draw(batch, "GAME OVER", 350, 240);
            this.musicaLluvia.stop();
            return; // Termina la actualización de lógica
        }

        // 2. JUEGO ACTIVO (Todo lo que antes estaba en el 'else')

        // Singletons y Avance
        GestorTiempo.getInstancia().actualizar(Gdx.graphics.getDeltaTime());
        this.jugador = this.gestorNiveles.actualizarEstado(this.jugador.getPuntos(), this.jugador);

        // Lógica de Movimiento 2D (Nivel 3)
        boolean permitirMovimientoVertical = gestorNiveles.getNivelActual() == 3;
        jugador.actualizarMovimiento(permitirMovimientoVertical);

        // Colisiones y Caída de Ítems
        gestorGotas.actualizarMovimiento(jugador, gestorNiveles);

        // HABILIDADES DEL VIKINGO (NIVEL 5)
        if (gestorNiveles.getNivelActual() == 5) {
            this.manejarHabilidadesVikingo();
        }

        // Dibujo de la Lluvia y Jugador (se hacen fuera, pero las llamadas deben ser simples)
        this.dibujarHUDVikingo(batch);
    }

    // MÉTODO DELEGADO: Extrae la lógica del Vikingo (GM1.6)
    private void manejarHabilidadesVikingo() {
        Vikingo vikingo = (Vikingo) jugador;

        // ITERATOR (GM2.2)
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            vikingo.usarPocion();
        }

        // STRATEGY (GM2.3)
        if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            if (vikingo.estrategiaRecoleccion instanceof EstrategiaNormal) {
                vikingo.activarFuria();
            } else {
                vikingo.desactivarFuria();
            }
        }
    }

    // MÉTODO DELEGADO: Dibuja elementos secundarios del HUD (GM1.6)
    private void dibujarHUDVikingo(SpriteBatch batch) {
        if (gestorNiveles.getNivelActual() == 5) {
            Vikingo vikingo = (Vikingo) jugador;
            font.draw(batch, "Pociones: " + vikingo.getPocionesRestantes(), 10, 440);
        }
    }

    public void dibujarHUDPrincipal(SpriteBatch batch) {
        font.draw(batch, "Gotas totales: " + jugador.getPuntos(), 5, 475);
        font.draw(batch, "Vidas : " + jugador.getVidas(), 720, 475);
        font.draw(batch, "Nivel: " + gestorNiveles.getNivelActual(), 350, 475);

        // Si el nivel es 5, dibuja las pociones
        this.dibujarHUDVikingo(batch);
    }

    // MÉTODO NUEVO: Dibuja todos los elementos del juego (sacando las llamadas finales)
    public void dibujarElementosJuego(SpriteBatch batch) {
        jugador.dibujar(batch);
        gestorGotas.actualizarDibujoLluvia(batch);
    }
}
