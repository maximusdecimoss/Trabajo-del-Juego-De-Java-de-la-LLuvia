package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

/**
 * Clase Singleton para gestionar la carga y liberación de recursos del juego (texturas, sonidos, música).
 * Cumple con el principio de encapsulamiento al usar atributos privados y getters.
 * (Cumple GM1.5, GM2.1)
 */
public class GestorRecursos {
    private static GestorRecursos instancia = null;

    private Texture tTarro;
    private Texture tParaguas;
    private Texture tPersona;
    private Texture tPerro;
    private Texture tVikingo;
    private Texture tGotaBuena;
    private Texture tGotaMala;
    private Texture tRoca;
    private Texture tEscudo;
    private Texture tGloboAgua;
    private Texture tMoneda;
    private Texture tHueso;
    private Texture tLodo;
    private Texture tMeteoro;
    private Texture tPocion;
    private Texture tGotaCurativa;
    private Sound sDrop;
    private Sound sHurt;
    private Music mRain;

    private GestorRecursos() {
        // Carga de recursos
        this.tTarro = new Texture(Gdx.files.internal("bucket.png"));
        this.tParaguas = new Texture(Gdx.files.internal("paraguas.png"));
        this.tPersona = new Texture(Gdx.files.internal("persona.png"));
        this.tPerro = new Texture(Gdx.files.internal("perro.png"));
        this.tVikingo = new Texture(Gdx.files.internal("vikingo.png"));
        this.tGotaBuena = new Texture(Gdx.files.internal("drop.png"));
        this.tGotaMala = new Texture(Gdx.files.internal("dropBad.png"));
        this.tRoca = new Texture(Gdx.files.internal("roca.png"));
        this.tEscudo = new Texture(Gdx.files.internal("escudo.png"));
        this.tGloboAgua = new Texture(Gdx.files.internal("globo_agua.png"));
        this.tMoneda = new Texture(Gdx.files.internal("moneda.png"));
        this.tHueso = new Texture(Gdx.files.internal("hueso.png"));
        this.tLodo = new Texture(Gdx.files.internal("lodo.png"));
        this.tMeteoro = new Texture(Gdx.files.internal("meteoro.png"));
        this.tPocion = new Texture(Gdx.files.internal("pocion.png"));
        this.tGotaCurativa = new Texture(Gdx.files.internal("curativa.png"));
        this.sDrop = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        this.sHurt = Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));
        this.mRain = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
    }

    public static GestorRecursos getInstancia() {
        if (instancia == null) {
            instancia = new GestorRecursos();
        }
        return instancia;
    }

    // Getters para los recursos
    public Texture getTTarro() { return tTarro; }
    public Texture getTParaguas() { return tParaguas; }
    public Texture getTPersona() { return tPersona; }
    public Texture getTPerro() { return tPerro; }
    public Texture getTVikingo() { return tVikingo; }
    public Texture getTGotaBuena() { return tGotaBuena; }
    public Texture getTGotaMala() { return tGotaMala; }
    public Texture getTRoca() { return tRoca; }
    public Texture getTEscudo() { return tEscudo; }
    public Texture getTGloboAgua() { return tGloboAgua; }
    public Texture getTMoneda() { return tMoneda; }
    public Texture getTHueso() { return tHueso; }
    public Texture getTLodo() { return tLodo; }
    public Texture getTMeteoro() { return tMeteoro; }
    public Texture getTPocion() { return tPocion; }
    public Texture getTGotaCurativa() { return tGotaCurativa; }
    public Sound getSDrop() { return sDrop; }
    public Sound getSHurt() { return sHurt; }
    public Music getMRain() { return mRain; }

    public void liberarRecursos() {
        tTarro.dispose();
        tParaguas.dispose();
        tPersona.dispose();
        tPerro.dispose();
        tVikingo.dispose();
        tGotaBuena.dispose();
        tGotaMala.dispose();
        tRoca.dispose();
        tEscudo.dispose();
        tGloboAgua.dispose();
        tMoneda.dispose();
        tHueso.dispose();
        tLodo.dispose();
        tMeteoro.dispose();
        tPocion.dispose();
        tGotaCurativa.dispose();
        sDrop.dispose();
        sHurt.dispose();
        mRain.dispose();
        instancia = null;
    }
}
