// Archivo: ObjetoLluviosoAbstracto.java
package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Clase padre abstracta para todos los objetos que caen (Gotas, Rocas, Escudos, etc.).
 * (Cumple GM1.4)
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
    // El GestorGotas maneja las texturas, por lo que este método queda vacío.
    @Override
    public void liberarRecursos() {
        // No hace nada, el GestorGotas es el dueño de la textura
    }
}
