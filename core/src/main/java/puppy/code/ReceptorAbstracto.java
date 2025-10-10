// Archivo: ReceptorAbstracto.java
package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

/**
 * Clase padre abstracta para todos los receptores (Tarro, Paraguas, Vikingo, etc.).
 * Contiene la lógica común de estadísticas, movimiento y colisión.
 * (Cumple GM1.4)
 */
public abstract class ReceptorAbstracto implements Desechable {

    // LÓGICA COMÚN QUE ERA DE TARRO
    protected Rectangle limites;
    protected Texture imagen;
    protected Sound sonidoHerido;

    protected int vidas = 3;
    protected int puntos = 0;
    protected int velx = 400; // La velocidad base del movimiento
    protected boolean herido = false;
    protected int tiempoHeridoMax = 50;
    protected int tiempoHerido;
    protected boolean tieneEscudo = false;

    // Nueva constante para el movimiento del jugador
    protected final float VELOCIDAD_BASE = 400f;

    public ReceptorAbstracto(Texture tex, Sound ss) {
        this.imagen = tex;
        this.sonidoHerido = ss;
    }

    // MÉTODOS ABSTRACTOS
    public abstract void crear();

    // MÉTODOS CONCRETOS (Lógica compartida por todos los receptores)

    public void dañar(GestorNiveles gestor) {

        if (this.tieneEscudo) {
            this.tieneEscudo = false;
            return; // El daño es ignorado
        }

        if(this.vidas > 0) {
            --this.vidas;

            // GM1.7: Aplicar penalización progresiva de puntos
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

    // CORRECCIÓN: 'puntos' sí existe (declarado arriba).
    // El comentario de error estaba en tu código fuente, no en la clase.
    public void setPuntos(int nuevosPuntos) {
        this.puntos = nuevosPuntos;
    }

    public boolean isGameOver() {
        return this.vidas <= 0;
    }

    public boolean estaHerido() {
        return herido;
    }

    // MOVIMIENTO: Integración del Singleton (GM2.1)
    public void actualizarMovimiento() {

        // 1. Obtener el factor de velocidad del Singleton (Hueso/Lodo)
        float factorSingleton = GestorTiempo.getInstancia().getFactorVelocidadGlobal();

        // 2. Calcular la velocidad real (VELOCIDAD_BASE * factorSingleton)
        float velocidadReal = VELOCIDAD_BASE * factorSingleton;

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            // Usa la velocidad real, que puede ser 0.5x, 1.0x o 1.5x la base
            this.limites.x -= velocidadReal * Gdx.graphics.getDeltaTime();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            // Usa la velocidad real
            this.limites.x += velocidadReal * Gdx.graphics.getDeltaTime();
        }

        // Que no se salga de los bordes izq y der (Lógica de 800x480)
        if(this.limites.x < 0) this.limites.x = 0;
        if(this.limites.x > 800 - 64) this.limites.x = 800 - 64;
    }


    public void dibujar(SpriteBatch batch) {
        // ... (Tu lógica de dibujo sigue siendo correcta)
        final float ANCHO = 64;
        final float ALTO = 64;

        batch.draw(
            this.imagen,
            this.limites.x,
            this.limites.y,
            ANCHO,
            ALTO
        );
    }

    // Getters y Setters
    public int getVidas() { return this.vidas; }
    public int getPuntos() { return this.puntos; }
    public Rectangle getArea() { return this.limites; }
    public void sumarPuntos(int pp) { this.puntos += pp; }
    public void setTieneEscudo(boolean tieneEscudo) { this.tieneEscudo = tieneEscudo; } // Setter necesario para Escudo

    // Implementación de la interfaz Desechable (GM1.5)
    public abstract void liberarRecursos();
}
