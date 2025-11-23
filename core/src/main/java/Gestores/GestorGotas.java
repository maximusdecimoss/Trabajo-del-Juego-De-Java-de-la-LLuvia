package Gestores;

import Estrategias.*;
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
    private IFabricaObjetosLluviosos fabricaActual;
    private int nivelAnterior = -1;
    private final Texture texturaEscudo;

    public GestorGotas(Texture texBuena, Texture texMala, Sound sonido, Music musica,
                       Texture texRoca, Texture texEscudo, Texture texGloboAgua, Texture texMoneda,
                       Texture texHueso, Texture texLodo, Texture texMeteoro, Texture texPocion,
                       Texture texGotaCurativa) {
        this.musicaLluvia = musica;
        this.texturaEscudo = texEscudo;
        this.objetosLluviosos = new Array<>();
    }

    public void crear() {
        objetosLluviosos.clear();
        musicaLluvia.setLooping(true);
        musicaLluvia.play();
    }

    private IFabricaObjetosLluviosos crearFabrica(int nivel) {
        GestorRecursos r = GestorRecursos.getInstancia();
        if (nivel == 1) return new FabricaNivel1(r.getTGotaMala(), r.getTGotaBuena());
        if (nivel == 2) return new FabricaNivel2(r.getTRoca(), r.getTGotaCurativa());
        if (nivel == 3) return new FabricaNivel3(r.getTGloboAgua(), r.getTMoneda());
        if (nivel == 4) return new FabricaNivel4(r.getTLodo(), r.getTHueso());
        if (nivel == 5) return new FabricaNivel5(r.getTMeteoro(), r.getTPocion());
        return new FabricaNivel1(r.getTGotaMala(), r.getTGotaBuena());
    }

    public void actualizarMovimiento(ReceptorAbstracto receptor, GestorNiveles gestorNiveles) {
        int nivelActual = gestorNiveles.getNivelActual();

        if (nivelActual != nivelAnterior) {
            nivelAnterior = nivelActual;
            fabricaActual = crearFabrica(nivelActual);
            objetosLluviosos.clear();
        }

        float factorVelocidad = 1.0f + (nivelActual - 1) * 0.2f;

        if (TimeUtils.nanoTime() - tiempoUltimaGota > (1000000000L * 2) / nivelActual) {
            crearGota(nivelActual);
        }

        for (int i = objetosLluviosos.size - 1; i >= 0; i--) {
            ObjetoLluviosoAbstracto objeto = objetosLluviosos.get(i);
            objeto.actualizar(factorVelocidad);

            if (objeto.estaFueraDePantalla()) {
                objetosLluviosos.removeIndex(i);
                continue;
            }

            if (objeto.obtenerLimites().overlaps(receptor.getArea())) {
                objeto.procesarColision(receptor, gestorNiveles);
                objetosLluviosos.removeIndex(i);
            }
        }
    }

    private void crearGota(int nivelActual) {
        float x = MathUtils.random(0, 736);
        ObjetoLluviosoAbstracto nueva = null;
        int prob = MathUtils.random(1, 100);

        if (prob <= 30) {
            nueva = fabricaActual.crearObjetoMalo(x);
        } else if (prob <= 90 || nivelActual == 1) {
            nueva = fabricaActual.crearObjetoBueno(x);
        } else if (nivelActual >= 2) {
            nueva = new Escudo(texturaEscudo, x);
        }

        if (nueva != null) {
            if (nivelActual == 3) {
                nueva.setEstrategiaMovimiento(new EstrategiaMovimientoLateral());
            }
            objetosLluviosos.add(nueva);
        }
        tiempoUltimaGota = TimeUtils.nanoTime();
    }


    public void actualizarDibujoLluvia(SpriteBatch batch) {
        for (ObjetoLluviosoAbstracto objeto : objetosLluviosos) {
            objeto.dibujar(batch);
        }
    }

    public void dibujar(SpriteBatch batch) {
        actualizarDibujoLluvia(batch);
    }
}
