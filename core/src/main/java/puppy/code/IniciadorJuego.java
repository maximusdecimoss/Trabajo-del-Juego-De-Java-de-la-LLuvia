// Archivo: IniciadorJuego.java
package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * Clase responsable de inicializar y conectar TODAS las dependencias (Gestores, Jugador)
 * usando los recursos del Singleton. Cumple GM1.6.
 */
public class IniciadorJuego {

    private final GestorRecursos recursos;

    // Atributos que se inicializarán y serán usados por GameLluvia
    public ReceptorAbstracto jugador;
    public GestorGotas gestorGotas;
    public GestorNiveles gestorNiveles;
    public GestorControlJuego gestorControl;

    public IniciadorJuego(BitmapFont font, Music musicaLluvia) {
        this.recursos = GestorRecursos.getInstancia();

        // 1. Crear el Array de Texturas (Delegado de la lógica de crear receptores)
        Array<Texture> texturasReceptores = new Array<>();
        texturasReceptores.add(recursos.tTarro);
        texturasReceptores.add(recursos.tParaguas);
        texturasReceptores.add(recursos.tPersona);
        texturasReceptores.add(recursos.tPerro);
        texturasReceptores.add(recursos.tVikingo);

        // 2. Inicialización de los Gestores
        this.gestorNiveles = new GestorNiveles(texturasReceptores, recursos.sHurt);

        this.gestorGotas = new GestorGotas(
            recursos.tGotaBuena, recursos.tGotaMala, recursos.sDrop, recursos.mRain,
            recursos.tRoca, recursos.tEscudo, recursos.tGloboAgua, recursos.tMoneda,
            recursos.tHueso, recursos.tLodo, recursos.tMeteoro, recursos.tPocion
        );

        // 3. Creación del Jugador Inicial
        this.jugador = this.gestorNiveles.crearReceptor(1);

        // 4. Inicialización del Gestor de Control
        this.gestorControl = new GestorControlJuego(this.jugador, this.gestorGotas, this.gestorNiveles, recursos.mRain, font);

        // 5. Ejecutar métodos de inicialización
        this.jugador.crear();
        this.gestorGotas.crear();
    }
}
