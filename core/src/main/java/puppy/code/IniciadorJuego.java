package puppy.code;

import Gestores.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.audio.Music;

public class IniciadorJuego {

    private final GestorRecursos recursos;
    private final ReceptorAbstracto jugador;
    private final GestorGotas gestorGotas;
    private final GestorNiveles gestorNiveles;
    private final GestorControlJuego gestorControl;


    public IniciadorJuego(BitmapFont font, Music musicaLluvia) {
        this.recursos = GestorRecursos.getInstancia();

        // 1. Cargar texturas de receptores en orden (nivel 1 a 5)
        Array<Texture> texturasReceptores = new Array<>();
        texturasReceptores.add(recursos.getTTarro());
        texturasReceptores.add(recursos.getTParaguas());
        texturasReceptores.add(recursos.getTPersona());
        texturasReceptores.add(recursos.getTPerro());
        texturasReceptores.add(recursos.getTVikingo());


        this.gestorNiveles = new GestorNiveles(texturasReceptores, recursos.getSHurt());
        this.gestorGotas = new GestorGotas(
            recursos.getTGotaBuena(), recursos.getTGotaMala(), recursos.getSDrop(), musicaLluvia,
            recursos.getTRoca(), recursos.getTEscudo(), recursos.getTGloboAgua(), recursos.getTMoneda(),
            recursos.getTHueso(), recursos.getTLodo(), recursos.getTMeteoro(), recursos.getTPocion(),
            recursos.getTGotaCurativa()
        );


        this.jugador = this.gestorNiveles.crearReceptor(1);
        this.gestorControl = new GestorControlJuego(this.jugador, this.gestorGotas, this.gestorNiveles, musicaLluvia, font);


        this.jugador.crear();
        this.gestorGotas.crear();
    }

    public GestorControlJuego getGestorControl() {
        return gestorControl;
    }
}
