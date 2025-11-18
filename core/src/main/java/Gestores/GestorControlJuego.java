package Gestores;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.audio.Music;
import objetosQueCaen.Vikingo;
import puppy.code.*;

public class GestorControlJuego {
    protected ReceptorAbstracto jugador;
    protected GestorGotas gestorGotas;
    protected GestorNiveles gestorNiveles;
    protected Music musicaLluvia;
    protected BitmapFont font;
    protected RenderizadorJuego renderizador; // Nuevo atributo


    public GestorControlJuego(ReceptorAbstracto jugador, GestorGotas gestorGotas, GestorNiveles gestorNiveles, Music musicaLluvia, BitmapFont font) {
        this.jugador = jugador;
        this.gestorGotas = gestorGotas;
        this.gestorNiveles = gestorNiveles;
        this.musicaLluvia = musicaLluvia;
        this.font = font;
        this.renderizador = new RenderizadorJuego(this, font); // Inicializar renderizador
    }

    public void actualizarYRenderizarLogica(SpriteBatch batch) {
        // 1. CONDICIONES DE FIN DE JUEGO
        if (gestorNiveles.ganoJuego(jugador.getPuntos())) {
            renderizador.dibujarFinJuego(batch, true); // Delegar dibujo de victoria
            this.musicaLluvia.stop();
            return;
        } else if (jugador.isGameOver()) {
            renderizador.dibujarFinJuego(batch, false); // Delegar dibujo de game over
            this.musicaLluvia.stop();
            return;
        }

        // 2. JUEGO ACTIVO
        GestorTiempo.getInstancia().actualizar(Gdx.graphics.getDeltaTime());
        this.jugador = this.gestorNiveles.actualizarEstado(this.jugador.getPuntos(), this.jugador);
        boolean permitirMovimientoVertical = gestorNiveles.getNivelActual() == 3;
        jugador.actualizarMovimiento(permitirMovimientoVertical);
        gestorGotas.actualizarMovimiento(jugador, gestorNiveles);
        if (gestorNiveles.getNivelActual() == 5) {
            this.manejarHabilidadesVikingo();
        }

        // 3. DIBUJO DE ELEMENTOS Y HUD
        renderizador.dibujar(batch); // Delegar todo el dibujo
    }

    private void manejarHabilidadesVikingo() {
        Vikingo vikingo = (Vikingo) jugador;
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

    public void dibujarHUDPrincipal(SpriteBatch batch) {
        font.draw(batch, "Gotas totales: " + jugador.getPuntos(), 5, 475);
        font.draw(batch, "Vidas : " + jugador.getVidas(), 720, 475);
        font.draw(batch, "Nivel: " + gestorNiveles.getNivelActual(), 350, 475);
        dibujarHUDVikingo(batch);
    }

    private void dibujarHUDVikingo(SpriteBatch batch) {
        if (gestorNiveles.getNivelActual() == 5) {
            Vikingo vikingo = (Vikingo) jugador;
            font.draw(batch, "Pociones: " + vikingo.getPocionesRestantes(), 10, 440);
        }
    }

    public void dibujarElementosJuego(SpriteBatch batch) {
        jugador.dibujar(batch);
        gestorGotas.dibujar(batch); // Cambiar a nuevo método
    }

    // Getter para el renderizador (usado por GameLluvia si es necesario)

}
