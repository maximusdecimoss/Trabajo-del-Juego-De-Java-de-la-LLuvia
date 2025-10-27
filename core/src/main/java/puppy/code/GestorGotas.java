package puppy.code;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.math.MathUtils;

public class GestorGotas implements Desechable {
    private Array<ObjetoLluviosoAbstracto> objetosLluviosos;
    private long tiempoUltimaGota;
    private Sound sonidoGota;
    private Music musicaLluvia;
    private final Texture texturaGotaBuena;
    private final Texture texturaGotaMala;
    private final Texture texturaRoca;
    private final Texture texturaEscudo;
    private final Texture texturaGloboAgua;
    private final Texture texturaMoneda;
    private final Texture texturaHueso;
    private final Texture texturaLodo;
    private final Texture texturaMeteoro;
    private final Texture texturaPocion;
    private final Texture texturaGotaCurativa;
    private IFabricaObjetosLluviosos fabricaActual;
    private RenderizadorGotas renderizador;

    public GestorGotas(Texture texBuena, Texture texMala, Sound sonido, Music musica,
                       Texture texRoca, Texture texEscudo, Texture texGloboAgua, Texture texMoneda,
                       Texture texHueso, Texture texLodo, Texture texMeteoro, Texture texPocion,
                       Texture texGotaCurativa) {
        this.texturaGotaBuena = texBuena;
        this.texturaGotaMala = texMala;
        this.sonidoGota = sonido;
        this.musicaLluvia = musica;
        this.texturaRoca = texRoca;
        this.texturaEscudo = texEscudo;
        this.texturaGloboAgua = texGloboAgua;
        this.texturaMoneda = texMoneda;
        this.texturaHueso = texHueso;
        this.texturaLodo = texLodo;
        this.texturaMeteoro = texMeteoro;
        this.texturaPocion = texPocion;
        this.texturaGotaCurativa = texGotaCurativa;
        this.renderizador = new RenderizadorGotas(this);
        this.objetosLluviosos = new Array<>();
    }

    public void crear() {
        this.objetosLluviosos = new Array<>();
        this.musicaLluvia.setLooping(true);
        this.musicaLluvia.play();
    }

    private void crearObjetoLluvioso(int nivelActual) {
        float posicionX = (float) MathUtils.random(0, 736);
        // Usar GestorRecursos para obtener la textura del escudo
        GestorRecursos recursos = GestorRecursos.getInstancia();
        ObjetoLluviosoAbstracto nuevoObjeto = null;
        int probabilidad = MathUtils.random(1, 100);

        // Probabilidad: 30% malo, 60% bueno, 10% escudo (en niveles 2+)
        if (probabilidad <= 30) {
            nuevoObjeto = this.fabricaActual.crearObjetoMalo(posicionX);
        } else if (probabilidad <= 90 || nivelActual == 1) {
            nuevoObjeto = this.fabricaActual.crearObjetoBueno(posicionX);
        } else if (nivelActual >= 2) {
            nuevoObjeto = new Escudo(recursos.getTEscudo(), posicionX);
        }

        if (nuevoObjeto != null) {
            if (this.fabricaActual instanceof FabricaNivel3) {
                nuevoObjeto.setEstrategiaMovimiento(new EstrategiaMovimientoLateral());
            }
            this.objetosLluviosos.add(nuevoObjeto);
        }
        this.tiempoUltimaGota = TimeUtils.nanoTime();
    }

    public void actualizarMovimiento(ReceptorAbstracto receptor, GestorNiveles gestorNiveles) {
        int nivelActual = gestorNiveles.getNivelActual();
        if (this.fabricaActual == null || !this.esFabricaCorrecta(nivelActual)) {
            this.fabricaActual = this.seleccionarFabrica(nivelActual);
            this.objetosLluviosos.clear();
        }
        float factorVelocidad = 1.0f + (nivelActual - 1) * 0.2f;
        if (TimeUtils.nanoTime() - this.tiempoUltimaGota > (1000000000L * 2) / nivelActual) {
            this.crearObjetoLluvioso(nivelActual);
        }
        for (int i = 0; i < this.objetosLluviosos.size; ++i) {
            ObjetoLluviosoAbstracto objeto = this.objetosLluviosos.get(i);
            objeto.actualizar(factorVelocidad);
            if (objeto.estaFueraDePantalla()) {
                this.objetosLluviosos.removeIndex(i);
                --i;
                continue;
            }
            if (objeto.obtenerLimites().overlaps(receptor.getArea())) {
                objeto.aplicarEfecto(receptor, gestorNiveles);
                this.sonidoGota.play();
                this.objetosLluviosos.removeIndex(i);
                --i;
            }
        }
    }

    public void actualizarDibujoLluvia(SpriteBatch batch) {
        for (ObjetoLluviosoAbstracto objeto : this.objetosLluviosos) {
            objeto.dibujar(batch);
        }
    }

    public void dibujar(SpriteBatch batch) {
        renderizador.actualizarDibujoLluvia(batch);
    }

    private boolean esFabricaCorrecta(int nivel) {
        if (nivel == 1) return this.fabricaActual instanceof FabricaNivel1;
        if (nivel == 2) return this.fabricaActual instanceof FabricaNivel2;
        if (nivel == 3) return this.fabricaActual instanceof FabricaNivel3;
        if (nivel == 4) return this.fabricaActual instanceof FabricaNivel4;
        if (nivel == 5) return this.fabricaActual instanceof FabricaNivel5;
        return false;
    }

    private IFabricaObjetosLluviosos seleccionarFabrica(int nivel) {
        GestorRecursos recursos = GestorRecursos.getInstancia();
        switch (nivel) {
            case 1:
                return new FabricaNivel1(recursos.getTGotaMala(), recursos.getTGotaBuena());
            case 2:
                return new FabricaNivel2(recursos.getTRoca(), recursos.getTGotaCurativa());
            case 3:
                return new FabricaNivel3(recursos.getTGloboAgua(), recursos.getTMoneda());
            case 4:
                return new FabricaNivel4(recursos.getTLodo(), recursos.getTHueso());
            case 5:
                return new FabricaNivel5(recursos.getTMeteoro(), recursos.getTPocion());
            default:
                return new FabricaNivel1(recursos.getTGotaMala(), recursos.getTGotaBuena());
        }
    }

    @Override
    public void liberarRecursos() {
        this.sonidoGota.dispose();
        this.musicaLluvia.dispose();
        this.texturaGotaBuena.dispose();
        this.texturaGotaMala.dispose();
        this.texturaRoca.dispose();
        this.texturaEscudo.dispose();
        this.texturaGloboAgua.dispose();
        this.texturaMoneda.dispose();
        this.texturaHueso.dispose();
        this.texturaLodo.dispose();
        this.texturaMeteoro.dispose();
        this.texturaPocion.dispose();
        this.texturaGotaCurativa.dispose();
    }
}
