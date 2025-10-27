package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

public class GestorRecursos implements Desechable {
    private static GestorRecursos instancia = null;
    public Texture tGotaBuena, tGotaMala, tTarro, tParaguas, tPersona, tPerro, tVikingo;
    public Texture tRoca, tEscudo, tGloboAgua, tMoneda, tHueso, tLodo, tMeteoro, tPocion;
    public Sound sDrop, sHurt;
    public Music mRain;

    private GestorRecursos() {
        tGotaBuena = new Texture(Gdx.files.internal("drop.png"));
        tGotaMala = new Texture(Gdx.files.internal("dropBad.png"));
        tTarro = new Texture(Gdx.files.internal("bucket.png"));
        tParaguas = new Texture(Gdx.files.internal("paraguas.png"));
        tPersona = new Texture(Gdx.files.internal("persona.png"));
        tPerro = new Texture(Gdx.files.internal("perro.png"));
        tVikingo = new Texture(Gdx.files.internal("vikingo.png"));
        tRoca = new Texture(Gdx.files.internal("roca.png"));
        tEscudo = new Texture(Gdx.files.internal("escudo.png"));
        tGloboAgua = new Texture(Gdx.files.internal("globo_agua.png"));
        tMoneda = new Texture(Gdx.files.internal("moneda.png"));
        tHueso = new Texture(Gdx.files.internal("hueso.png"));
        tLodo = new Texture(Gdx.files.internal("lodo.png"));
        tMeteoro = new Texture(Gdx.files.internal("meteoro.png"));
        tPocion = new Texture(Gdx.files.internal("pocion.png"));
        sDrop = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        sHurt = Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));
        mRain = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
    }

    public static GestorRecursos getInstancia() {
        if (instancia == null) {
            instancia = new GestorRecursos();
        }
        return instancia;
    }

    public Array<Texture> getTexturasReceptores() {
        Array<Texture> texturas = new Array<>();
        texturas.add(tTarro);
        texturas.add(tParaguas);
        texturas.add(tPersona);
        texturas.add(tPerro);
        texturas.add(tVikingo);
        return texturas;
    }

    @Override
    public void liberarRecursos() {
        tGotaBuena.dispose();
        tGotaMala.dispose();
        tTarro.dispose();
        tParaguas.dispose();
        tPersona.dispose();
        tPerro.dispose();
        tVikingo.dispose();
        tRoca.dispose();
        tEscudo.dispose();
        tGloboAgua.dispose();
        tMoneda.dispose();
        tHueso.dispose();
        tLodo.dispose();
        tMeteoro.dispose();
        tPocion.dispose();
        sDrop.dispose();
        sHurt.dispose();
        mRain.dispose();
        instancia = null;
    }
}
