// Archivo: ObjetoLluviosoAbstracto.java
package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Clase padre abstracta para todos los objetos que caen (Gotas, Rocas, Escudos, etc.).
 * Implementa el Patrón Template Method (GM2.2) para el proceso de destrucción.
 */
public abstract class ObjetoLluviosoAbstracto implements Desechable {

    // ATRIBUTOS PROTECTED
    protected Rectangle limites;
    protected Texture textura; // Usaremos 'textura' aquí para compatibilidad con las subclases
    protected float velocidad = 200; // Velocidad base

    // CONSTRUCTOR
    public ObjetoLluviosoAbstracto(Texture tex, float posicionX) {
        this.textura = tex;

        // Inicializar límites (usando un tamaño fijo, por ejemplo, 48x48)
        this.limites = new Rectangle();
        this.limites.x = posicionX;
        this.limites.y = 480; // Inicia arriba de la pantalla
        this.limites.width = 48;
        this.limites.height = 48;
    }

    // MÉTODO ABSTRACTO (El paso variable del Template Method)
    /**
     * Paso abstracto del Template Method: Las subclases implementan la liberación
     * de recursos que SOLO ellas poseen (ej. sonidos o texturas no compartidas).
     */
    protected abstract void liberarRecursosUnicos();

    // TEMPLATE METHOD (GM2.2): Esqueleto del algoritmo de destrucción
    /**
     * Define la secuencia final para eliminar un objeto del juego.
     * Es 'final' para que las subclases no puedan modificar la estructura del algoritmo.
     */
    public final void destruir() {
        // PASO 1: Limpieza del estado del objeto (Paso Fijo)
        this.limites = null;

        // PASO 2: Liberación de recursos únicos de la subclase (Paso Variable)
        this.liberarRecursosUnicos();

        // El paso de eliminación de la lista de GestorGotas se mantiene allí.
    }


    // MÉTODO ABSTRACTO (El polimorfismo clave)
    public abstract void aplicarEfecto(ReceptorAbstracto receptor, GestorNiveles gestor);

    // MÉTODO CONCRETO (Lógica de movimiento compartida)
    public void actualizar(float factorVelocidad) {
        // Mover hacia abajo
        this.limites.y -= this.velocidad * factorVelocidad * com.badlogic.gdx.Gdx.graphics.getDeltaTime();
    }

    // DIBUJO CONCRETO
    public void dibujar(SpriteBatch batch) {
        batch.draw(this.textura, this.limites.x, this.limites.y, this.limites.width, this.limites.height);
    }

    // UTILIDADES
    public boolean estaFueraDePantalla() {
        return this.limites.y + this.limites.height < 0;
    }

    public Rectangle obtenerLimites() {
        return this.limites;
    }

    // IMPLEMENTACIÓN DESECHABLE (GM1.5)
    // Este método ya no es necesario ya que usamos destruir() como template,
    // pero lo dejamos vacío para mantener la compatibilidad con Desechable.
    @Override
    public void liberarRecursos() {
        // La lógica de liberación se movió al Template Method: destruir()
    }
}
