package Gestores;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import interfaces.IDibujable;
import interfaces.IEstrategiaRecoleccion;
import Estrategias.EstrategiaNormal;

public abstract class ReceptorAbstracto implements IDibujable {

    protected IEstrategiaRecoleccion estrategiaRecoleccion = new EstrategiaNormal();
    protected Rectangle limites;
    protected Texture imagen;
    protected Sound sonidoHerido;
    protected int vidas = 3;
    protected int puntos = 0;
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
    }

    public abstract void crear();

    public void dañar(GestorNiveles gestor) {
        if (tieneEscudo) {
            tieneEscudo = false;
            return;
        }
        if (vidas > 0) {
            vidas--;
            int penalizacion = gestor.obtenerPenalizacionPorNivel();
            puntos -= penalizacion;
            if (puntos < 0) puntos = 0;
            herido = true;
            tiempoHerido = tiempoHeridoMax;
            sonidoHerido.play();
        }
    }

    public boolean isGameOver() {
        return vidas <= 0;
    }

    public boolean estaHerido() {
        return herido;
    }

    public void actualizarMovimiento(boolean permitirVertical) {
        float factor = GestorTiempo.getInstancia().getFactorVelocidadGlobal();
        float velocidadReal = VELOCIDAD_BASE * factor;

        float nuevaX = limites.x;
        if (com.badlogic.gdx.Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.LEFT)) nuevaX -= velocidadReal * com.badlogic.gdx.Gdx.graphics.getDeltaTime();
        if (com.badlogic.gdx.Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.RIGHT)) nuevaX += velocidadReal * com.badlogic.gdx.Gdx.graphics.getDeltaTime();

        float nuevaY = limites.y;
        if (permitirVertical) {
            if (com.badlogic.gdx.Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.UP)) nuevaY += velocidadReal * com.badlogic.gdx.Gdx.graphics.getDeltaTime();
            if (com.badlogic.gdx.Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.DOWN)) nuevaY -= velocidadReal * com.badlogic.gdx.Gdx.graphics.getDeltaTime();
        }

        limites.x = Math.max(0, Math.min(nuevaX, 800 - ANCHO_RECEPTOR));
        limites.y = Math.max(0, Math.min(nuevaY, 480 - ALTO_RECEPTOR));
    }

    public void dibujar(SpriteBatch batch) {
        batch.draw(imagen, limites.x, limites.y, ANCHO_RECEPTOR, ALTO_RECEPTOR);
    }

    public int getVidas() { return vidas; }
    public int getPuntos() { return puntos; }
    public Rectangle getArea() { return limites; }
    public boolean tieneEscudo() { return tieneEscudo; }


    public void setTieneEscudo(boolean b) { this.tieneEscudo = b; }
    public void setPuntos(int nuevosPuntos) { this.puntos = nuevosPuntos; }
    public void setVidas(int nuevasVidas) { this.vidas = nuevasVidas; }

    public void sumarPuntos(int puntajeBase) {
        int puntosFinales = estrategiaRecoleccion.sumarPuntos(puntajeBase);
        this.puntos += puntosFinales;
    }

    public void setEstrategiaRecoleccion(IEstrategiaRecoleccion nueva) {
        this.estrategiaRecoleccion = nueva;
    }
}
