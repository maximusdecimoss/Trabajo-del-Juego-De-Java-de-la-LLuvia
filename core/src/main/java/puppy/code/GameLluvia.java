package puppy.code;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

// IMPORTACIONES NECESARIAS PARA LA LÓGICA DE NIVELES AVANZADA
import com.badlogic.gdx.Input; // Para usar Input.Keys.SPACE, F
import puppy.code.GestorTiempo; // Para el Singleton (GM2.1)
import puppy.code.EstrategiaDoblePunto; // Para el Strategy (GM2.3)
import puppy.code.EstrategiaNormal; // Para el Strategy (GM2.3)
import puppy.code.Vikingo; // Necesario para castear al jugador en Nivel 5

public class GameLluvia extends ApplicationAdapter {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private BitmapFont font;

    // Declaración de objetos principales
    private ReceptorAbstracto jugador;
    private GestorGotas gestorGotas;
    private GestorNiveles gestorNiveles;

    // ATRIBUTOS PARA MANEJAR LOS ASSETS
    // ASSETS DE GOTAS Y SONIDO
    private Texture texturaGotaBuena;
    private Texture texturaGotaMala;
    private Sound sonidoGota;
    private Music musicaLluvia;
    private Sound sonidoHerido;

    // ASSETS DE RECEPTORES (5)
    private Texture texturaTarro;
    private Texture texturaParaguas;
    private Texture texturaPersona;
    private Texture texturaPerro;
    private Texture texturaVikingo;

    // ASSETS NUEVOS PARA NIVELES (Roca y Escudo)
    private Texture texturaRoca;
    private Texture texturaEscudo;

    // ¡NUEVOS ASSETS PARA NIVEL 3!
    private Texture texturaGloboAgua;
    private Texture texturaMoneda;

    // NUEVO ASSETs para nivel 4
    private Texture texturaHueso;
    private Texture texturaLodo;

    // NUEVO ASSETs para nivel 5
    private Texture texturaMeteoro;
    private Texture texturaPocion;


    @Override
    public void create () {
        font = new BitmapFont();

        // 1. Cargar ASSETS COMUNES y de Receptor
        this.sonidoHerido = Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));

        // 2. CARGAR TODAS LAS TEXTURAS DE LOS 5 RECEPTORES
        this.texturaTarro = new Texture(Gdx.files.internal("bucket.png"));
        this.texturaParaguas = new Texture(Gdx.files.internal("paraguas.png"));
        this.texturaPersona = new Texture(Gdx.files.internal("persona.png"));
        this.texturaPerro = new Texture(Gdx.files.internal("perro.png"));
        this.texturaVikingo = new Texture(Gdx.files.internal("vikingo.png"));

        // 3. CARGAR TEXTURAS DE ÍTEMS DE NIVEL 2-5
        this.texturaRoca = new Texture(Gdx.files.internal("roca.png"));
        this.texturaEscudo = new Texture(Gdx.files.internal("escudo.png"));
        this.texturaGloboAgua = new Texture(Gdx.files.internal("globo_agua.png"));
        this.texturaMoneda = new Texture(Gdx.files.internal("moneda.png"));
        this.texturaHueso = new Texture(Gdx.files.internal("hueso.png"));
        this.texturaLodo = new Texture(Gdx.files.internal("lodo.png"));
        this.texturaMeteoro = new Texture(Gdx.files.internal("meteoro.png")); // Nivel 5
        this.texturaPocion = new Texture(Gdx.files.internal("pocion.png"));   // Nivel 5

        // 4. Cargar ASSETS de la Lluvia
        this.texturaGotaBuena = new Texture(Gdx.files.internal("drop.png"));
        this.texturaGotaMala = new Texture(Gdx.files.internal("dropBad.png"));
        this.sonidoGota = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        this.musicaLluvia = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));


        // 5. Crear el Array de Texturas para el Gestor de Niveles
        Array<Texture> texturasReceptores = new Array<>();
        texturasReceptores.add(this.texturaTarro);
        texturasReceptores.add(this.texturaParaguas);
        texturasReceptores.add(this.texturaPersona);
        texturasReceptores.add(this.texturaPerro);
        texturasReceptores.add(this.texturaVikingo);

        // 6. Inicializar Gestores
        this.gestorNiveles = new GestorNiveles(texturasReceptores, this.sonidoHerido);

        // ¡PASAMOS TODAS LAS TEXTURAS AL CONSTRUCTOR DE GESTORGOTAS!
        this.gestorGotas = new GestorGotas(
            this.texturaGotaBuena, this.texturaGotaMala, this.sonidoGota, this.musicaLluvia,
            this.texturaRoca, this.texturaEscudo,
            this.texturaGloboAgua, this.texturaMoneda,
            this.texturaHueso,this.texturaLodo,
            this.texturaMeteoro, this.texturaPocion // ¡Texturas de Nivel 5!
        );

        // 7. El GestorNiveles crea el jugador INICIAL (Nivel 1)
        this.jugador = this.gestorNiveles.crearReceptor(1);

        // 8. Inicialización y Creación
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();

        this.jugador.crear();
        gestorGotas.crear();
    }


    @Override
    public void render () {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        // Dibujar textos (puntos, vidas y NIVEL)
        font.draw(batch, "Gotas totales: " + jugador.getPuntos(), 5, 475);
        font.draw(batch, "Vidas : " + jugador.getVidas(), 720, 475);
        font.draw(batch, "Nivel: " + gestorNiveles.getNivelActual(), 350, 475);

        // LÓGICA DE CONTROL DEL JUEGO
        if (!jugador.isGameOver()) {

            // 1. ¡PASO CRUCIAL DEL SINGLETON (GM2.1)!
            // Actualiza el factor de velocidad global descontando el tiempo
            GestorTiempo.getInstancia().actualizar(Gdx.graphics.getDeltaTime());

            // 2. Verificar avance de nivel
            this.jugador = this.gestorNiveles.actualizarEstado(this.jugador.getPuntos(), this.jugador);

            // 3. Movimiento y colisiones
            jugador.actualizarMovimiento();
            gestorGotas.actualizarMovimiento(jugador, gestorNiveles);

            // 4. INTEGRACIÓN DE HABILIDADES DEL VIKINGO (NIVEL 5)
            if (gestorNiveles.getNivelActual() == 5) {

                // Castear el ReceptorAbstracto al tipo Vikingo para acceder a sus métodos únicos
                Vikingo vikingo = (Vikingo) jugador;

                // ITERATOR (GM2.2): Usar poción con la tecla ESPACIO
                if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                    vikingo.usarPocion();
                }

                // STRATEGY (GM2.3): Alternar Furia con la tecla F
                if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
                    if (vikingo.estrategiaRecoleccion instanceof EstrategiaNormal) {
                        vikingo.activarFuria();
                    } else {
                        vikingo.desactivarFuria();
                    }
                }

                // Mostrar pociones restantes en el HUD
                font.draw(batch, "Pociones: " + vikingo.getPocionesRestantes(), 10, 440);
            }


        } else {
            // Lógica de Game Over
            font.draw(batch, "GAME OVER", 350, 240);
            this.musicaLluvia.stop();
        }

        // Dibujo
        jugador.dibujar(batch);
        gestorGotas.actualizarDibujoLluvia(batch);

        batch.end();
    }

    @Override
    public void dispose() {
        // 1. Llama a la INTERFAZ DESECHABLE (GM1.5)
        ((Desechable)this.jugador).liberarRecursos();
        ((Desechable)this.gestorGotas).liberarRecursos();

        // 2. Llama al método DISPOSE() DIRECTO de LibGDX (Texturas que GameLluvia cargó)
        // Libera las texturas de los 5 Receptores
        this.texturaTarro.dispose();
        this.texturaParaguas.dispose();
        this.texturaPersona.dispose();
        this.texturaPerro.dispose();
        this.texturaVikingo.dispose();

        // LIBERACIÓN DE LAS TEXTURAS DE LOS ÍTEMS (Nivel 2)
        this.texturaRoca.dispose();
        this.texturaEscudo.dispose();

        // ¡LIBERACIÓN DE LAS TEXTURAS DEL NIVEL 3!
        this.texturaGloboAgua.dispose();
        this.texturaMoneda.dispose();

        // ¡LIBERACIÓN DE LAS TEXTURAS DEL NIVEL 4!
        this.texturaHueso.dispose();
        this.texturaLodo.dispose();

        // ¡LIBERACIÓN DE LAS TEXTURAS DEL NIVEL 5!
        this.texturaMeteoro.dispose();
        this.texturaPocion.dispose();


        // Libera las texturas de las Gotas (base)
        this.texturaGotaBuena.dispose();
        this.texturaGotaMala.dispose();

        // Libera los elementos que GameLluvia maneja directamente
        this.batch.dispose();
        this.font.dispose();

        // Libera el sonido de herida
        this.sonidoHerido.dispose();
    }
}
