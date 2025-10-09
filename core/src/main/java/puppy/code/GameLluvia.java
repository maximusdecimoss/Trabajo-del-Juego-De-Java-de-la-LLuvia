package puppy.code;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameLluvia extends ApplicationAdapter {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private BitmapFont font;

    // Declaración de objetos principales
    private Tarro tarro;
    private GestorGotas gestorGotas; // Usando el nombre exacto de tu clase GestorGotas

    // ATRIBUTOS PARA MANEJAR LOS ASSETS que GameLluvia crea y otros usan
    private Texture texturaTarro;
    private Texture texturaGotaBuena;
    private Texture texturaGotaMala;
    private Sound sonidoGota;
    private Music musicaLluvia;
    private Sound sonidoHerido;


    @Override
    public void create () {
        font = new BitmapFont();

        // 1. Cargar ASSETS del Jugador (Tarro)
        // El Tarro necesita la textura y el sonido de ser herido
        this.sonidoHerido = Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));
        this.texturaTarro = new Texture(Gdx.files.internal("bucket.png"));
        tarro = new Tarro(this.texturaTarro, this.sonidoHerido);

        // 2. Cargar ASSETS de la Lluvia (Gotas y música)
        this.texturaGotaBuena = new Texture(Gdx.files.internal("drop.png"));
        this.texturaGotaMala = new Texture(Gdx.files.internal("dropBad.png")); // Asumiendo dropBad.png
        this.sonidoGota = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        this.musicaLluvia = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

        // 3. Inicializar GestorGotas, pasando los ASSETS
        gestorGotas = new GestorGotas(this.texturaGotaBuena, this.texturaGotaMala, this.sonidoGota, this.musicaLluvia);

        // 4. Inicialización de Cámara y Batch
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();

        // 5. Creación de objetos (inicialización de posiciones y arrays)
        tarro.crear();
        gestorGotas.crear();
    }


    @Override
    public void render () {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        // Dibujar textos (puntos y vidas)
        font.draw(batch, "Gotas totales: " + tarro.getPuntos(), 5, 475);
        font.draw(batch, "Vidas : " + tarro.getVidas(), 720, 475);

        // =======================================================
        // LÓGICA DE CONTROL DEL JUEGO
        // =======================================================
        if (!tarro.isGameOver()) {

            // Movimiento y caída solo ocurren si el tarro NO está herido
            // Tu clase Tarro ya tiene la lógica de movimiento, así que no hace falta el if anidado
            tarro.actualizarMovimiento();
            gestorGotas.actualizarMovimiento(tarro);

        } else {
            // Lógica de Game Over: Muestra un mensaje
            font.draw(batch, "GAME OVER", 350, 240);
            this.musicaLluvia.stop(); // Detenemos la música al perder
        }

        // Dibujo
        tarro.dibujar(batch);
        gestorGotas.actualizarDibujoLluvia(batch);

        batch.end();
    }

    @Override
    public void dispose() {
        // Implementación de la interfaz Desechable (GM1.5)
        // Llama a liberarRecursos() en las clases que implementan la interfaz.
        ((Desechable)this.tarro).liberarRecursos();
        ((Desechable)this.gestorGotas).liberarRecursos();

        // Libera los elementos que GameLluvia maneja directamente
        this.batch.dispose();
        this.font.dispose();
    }
}
