package puppy.code;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Array;

public class GameLluvia extends ApplicationAdapter {

    // ATRIBUTOS GRÁFICOS BÁSICOS
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private BitmapFont font;

    // CLASE DE DELEGACIÓN ÚNICA
    private IniciadorJuego iniciador;

    @Override
    public void create () {
        // 1. INICIALIZACIÓN DE UTILIDADES GRÁFICAS
        font = new BitmapFont();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();

        // 2. CARGA CENTRALIZADA DE RECURSOS (Singleton GM2.1)
        GestorRecursos.getInstancia();

        // 3. INICIALIZACIÓN DE TODA LA LÓGICA (Delegada a IniciadorJuego)
        this.iniciador = new IniciadorJuego(this.font, GestorRecursos.getInstancia().mRain);
    }


    @Override
    public void render () {
        // 1. PASOS ESENCIALES DE LIBGDX (Responsabilidad de ApplicationAdapter)
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        // 2. LLAMADA ÚNICA AL GESTOR DE CONTROL DE LÓGICA Y DIBUJO (GM1.6)
        // Este método ahora contiene: actualización, lógica de fin de juego,
        // dibujo del HUD, y llamadas a dibujar los elementos.
        this.iniciador.gestorControl.actualizarYRenderizarLogica(batch);

        batch.end();
    }

    @Override
    public void dispose() {
        // 1. LIBERACIÓN CENTRALIZADA DE RECURSOS (GM1.6/GM2.1)
        GestorRecursos.getInstancia().liberarRecursos();

        // 2. Liberación de elementos de la propia clase
        this.batch.dispose();
        this.font.dispose();
    }
}
