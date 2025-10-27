package puppy.code;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RenderizadorGotas {
    private final GestorGotas gestorGotas;

    public RenderizadorGotas(GestorGotas gestorGotas) {
        this.gestorGotas = gestorGotas;
    }

    public void actualizarDibujoLluvia(SpriteBatch batch) {
        gestorGotas.actualizarDibujoLluvia(batch);
    }
}
