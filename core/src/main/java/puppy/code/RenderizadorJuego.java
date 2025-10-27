package puppy.code;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RenderizadorJuego {
    private final GestorControlJuego gestorControl;
    private final BitmapFont font;

    public RenderizadorJuego(GestorControlJuego gestorControl, BitmapFont font) {
        this.gestorControl = gestorControl;
        this.font = font;
    }

    public void dibujar(SpriteBatch batch) {
        // Dibuja elementos del juego (jugador e ítems)
        gestorControl.dibujarElementosJuego(batch);
        // Dibuja HUD (puntos, vidas, nivel, pociones)
        gestorControl.dibujarHUDPrincipal(batch);
    }

    public void dibujarFinJuego(SpriteBatch batch, boolean victoria) {
        if (victoria) {
            font.draw(batch, "¡VICTORIA! Puntos Máximos Alcanzados", 250, 240);
        } else {
            font.draw(batch, "GAME OVER", 350, 240);
        }
    }
}
