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

	   private Tarro tarro;
	   private Lluvia lluvia;
	@Override
	public void create () {
		 font = new BitmapFont(); // use libGDX's default Arial font

		  // load the images for the droplet and the bucket, 64x64 pixels each
		  Sound hurtSound = Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));
		  tarro = new Tarro(new Texture(Gdx.files.internal("bucket.png")),hurtSound);

	      // load the drop sound effect and the rain background "music"
          Texture gota = new Texture(Gdx.files.internal("drop.png"));
          Texture gotaMala = new Texture(Gdx.files.internal("dropBad.png"));

          Sound dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));

	      Music rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
          lluvia = new Lluvia(gota, gotaMala, dropSound, rainMusic);

	      // camera
	      camera = new OrthographicCamera();
	      camera.setToOrtho(false, 800, 480);
	      batch = new SpriteBatch();
	      // creacion del tarro
	      tarro.crear();

	      // creacion de la lluvia
	      lluvia.crear();
	}



	@Override
    public void render () {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        // Dibujar textos (puntos y vidas) - Esto siempre debe dibujarse
        font.draw(batch, "Gotas totales: " + tarro.getPuntos(), 5, 475);
        font.draw(batch, "Vidas : " + tarro.getVidas(), 720, 475);

        // =======================================================
        // NUEVA LÓGICA DE CONTROL DEL JUEGO
        // =======================================================
        if (!tarro.isGameOver()) { // Verifica si el juego AÚN está activo

            if (!tarro.estaHerido()) {
                // Movimiento y caída solo ocurren si no hay Game Over y no está herido
                tarro.actualizarMovimiento();
                lluvia.actualizarMovimiento(tarro);
            }

        } else {
            // Lógica de Game Over: Muestra un mensaje en el centro de la pantalla.
            font.draw(batch, "GAME OVER", 350, 240);

            // Opcional: Detener la música de fondo aquí
            // lluvia.detenerMusica(); // Si agregaste este método a Lluvia.java
        }

        // El dibujo siempre se mantiene, para que se vean los objetos finales
        tarro.dibujar(batch);
        lluvia.actualizarDibujoLluvia(batch);

        batch.end();
    }

	@Override
	public void dispose () {
	      tarro.destruir();
          lluvia.destruir();
	      batch.dispose();
	      font.dispose();
	}
}

