package puppy.code;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

// IMPORTS NECESARIOS PARA PODER INSTANCIAR LOS PERSONAJES
import puppy.code.ReceptorAbstracto;
import puppy.code.Desechable;
import puppy.code.Tarro;
import puppy.code.Paraguas;
import puppy.code.Persona;
import puppy.code.Perro;
import puppy.code.Vikingo;


/**
 * Gestiona el estado del juego, el nivel actual y proporciona la lógica para
 * la penalización progresiva (GM1.7) y la victoria (15,000 puntos).
 */
public class GestorNiveles {

    private int nivelActual = 1;
    private final int MAX_NIVELES = 5;

    // TABLA DE METAS FIJAS: Puntos necesarios para ENTRAR al siguiente nivel.
    // Índice 0: Meta para Nivel 2 (150)
    // Índice 1: Meta para Nivel 3 (1000)
    // Índice 2: Meta para Nivel 4 (5000)
    // Índice 3: Meta para Nivel 5 (10000)
    private final int[] METAS_NIVEL = {150, 1000, 5000, 10000};

    // CONDICIÓN DE VICTORIA FINAL
    private final int META_PUNTOS_TOTAL = 15000;

    // Atributos de assets necesarios para crear los receptores
    private final Array<Texture> texturasReceptor;
    private final Sound sonidoHerido;


    public GestorNiveles(Array<Texture> texturas, Sound sonidoHerido) {
        this.texturasReceptor = texturas;
        this.sonidoHerido = sonidoHerido;
    }

    // MÉTODO MODIFICADO: Maneja la lógica de avance y victoria
    public ReceptorAbstracto actualizarEstado(int puntosActuales, ReceptorAbstracto jugadorActual) {

        // Si ya ganó, devolvemos el jugador sin hacer nada más.
        if (ganoJuego(puntosActuales)) {
            return jugadorActual;
        }

        int nivelAnterior = this.nivelActual;

        // LÓGICA DE PROGRESIÓN POR META FIJA
        // Solo verificamos si no estamos en el Nivel Máximo
        if (this.nivelActual < MAX_NIVELES) {

            // La meta se busca en el array: array[nivelActual - 1]
            // (Ej: Nivel 1 -> array[0] = 150 puntos)
            int metaRequerida = METAS_NIVEL[this.nivelActual - 1];

            if (puntosActuales >= metaRequerida) {
                this.avanzarNivel();
            }
        }

        // Si el nivel cambió, liberamos el anterior y creamos el nuevo receptor.
        if (this.nivelActual > nivelAnterior) {
            // Liberar recursos del personaje anterior (GM1.5)
            ((Desechable)jugadorActual).liberarRecursos();

            // Crear el nuevo receptor para el nuevo nivel
            ReceptorAbstracto nuevoReceptor = this.crearReceptor(this.nivelActual);

            // Transferir el puntaje y vidas al nuevo receptor
            nuevoReceptor.setPuntos(puntosActuales);
            nuevoReceptor.setVidas(jugadorActual.getVidas());

            return nuevoReceptor;
        }

        return jugadorActual;
    }

    private void avanzarNivel() {
        if (this.nivelActual < MAX_NIVELES) {
            this.nivelActual++;
        }
    }

    // LÓGICA DE INSTANCIACIÓN DE RECEPTORES (Factory Method Pattern)
    public ReceptorAbstracto crearReceptor(int nivel) {
        Texture texturaNivel = this.texturasReceptor.get(nivel - 1); // <-- CORRECCIÓN

        ReceptorAbstracto nuevoReceptor;

        switch (nivel) {
            case 1:
                nuevoReceptor = new Tarro(texturaNivel, this.sonidoHerido);
                break;
            case 2:
                nuevoReceptor = new Paraguas(texturaNivel, this.sonidoHerido);
                break;
            case 3:
                nuevoReceptor = new Persona(texturaNivel, this.sonidoHerido);
                break;
            case 4:
                nuevoReceptor = new Perro(texturaNivel, this.sonidoHerido);
                break;
            case 5:
                nuevoReceptor = new Vikingo(texturaNivel, this.sonidoHerido);
                break;
            default:
                nuevoReceptor = new Tarro(this.texturasReceptor.get(0), this.sonidoHerido);
                break;
        }

        nuevoReceptor.crear();
        return nuevoReceptor;
    }

    // Getters
    public int getNivelActual() { return nivelActual; }

    // GM1.7: Penalización Progresiva
    public int obtenerPenalizacionPorNivel() {
        return this.nivelActual * 10;
    }

    // Verifica la condición de victoria
    public boolean ganoJuego(int puntosActuales) {
        return puntosActuales >= META_PUNTOS_TOTAL;
    }
}
