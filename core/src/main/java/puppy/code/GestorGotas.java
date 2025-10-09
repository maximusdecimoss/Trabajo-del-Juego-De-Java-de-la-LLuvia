// Archivo: Lluvia.java (Refactorizado)
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
    // 1. Array principal: Ahora almacena el tipo abstracto.
    private Array<ObjetoLluviosoAbstracto> objetosLluviosos;

    private long tiempoUltimaGota;
    private Texture texturaGotaBuena;
    private Texture texturaGotaMala;
    private Sound sonidoGota;
    private Music musicaLluvia;


    // CONSTRUCTOR
    // Recibe los assets y los almacena (responsabilidad del gestor)
    public GestorGotas(Texture texBuena, Texture texMala, Sound sonido, Music musica) {
        this.musicaLluvia = musica;
        this.sonidoGota = sonido;
        this.texturaGotaBuena = texBuena;
        this.texturaGotaMala = texMala;
    }

    // CREACIÓN
    public void crear() {
        // Inicializamos el array principal con el tipo abstracto
        this.objetosLluviosos = new Array<ObjetoLluviosoAbstracto>();
        this.crearObjetoLluvioso();
        this.musicaLluvia.setLooping(true);
        this.musicaLluvia.play();
    }

    private void crearObjetoLluvioso() {
        float posicionX = (float)MathUtils.random(0, 736);

        // LÓGICA DE GENERACIÓN (Nivel 1: Gota Buena y Mala)
        if (MathUtils.random(1, 10) < 3) {
            // Creamos una Gota Mala
            this.objetosLluviosos.add(new GotaMala(this.texturaGotaMala, posicionX));
        } else {
            // Creamos una Gota Buena
            this.objetosLluviosos.add(new GotaBuena(this.texturaGotaBuena, posicionX));
        }

        this.tiempoUltimaGota = TimeUtils.nanoTime();
    }

    // ACTUALIZACIÓN DE MOVIMIENTO Y COLISIONES (DEMOSTRACIÓN DE POLIMORFISMO GM1.6)
    public void actualizarMovimiento(Tarro tarro) {
        // Generar nueva gota
        if (TimeUtils.nanoTime() - this.tiempoUltimaGota > 500000000L) { // Reducido para que la lógica de caída sea más rápida
            this.crearObjetoLluvioso();
        }

        // Iterar y actualizar todos los objetos
        for(int i = 0; i < this.objetosLluviosos.size; ++i) {
            ObjetoLluviosoAbstracto objeto = this.objetosLluviosos.get(i);

            objeto.actualizar(); // Lógica de caída heredada (GM1.4)

            // 1. Eliminar si está fuera de pantalla
            if (objeto.estaFueraDePantalla()) {
                this.objetosLluviosos.removeIndex(i);
                --i; // Ajustamos el índice
                continue;
            }

            // 2. Chequeo de colisión
            if (objeto.obtenerLimites().overlaps(tarro.getArea())) {

                // *** POLIMORFISMO (GM1.6) ***
                // La llamada es la misma para todos, pero GotaBuena suma y GotaMala daña.
                objeto.aplicarEfecto(tarro);

                this.sonidoGota.play(); // El sonido es común a todas las colisiones

                // Lógica común: el objeto desaparece tras colisionar
                this.objetosLluviosos.removeIndex(i);
                --i; // Ajustamos el índice
            }
        }
    }

    // DIBUJO POLIMÓRFICO
    public void actualizarDibujoLluvia(SpriteBatch batch) {
        for(ObjetoLluviosoAbstracto objeto : this.objetosLluviosos) {
            objeto.dibujar(batch); // La lógica de dibujo es heredada
        }
    }

    // LIBERACIÓN DE RECURSOS (GM1.5)
    @Override
    public void liberarRecursos() {
        // Asegúrate de que las texturas, sonido y música se liberen
        this.sonidoGota.dispose();
        this.musicaLluvia.dispose();
        this.texturaGotaBuena.dispose();
        this.texturaGotaMala.dispose();
    }
}
