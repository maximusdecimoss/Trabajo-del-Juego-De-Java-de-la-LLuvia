package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class IniciadorJuego {
    private final GestorRecursos recursos;
    public ReceptorAbstracto jugador;
    public GestorGotas gestorGotas;
    public GestorNiveles gestorNiveles;
    public GestorControlJuego gestorControl;

    public IniciadorJuego(BitmapFont font, Music musicaLluvia) {
        this.recursos = GestorRecursos.getInstancia();
        Array<Texture> texturasReceptores = new Array<>();
        texturasReceptores.add(recursos.getTTarro());
        texturasReceptores.add(recursos.getTParaguas());
        texturasReceptores.add(recursos.getTPersona());
        texturasReceptores.add(recursos.getTPerro());
        texturasReceptores.add(recursos.getTVikingo());
        this.gestorNiveles = new GestorNiveles(texturasReceptores, recursos.getSHurt());
        this.gestorGotas = new GestorGotas(
            recursos.getTGotaBuena(), recursos.getTGotaMala(), recursos.getSDrop(), recursos.getMRain(),
            recursos.getTRoca(), recursos.getTEscudo(), recursos.getTGloboAgua(), recursos.getTMoneda(),
            recursos.getTHueso(), recursos.getTLodo(), recursos.getTMeteoro(), recursos.getTPocion(),
            recursos.getTGotaCurativa()
        );
        this.jugador = this.gestorNiveles.crearReceptor(1);
        this.gestorControl = new GestorControlJuego(this.jugador, this.gestorGotas, this.gestorNiveles, recursos.getMRain(), font);
        this.jugador.crear();
        this.gestorGotas.crear();
    }
}
