package Gestores;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.audio.Music;
import Estrategias.EstrategiaNormal;
import puppy.code.RenderizadorJuego;

public class GestorControlJuego {

    private  ReceptorAbstracto jugador;
    private final GestorGotas gestorGotas;
    private final GestorNiveles gestorNiveles;
    private final Music musicaLluvia;
    private final BitmapFont font;
    private final RenderizadorJuego renderizador;

    public GestorControlJuego(ReceptorAbstracto jugador, GestorGotas gestorGotas,
                              GestorNiveles gestorNiveles, Music musicaLluvia, BitmapFont font) {
        this.jugador = jugador;
        this.gestorGotas = gestorGotas;
        this.gestorNiveles = gestorNiveles;
        this.musicaLluvia = musicaLluvia;
        this.font = font;
        this.renderizador = new RenderizadorJuego(this, font);
    }

    public void actualizarYRenderizarLogica(SpriteBatch batch) {
        // === FIN DE JUEGO ===
        if (gestorNiveles.ganoJuego(jugador.getPuntos())) {
            renderizador.dibujarFinJuego(batch, true);
            musicaLluvia.stop();
            return;
        }
        if (jugador.isGameOver()) {
            renderizador.dibujarFinJuego(batch, false);
            musicaLluvia.stop();
            return;
        }

        // === LÓGICA DEL JUEGO ===
        GestorTiempo.getInstancia().actualizar(Gdx.graphics.getDeltaTime());
        jugador = gestorNiveles.actualizarEstado(jugador.getPuntos(), jugador);

        // Movimiento vertical desde nivel 3 (Persona, Perro y Vikingo)
        boolean permitirVertical = gestorNiveles.getNivelActual() >= 3;
        jugador.actualizarMovimiento(permitirVertical);

        gestorGotas.actualizarMovimiento(jugador, gestorNiveles);

        // === HABILIDADES DEL VIKINGO (NIVEL 5)
        if (gestorNiveles.getNivelActual() == 5 && jugador instanceof ReceptorEvolutivo) {
            ReceptorEvolutivo vikingo = (ReceptorEvolutivo) jugador;

            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                vikingo.usarPocion();
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
                if (vikingo.estrategiaRecoleccion instanceof EstrategiaNormal) {
                    vikingo.activarFuria();
                } else {
                    vikingo.desactivarFuria();
                }
            }
        }

        // === DIBUJO ===
        renderizador.dibujar(batch);
    }

    // ==================== HUD ====================
    public void dibujarHUDPrincipal(SpriteBatch batch) {
        font.draw(batch, "Puntos: " + jugador.getPuntos(), 10, 470);
        font.draw(batch, "Vidas: " + jugador.getVidas(), 650, 470);
        font.draw(batch, "Nivel: " + gestorNiveles.getNivelActual(), 350, 470);

        // HUD especial del Vikingo
        if (gestorNiveles.getNivelActual() == 5 && jugador instanceof ReceptorEvolutivo) {
            ReceptorEvolutivo vikingo = (ReceptorEvolutivo) jugador;
            font.draw(batch, "Pociones: " + vikingo.getPocionesRestantes(), 10, 430);
            font.draw(batch, "[F] Modo Furia | [ESPACIO] Usar poción", 200, 430);
        }
    }

    public void dibujarElementosJuego(SpriteBatch batch) {
        jugador.dibujar(batch);
        gestorGotas.dibujar(batch);
    }
}
