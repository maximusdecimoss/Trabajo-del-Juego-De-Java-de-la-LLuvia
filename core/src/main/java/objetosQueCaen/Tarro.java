package objetosQueCaen;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import Gestores.ReceptorAbstracto;


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



}
