package Gestores;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import interfaces.IEstrategiaRecoleccion;
import puppy.code.EstrategiaNormal;

/**
 * Clase padre abstracta para todos los receptores (Tarro, Paraguas, Vikingo, etc.).
 * Contiene la lógica común de estadísticas, movimiento y colisión.
 * (Cumple GM1.4)
 */
public abstract class ReceptorAbstracto  {
    protected IEstrategiaRecoleccion estrategiaRecoleccion;
    protected Rectangle limites;
    protected Texture imagen;
    protected Sound sonidoHerido;
    protected int vidas = 3;
    protected int puntos = 0;
    protected int velx = 400;
    protected boolean herido = false;
    protected int tiempoHeridoMax = 50;
    protected int tiempoHerido;
    protected boolean tieneEscudo = false;
    protected final float VELOCIDAD_BASE = 400f;
    protected final float ANCHO_RECEPTOR = 64f;
    protected final float ALTO_RECEPTOR = 64f;

    public ReceptorAbstracto(Texture tex, Sound ss) {
        this.imagen = tex;
        this.sonidoHerido = ss;
        this.estrategiaRecoleccion = new EstrategiaNormal();
    }

    public void setPuntos(int nuevosPuntos) {
        this.puntos = nuevosPuntos;
    }

    public void setVidas(int nuevasVidas) {
        this.vidas = nuevasVidas;
    }

    public abstract void crear();

    public void dañar(GestorNiveles gestor) {
        if (this.tieneEscudo) {
            this.tieneEscudo = false;
            return;
        }
        if (this.vidas > 0) {
            --this.vidas;
            int penalizacion = gestor.obtenerPenalizacionPorNivel();
            this.puntos -= penalizacion;
            if (this.puntos < 0) {
                this.puntos = 0;
            }
            this.herido = true;
            this.tiempoHerido = this.tiempoHeridoMax;
            this.sonidoHerido.play();
        }
    }

    public boolean isGameOver() {
        return this.vidas <= 0;
    }

    public boolean estaHerido() {
        return herido;
    }

    public void actualizarMovimiento(boolean permitirMovimientoVertical) {
        float factorSingleton = GestorTiempo.getInstancia().getFactorVelocidadGlobal();
        float velocidadReal = VELOCIDAD_BASE * factorSingleton;

        // Movimiento Lateral (X)
        float nuevaX = this.limites.x;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            nuevaX -= velocidadReal * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            nuevaX += velocidadReal * Gdx.graphics.getDeltaTime();
        }

        // Movimiento Vertical (Y) - Solo si se permite (Nivel 3)
        float nuevaY = this.limites.y;
        if (permitirMovimientoVertical) {
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                nuevaY += velocidadReal * Gdx.graphics.getDeltaTime();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                nuevaY -= velocidadReal * Gdx.graphics.getDeltaTime();
            }
        }

        // Aplicar límites
        this.limites.x = Math.max(0, Math.min(nuevaX, 800 - ANCHO_RECEPTOR));
        this.limites.y = Math.max(0, Math.min(nuevaY, 480 - ALTO_RECEPTOR));
    }

    public void dibujar(SpriteBatch batch) {
        batch.draw(
            this.imagen,
            this.limites.x,
            this.limites.y,
            ANCHO_RECEPTOR,
            ALTO_RECEPTOR
        );
    }

    public int getVidas() { return this.vidas; }
    public int getPuntos() { return this.puntos; }
    public Rectangle getArea() { return this.limites; }

    public void sumarPuntos(int puntajeBase) {
        int puntosASumar = this.estrategiaRecoleccion.sumarPuntos(puntajeBase);
        this.puntos += puntosASumar;
    }

    public void setEstrategiaRecoleccion(IEstrategiaRecoleccion nuevaEstrategia) {
        this.estrategiaRecoleccion = nuevaEstrategia;
    }

    public void setTieneEscudo(boolean tieneEscudo) { this.tieneEscudo = tieneEscudo; }
    public boolean tieneEscudo() { return this.tieneEscudo; }


}
