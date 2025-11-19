package Gestores;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import interfaces.IFabricaObjetosLluviosos;
import objetosQueCaen.Escudo;
import puppy.code.*;

public class GestorGotas {
    private Array<ObjetoLluviosoAbstracto> objetosLluviosos;
    private long tiempoUltimaGota;
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
        this.objetosLluviosos.clear();
        this.musicaLluvia.setLooping(true);
        this.musicaLluvia.play();
    }

    private void crearObjetoLluvioso(int nivelActual) {
        float posicionX = MathUtils.random(0, 736);
        GestorRecursos recursos = GestorRecursos.getInstancia();
        ObjetoLluviosoAbstracto nuevoObjeto = null;
        int probabilidad = MathUtils.random(1, 100);

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

        // Cambiar fábrica si subimos de nivel
        if (this.fabricaActual == null || !this.esFabricaCorrecta(nivelActual)) {
            this.fabricaActual = this.seleccionarFabrica(nivelActual);
            this.objetosLluviosos.clear();
        }

        float factorVelocidad = 1.0f + (nivelActual - 1) * 0.2f;

        // Generar nueva gota según dificultad del nivel
        if (TimeUtils.nanoTime() - this.tiempoUltimaGota > (1000000000L * 2) / nivelActual) {
            this.crearObjetoLluvioso(nivelActual);
        }

        // Actualizar y comprobar colisiones
        for (int i = 0; i < this.objetosLluviosos.size; ++i) {
            ObjetoLluviosoAbstracto objeto = this.objetosLluviosos.get(i);
            objeto.actualizar(factorVelocidad);

            if (objeto.estaFueraDePantalla()) {
                this.objetosLluviosos.removeIndex(i);
                --i;
                continue;
            }

            if (objeto.obtenerLimites().overlaps(receptor.getArea())) {
                // ¡AQUÍ ESTÁ EL TEMPLATE METHOD EN ACCIÓN! (GM2.2)
                objeto.procesarColision(receptor, gestorNiveles);
                // El sonido ya se reproduce dentro del Template Method → no lo repetimos

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
}
