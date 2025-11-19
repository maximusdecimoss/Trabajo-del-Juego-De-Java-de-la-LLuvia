package puppy.code;

import Gestores.GestorControlJuego;
import Gestores.GestorRecursos;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameLluvia extends ApplicationAdapter {
    private SpriteBatch batch;
    private BitmapFont font;
    private IniciadorJuego iniciador;
    private GestorControlJuego gestorControl;
    private OrthographicCamera camera;
    private FitViewport viewport;

    @Override
    public void create() {
        // Configurar cámara y viewport
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, 800, 480); // Resolución 800x480
        this.viewport = new FitViewport(800, 480, camera);
        this.batch = new SpriteBatch();
        this.batch.setProjectionMatrix(camera.combined);
        this.font = new BitmapFont();
        this.iniciador = new IniciadorJuego(this.font, GestorRecursos.getInstancia().getMRain());
        this.gestorControl = this.iniciador.getGestorControl();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.0F, 0.0F, 0.2F, 1.0F);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.camera.update();
        this.batch.setProjectionMatrix(camera.combined);
        this.batch.begin();
        this.gestorControl.actualizarYRenderizarLogica(this.batch);
        this.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        this.viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        this.batch.dispose();
        this.font.dispose();
        GestorRecursos.getInstancia().liberarRecursos();
    }
}
