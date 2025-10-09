package puppy.code;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Gestor de los objetos que caen.
 * Actúa como el Contexto que utiliza el ObjetoLluviosoAbstracto. (Cumple GM1.4)
 */
public class GestorGotas implements Desechable {

    // ATRIBUTOS
    private Array<ObjetoLluviosoAbstracto> objetosLluviosos;

    private long tiempoUltimaGota;
    private Texture texturaGotaBuena;
    private Texture texturaGotaMala;
    private Sound sonidoGota;
    private Music musicaLluvia;

    // ATRIBUTOS NUEVOS PARA NIVELES > 1
    private Texture texturaRoca;
    private Texture texturaEscudo;


    // CONSTRUCTOR: Ahora debe recibir las nuevas texturas
    public GestorGotas(Texture texBuena, Texture texMala, Sound sonido, Music musica, Texture texRoca, Texture texEscudo) {
        this.musicaLluvia = musica;
        this.sonidoGota = sonido;
        this.texturaGotaBuena = texBuena;
        this.texturaGotaMala = texMala;
        // Almacenar las nuevas texturas
        this.texturaRoca = texRoca;
        this.texturaEscudo = texEscudo;
    }

    // CREACIÓN
    public void crear() {
        this.objetosLluviosos = new Array<ObjetoLluviosoAbstracto>();
        // NOTA: crearObjetoLluvioso() no se llama aquí, sino en actualizarMovimiento.
        this.musicaLluvia.setLooping(true);
        this.musicaLluvia.play();
    }

    // LÓGICA DE FÁBRICA: Decide qué objeto crear basado en el nivel
    private void crearObjetoLluvioso(GestorNiveles gestor) { // MÉTODO MODIFICADO: AHORA RECIBE EL GESTOR
        float posicionX = (float)MathUtils.random(0, 736);
        int nivelActual = gestor.getNivelActual();

        ObjetoLluviosoAbstracto nuevoObjeto = null;

        // LÓGICA DE CREACIÓN BASADA EN EL NIVEL
        if (nivelActual == 1) {
            // Nivel 1: Solo Gota Buena y Gota Mala (Probabilidad 70/30)
            if (MathUtils.random(1, 10) < 3) {
                nuevoObjeto = new GotaMala(this.texturaGotaMala, posicionX);
            } else {
                nuevoObjeto = new GotaBuena(this.texturaGotaBuena, posicionX);
            }
        } else if (nivelActual == 2) {
            // Nivel 2: Introduce Roca y Escudo
            int probabilidad = MathUtils.random(1, 10);

            if (probabilidad < 2) {
                // Roca (Daño Doble) - 10% de probabilidad
                nuevoObjeto = new Roca(this.texturaRoca, posicionX);
            } else if (probabilidad == 2) {
                // Escudo (Bonificación) - 10% de probabilidad
                nuevoObjeto = new Escudo(this.texturaEscudo, posicionX);
            } else if (probabilidad < 5) {
                // Gota Mala - 20% de probabilidad
                nuevoObjeto = new GotaMala(this.texturaGotaMala, posicionX);
            } else {
                // Gota Buena - 60% de probabilidad
                nuevoObjeto = new GotaBuena(this.texturaGotaBuena, posicionX);
            }
        }
        // TODO: Implementar lógica para Nivel 3, 4, 5...

        // Añadir el objeto si se creó
        if (nuevoObjeto != null) {
            this.objetosLluviosos.add(nuevoObjeto);
        }

        this.tiempoUltimaGota = TimeUtils.nanoTime();
    }

    // ACTUALIZACIÓN DE MOVIMIENTO Y COLISIONES
    public void actualizarMovimiento(ReceptorAbstracto receptor, GestorNiveles gestorNiveles) {

        float factorVelocidad = 1.0f + (gestorNiveles.getNivelActual() - 1) * 0.2f;
        int nivelActual = gestorNiveles.getNivelActual();

        // Generar nueva gota: Aumenta la frecuencia de aparición con el nivel
        if (TimeUtils.nanoTime() - this.tiempoUltimaGota > 1000000000L / nivelActual) {
            this.crearObjetoLluvioso(gestorNiveles); // LLAMADA CORREGIDA
        }

        // Iterar y actualizar todos los objetos
        for(int i = 0; i < this.objetosLluviosos.size; ++i) {
            ObjetoLluviosoAbstracto objeto = this.objetosLluviosos.get(i);

            // Llama al método actualizado que recibe el factor de velocidad
            objeto.actualizar(factorVelocidad);

            // 1. Eliminar si está fuera de pantalla
            if (objeto.estaFueraDePantalla()) {
                this.objetosLluviosos.removeIndex(i);
                --i;
                continue;
            }

            // 2. Chequeo de colisión
            if (objeto.obtenerLimites().overlaps(receptor.getArea())) {

                // POLIMORFISMO
                objeto.aplicarEfecto(receptor, gestorNiveles);

                this.sonidoGota.play();

                this.objetosLluviosos.removeIndex(i);
                --i;
            }
        }
    }
    // ----------------------------------------------------------------------------------

    // DIBUJO POLIMÓRFICO
    public void actualizarDibujoLluvia(SpriteBatch batch) {
        for(ObjetoLluviosoAbstracto objeto : this.objetosLluviosos) {
            objeto.dibujar(batch);
        }
    }

    // LIBERACIÓN DE RECURSOS (GM1.5)
    @Override
    public void liberarRecursos() {
        this.sonidoGota.dispose();
        this.musicaLluvia.dispose();
        this.texturaGotaBuena.dispose();
        this.texturaGotaMala.dispose();
        // Liberar las nuevas texturas cargadas
        this.texturaRoca.dispose();
        this.texturaEscudo.dispose();
    }
}
