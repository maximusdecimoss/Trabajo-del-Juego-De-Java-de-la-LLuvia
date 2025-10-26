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
    protected Texture textura;
    protected float velocidad = 200;

    // CONSTRUCTOR
    public ObjetoLluviosoAbstracto(Texture tex, float posicionX) {
        this.textura = tex;
        this.limites = new Rectangle();
        this.limites.x = posicionX;
        this.limites.y = 480;
        this.limites.width = 48;
        this.limites.height = 48;
    }

    // PASO ABSTRACTO (Paso variable del Template Method)
    protected abstract void liberarRecursosUnicos();

    // TEMPLATE METHOD (GM2.2): Esqueleto del algoritmo de destrucción
    public final void destruir() {
        // PASO 1: Limpieza de variables (Paso Fijo)
        this.limites = null;

        // PASO 2: Liberación de recursos únicos de la subclase (Paso Variable)
        this.liberarRecursosUnicos();
    }


    // MÉTODO ABSTRACTO (El polimorfismo clave)
    public abstract void aplicarEfecto(ReceptorAbstracto receptor, GestorNiveles gestor);

    // MÉTODO CONCRETO (Lógica de movimiento compartida)
    public void actualizar(float factorVelocidad) {
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
    // El método requerido por la interfaz DELEGA al Template Method para la destrucción.
    @Override
    public void liberarRecursos() {
        // Esto asegura que, si alguien llama a liberarRecursos(), se ejecuta el Template.
        this.destruir();
    }
}
