// Archivo: Persona.java
package puppy.code;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Persona extends ReceptorAbstracto {

    public Persona(Texture tex, Sound ss) {
        super(tex, ss);
    }

    @Override
    public void crear() {
        this.limites = new Rectangle();
        this.limites.x = 800 / 2 - 64 / 2;
        this.limites.y = 20;
        this.limites.width = 64;
        this.limites.height = 64;
    }

    @Override
    public void liberarRecursos() {
        this.imagen.dispose();
        this.sonidoHerido.dispose();
    }

    protected void liberarRecursosUnicos() {
        // No hay recursos únicos para liberar en esta clase.
    }
}
