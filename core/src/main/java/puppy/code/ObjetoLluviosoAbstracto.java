// Archivo: ObjetoLluviosoAbstracto.java

package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Clase padre abstracta para todos los ítems que caen (Gotas, Rocas, Escudos, etc.).
 * Define la lógica común de caída, límites y dibujo.
 * (Cumple GM1.4)
 */
public abstract class ObjetoLluviosoAbstracto {

    // Atributos protegidos para que las clases hijas puedan acceder
    protected Rectangle limites;
    protected Texture textura;
    protected float velocidadCaida = 300.0F;

    // El objeto constructor
    public ObjetoLluviosoAbstracto(Texture textura, float posicionX) {
        this.textura = textura;
        this.limites = new Rectangle(posicionX, 480.0F, 64.0F, 64.0F);
    }

    // MÉTODO ABSTRACTO (GM1.6): Ahora recibe el GestorNiveles para la penalización progresiva
    public abstract void aplicarEfecto(ReceptorAbstracto receptor, GestorNiveles gestor);

    // Lógica CONCRETA: Actualiza la posición del objeto (recibe factor de velocidad del GestorGotas)
    public void actualizar(float factorVelocidad) {
        // Multiplicamos la velocidad base por el factor de dificultad del nivel
        this.limites.y -= this.velocidadCaida * factorVelocidad * Gdx.graphics.getDeltaTime();
    }

    // Lógica CONCRETA: Dibuja el objeto
    public void dibujar(SpriteBatch lote) {
        lote.draw(this.textura, this.limites.x, this.limites.y);
    }

    // Getters
    public Rectangle obtenerLimites() {
        return limites;
    }

    // Check si el objeto ya salió de la pantalla
    public boolean estaFueraDePantalla() {
        return this.limites.y + 64.0F < 0.0F;
    }
}
