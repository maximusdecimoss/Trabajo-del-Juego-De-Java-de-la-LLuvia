package puppy.code;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

// ¡CORRECCIÓN CRÍTICA DE IMPORTS!
// Asegúrate de que las rutas de los receptores sean correctas si están en un subpaquete.
// Asumo que todos están en el paquete principal 'puppy.code'.
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
    private final int PUNTOS_POR_NIVEL = 100;
    private final int META_PUNTOS_TOTAL = 15000; // Constante de Victoria

    // Atributos de assets necesarios para crear los receptores
    private final Array<Texture> texturasReceptor;
    private final Sound sonidoHerido;


    public GestorNiveles(Array<Texture> texturas, Sound sonidoHerido) {
        this.texturasReceptor = texturas;
        this.sonidoHerido = sonidoHerido;
    }

    // MÉTODO ACTUALIZADO: Maneja la lógica de avance y victoria
    public ReceptorAbstracto actualizarEstado(int puntosActuales, ReceptorAbstracto jugadorActual) {

        // Si ya ganó, devolvemos el jugador sin hacer nada más.
        if (ganoJuego(puntosActuales)) {
            return jugadorActual;
        }

        int nivelAnterior = this.nivelActual;

        // Lógica para verificar si se debe avanzar de nivel
        if (puntosActuales >= this.nivelActual * PUNTOS_POR_NIVEL) {
            this.avanzarNivel();
        }

        // Si el nivel cambió, liberamos el anterior y creamos el nuevo receptor.
        if (this.nivelActual > nivelAnterior) {
            // Liberar recursos del personaje anterior (GM1.5)
            ((Desechable)jugadorActual).liberarRecursos();

            // Crear el nuevo receptor para el nuevo nivel
            ReceptorAbstracto nuevoReceptor = this.crearReceptor(this.nivelActual);

            // Transferir el puntaje y vidas al nuevo receptor
            nuevoReceptor.setPuntos(puntosActuales);
            nuevoReceptor.setVidas(jugadorActual.getVidas()); // Se asume que setVidas existe

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
        Texture texturaNivel = texturasReceptor.get(nivel - 1);

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
                break; // Asegúrate que el break está aquí para evitar fallos
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
