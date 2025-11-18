// Archivo: Paraguas.java
package objetosQueCaen;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import Gestores.ReceptorAbstracto;

public class Paraguas extends ReceptorAbstracto {

    public Paraguas(Texture tex, Sound ss) {
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
}
