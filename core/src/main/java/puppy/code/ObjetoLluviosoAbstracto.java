package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Clase padre abstracta para todos los objetos que caen.
 */
public abstract class ObjetoLluviosoAbstracto  {

    // PATRÓN STRATEGY (GM2.3): Atributo del Contexto
    protected IEstrategiaMovimiento estrategiaMovimiento;

    // ATRIBUTOS PROTECTED existentes
    protected Rectangle limites;
    protected Texture textura;
    protected float velocidad = 200;

    // CONSTRUCTOR: Inicializa la estrategia por defecto (Caída Vertical)
    public ObjetoLluviosoAbstracto(Texture tex, float posicionX) {
        this.textura = tex;
        // Inicializar límites...
        this.limites = new Rectangle();
        this.limites.x = posicionX;
        this.limites.y = 480;
        this.limites.width = 48;
        this.limites.height = 48;

        // ¡INICIALIZACIÓN DE LA ESTRATEGIA POR DEFECTO! (Strategy GM2.3)
        // Se asume que EstrategiaCaidaVertical ya existe.
        this.estrategiaMovimiento = new EstrategiaCaidaVertical();
    }


    public void setEstrategiaMovimiento(IEstrategiaMovimiento nuevaEstrategia) {
        this.estrategiaMovimiento = nuevaEstrategia;
    }



    public void actualizar(float factorVelocidad) {
        // DELEGA la tarea de mover a la Estrategia actual (GM2.3)
        this.estrategiaMovimiento.mover(this.limites, this.velocidad, factorVelocidad);
    }


    public abstract void aplicarEfecto(ReceptorAbstracto receptor, GestorNiveles gestor);


    public void dibujar(SpriteBatch batch) {
        batch.draw(this.textura, this.limites.x, this.limites.y, this.limites.width, this.limites.height);
    }


    public boolean estaFueraDePantalla() {
        // La lógica de salida de pantalla debe considerar las nuevas reglas de movimiento lateral
        // Pero para mantener la compatibilidad con el Template Method, la dejamos así.
        return this.limites.y + this.limites.height < 0;
    }

    public Rectangle obtenerLimites() {
        return this.limites;
    }


}
