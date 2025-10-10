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
    private Texture texturaGloboAgua; // NUEVO
    private Texture texturaMoneda;    // NUEVO


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

        // 3. CARGAR TEXTURAS NUEVAS PARA LOS NIVELES
        this.texturaRoca = new Texture(Gdx.files.internal("roca.png"));
        this.texturaEscudo = new Texture(Gdx.files.internal("escudo.png"));

        // ¡CARGAR TEXTURAS DEL NIVEL 3!
        this.texturaGloboAgua = new Texture(Gdx.files.internal("globo_agua.png")); // Cambia el nombre si es diferente
        this.texturaMoneda = new Texture(Gdx.files.internal("moneda.png"));       // Cambia el nombre si es diferente

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
            this.texturaRoca, this.texturaEscudo, // Texturas de Nivel 2
            this.texturaGloboAgua, this.texturaMoneda // ¡Texturas de Nivel 3!
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

            // 1. Verificar avance de nivel
            this.jugador = this.gestorNiveles.actualizarEstado(this.jugador.getPuntos(), this.jugador);

            // 2. Movimiento y colisiones
            jugador.actualizarMovimiento();
            gestorGotas.actualizarMovimiento(jugador, gestorNiveles);

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
        this.texturaTarro.dispose(); // <-- ¡Asegúrate de liberar esta!
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

        // Libera las texturas de las Gotas
        this.texturaGotaBuena.dispose();
        this.texturaGotaMala.dispose();

        // Libera los elementos que GameLluvia maneja directamente
        this.batch.dispose();
        this.font.dispose();

        // Libera el sonido de herida
        this.sonidoHerido.dispose();
    }
}
