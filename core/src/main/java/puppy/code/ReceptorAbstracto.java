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

    // PATRÓN STRATEGY (GM2.3) - Atributo del Contexto
    protected IEstrategiaRecoleccion estrategiaRecoleccion;

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

        // Inicialización del Strategy: Usa la estrategia por defecto
        this.estrategiaRecoleccion = new EstrategiaNormal();
    }

    // **********************************************
    // MÉTODOS DE LA LÓGICA DE TRANSFERENCIA Y CREACIÓN
    // **********************************************

    public void setPuntos(int nuevosPuntos) {
        this.puntos = nuevosPuntos;
    }

    // ¡MÉTODO AÑADIDO PARA LA CORRECCIÓN!
    /**
     * Settea el número de vidas. Usado por GestorNiveles para transferir el progreso.
     */
    public void setVidas(int nuevasVidas) {
        this.vidas = nuevasVidas;
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

    public boolean isGameOver() {
        // La condición para el fin del juego es que las vidas sean cero o menos
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
            this.limites.x -= velocidadReal * Gdx.graphics.getDeltaTime();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            this.limites.x += velocidadReal * Gdx.graphics.getDeltaTime();
        }

        // Que no se salga de los bordes izq y der (Lógica de 800x480)
        if(this.limites.x < 0) this.limites.x = 0;
        if(this.limites.x > 800 - 64) this.limites.x = 800 - 64;
    }


    public void dibujar(SpriteBatch batch) {
        // ... (Tu lógica de dibujo)
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

    // MÉTODO MODIFICADO (GM2.3): Usa la Estrategia para sumar puntos
    public void sumarPuntos(int puntajeBase) {
        // La estrategia calcula el puntaje final (1x, 2x, etc.)
        int puntosASumar = this.estrategiaRecoleccion.sumarPuntos(puntajeBase);
        this.puntos += puntosASumar;
    }

    // Nuevo Método (GM2.3): Permite cambiar la Estrategia en tiempo de ejecución
    public void setEstrategiaRecoleccion(IEstrategiaRecoleccion nuevaEstrategia) {
        this.estrategiaRecoleccion = nuevaEstrategia;
    }

    public void setTieneEscudo(boolean tieneEscudo) { this.tieneEscudo = tieneEscudo; }

    // Implementación de la interfaz Desechable (GM1.5)
    public abstract void liberarRecursos();
}
