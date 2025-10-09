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
        // Inicializa el área de colisión y la posición de inicio (arriba)
        this.limites = new Rectangle(posicionX, 480.0F, 64.0F, 64.0F);
    }

    // MÉTODO ABSTRACTO: Cada objeto DEBE definir su efecto al colisionar.
    // (Cumple GM1.6: Polimorfismo)
    public abstract void aplicarEfecto(Tarro receptor);

    // Lógica CONCRETA: Actualiza la posición del objeto (la caída)
    public void actualizar() {
        this.limites.y -= this.velocidadCaida * Gdx.graphics.getDeltaTime();
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
