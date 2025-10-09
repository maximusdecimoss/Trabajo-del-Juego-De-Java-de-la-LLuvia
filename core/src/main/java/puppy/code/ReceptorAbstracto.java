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
    protected Rectangle limites; // Renombramos 'bucket' a 'limites' para abstracción
    protected Texture imagen; // Renombramos 'bucketImage' a 'imagen'
    protected Sound sonidoHerido;

    protected int vidas = 3;
    protected int puntos = 0;
    protected int velx = 400;
    protected boolean herido = false;
    protected int tiempoHeridoMax = 50;
    protected int tiempoHerido;
    // La penalización progresiva de puntos se implementará en dañar()

    public ReceptorAbstracto(Texture tex, Sound ss) {
        this.imagen = tex;
        this.sonidoHerido = ss;
    }

    // MÉTODOS ABSTRACTOS: Lo que cada receptor hace de forma única
    // Aunque el movimiento y dibujo son similares, dejaremos el constructor/crear como abstracto.
    public abstract void crear(); // Cada subclase define sus límites/posición inicial

    // MÉTODOS CONCRETOS (Lógica compartida por todos los receptores)

    public void dañar(GestorNiveles gestor) {
        if(this.vidas > 0) {
            --this.vidas;

            // GM1.7: Aplicar penalización progresiva de puntos
            int penalizacion = gestor.obtenerPenalizacionPorNivel();
            this.puntos -= penalizacion;

            // Asegurarse de que los puntos no sean negativos (opcional)
            if (this.puntos < 0) {
                this.puntos = 0;
            }

            this.herido = true;
            this.tiempoHerido = this.tiempoHeridoMax;
            this.sonidoHerido.play();
        }
    }

    public void setPuntos(int nuevosPuntos) {
        this.puntos = nuevosPuntos; // ERROR: puntos no existe aquí
    }
    public boolean isGameOver() {
        return this.vidas <= 0;
    }

    public boolean estaHerido() {
        return herido;
    }

    // Movimiento y lógica de invencibilidad (tomado de Tarro.java)
    public void actualizarMovimiento() {
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) this.limites.x -= this.velx * Gdx.graphics.getDeltaTime();
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) this.limites.x += this.velx * Gdx.graphics.getDeltaTime();

        // Que no se salga de los bordes izq y der (Lógica de 800x480)
        if(this.limites.x < 0) this.limites.x = 0;
        if(this.limites.x > 800 - 64) this.limites.x = 800 - 64;
    }


    public void dibujar(SpriteBatch batch) {
        // Definimos un tamaño constante para el Receptor (64x64)
        final float ANCHO = 64;
        final float ALTO = 64;

        batch.draw(
            this.imagen, // <-- ¡CORREGIDO! Usar 'imagen' en lugar de 'textura'
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

    // Implementación de la interfaz Desechable (GM1.5)
    // Se deja abstracto aquí para que cada subclase decida si también debe liberar recursos

    public abstract void liberarRecursos();
}
