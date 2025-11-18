package puppy.code;

import Gestores.GestorControlJuego;
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

        gestorControl.dibujarElementosJuego(batch);

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
