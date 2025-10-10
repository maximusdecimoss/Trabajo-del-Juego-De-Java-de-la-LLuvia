package puppy.code;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

// IMPORTS NECESARIOS PARA PODER INSTANCIAR LOS PERSONAJES


/**
 * Gestiona el estado del juego, el nivel actual y proporciona la lógica para
 * la penalización progresiva (GM1.7).
 */
public class GestorNiveles {

    private int nivelActual = 1;
    private final int MAX_NIVELES = 5;
    private final int PUNTOS_POR_NIVEL = 100; // Puntos necesarios para avanzar

    // Atributos de assets necesarios para crear los receptores
    private final Array<Texture> texturasReceptor;
    private final Sound sonidoHerido;

    // El receptor actual, se usa internamente para la lógica
    private ReceptorAbstracto receptorActual;

    // Constructor: Recibe todos los assets de los 5 receptores
    public GestorNiveles(Array<Texture> texturas, Sound sonidoHerido) {
        this.texturasReceptor = texturas;
        this.sonidoHerido = sonidoHerido;
    }

    // MÉTODO MODIFICADO (Ahora recibe y devuelve el receptor para permitir el cambio de personaje)
    public ReceptorAbstracto actualizarEstado(int puntosActuales, ReceptorAbstracto jugadorActual) {

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
            this.receptorActual = this.crearReceptor(this.nivelActual);

            // El nuevo receptor mantiene el puntaje del anterior
            this.receptorActual.setPuntos(puntosActuales);

            // Retornar el nuevo receptor
            return this.receptorActual;
        }

        // Si el nivel NO cambió, retornamos el mismo receptor
        return jugadorActual;
    }

    private void avanzarNivel() {
        if (this.nivelActual < MAX_NIVELES) {
            this.nivelActual++;
            // TODO: Agregar efecto de sonido/música de avance de nivel
        }
    }

    // LÓGICA DE INSTANCIACIÓN DE RECEPTORES (Fábrica de Receptores)
    public ReceptorAbstracto crearReceptor(int nivel) {
        Texture texturaNivel = texturasReceptor.get(nivel - 1); // Array index = nivel - 1

        switch (nivel) {
            case 1:
                // Tarro: El receptor base, vida normal, velocidad normal
                this.receptorActual = new Tarro(texturaNivel, this.sonidoHerido);
                break;
            case 2:
                // Paraguas: Puede tener más velocidad o menos vida
                this.receptorActual = new Paraguas(texturaNivel, this.sonidoHerido);
                break;
            case 3:
                // Persona: Podría tener atributos neutros o dificultad media
                this.receptorActual = new Persona(texturaNivel, this.sonidoHerido);
                break;
            case 4:
                // Perro: Podría ser más rápido pero tener menos vidas
                this.receptorActual = new Perro(texturaNivel, this.sonidoHerido);
                break;
            case 5:
                // Vikingo: El más resistente, pero quizás más lento
                this.receptorActual = new Vikingo(texturaNivel, this.sonidoHerido);
                break;
            default:
                this.receptorActual = new Tarro(texturasReceptor.get(0), this.sonidoHerido);
        }
        this.receptorActual.crear();
        return this.receptorActual;
    }

    // Getters
    public int getNivelActual() { return nivelActual; }

    // GM1.7: Penalización Progresiva
    // Define cuántos puntos restar por daño basado en el nivel
    public int obtenerPenalizacionPorNivel() {
        // Penalización: Nivel 1 resta 10, Nivel 5 resta 50 (Nivel * 10)
        return this.nivelActual * 10;
    }
}
