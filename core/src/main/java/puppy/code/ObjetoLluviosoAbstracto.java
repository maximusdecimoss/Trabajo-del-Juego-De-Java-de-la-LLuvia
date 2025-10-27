package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Clase padre abstracta para todos los objetos que caen.
 * Ahora actúa como el CONTEXTO del patrón Strategy para el movimiento (GM2.3).
 */
public abstract class ObjetoLluviosoAbstracto implements Desechable {

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

    // PASO ABSTRACTO (Paso variable del Template Method)
    protected abstract void liberarRecursosUnicos();

    // TEMPLATE METHOD (GM2.2): Esqueleto del algoritmo de destrucción
    public final void destruir() {
        // PASO 1: Limpieza de variables (Paso Fijo)
        this.limites = null;

        // PASO 2: Liberación de recursos únicos de la subclase (Paso Variable)
        this.liberarRecursosUnicos();
    }

    // NUEVO MÉTODO: Permite cambiar la Estrategia en tiempo de ejecución
    public void setEstrategiaMovimiento(IEstrategiaMovimiento nuevaEstrategia) {
        this.estrategiaMovimiento = nuevaEstrategia;
    }

    // MÉTODO CONCRETO (Lógica de movimiento compartida) - ¡MODIFICADO PARA DELEGAR!

    public void actualizar(float factorVelocidad) {
        // DELEGA la tarea de mover a la Estrategia actual (GM2.3)
        this.estrategiaMovimiento.mover(this.limites, this.velocidad, factorVelocidad);
    }

    // MÉTODO ABSTRACTO (El polimorfismo clave)
    public abstract void aplicarEfecto(ReceptorAbstracto receptor, GestorNiveles gestor);

    // DIBUJO CONCRETO
    public void dibujar(SpriteBatch batch) {
        batch.draw(this.textura, this.limites.x, this.limites.y, this.limites.width, this.limites.height);
    }

    // UTILIDADES
    public boolean estaFueraDePantalla() {
        // La lógica de salida de pantalla debe considerar las nuevas reglas de movimiento lateral
        // Pero para mantener la compatibilidad con el Template Method, la dejamos así.
        return this.limites.y + this.limites.height < 0;
    }

    public Rectangle obtenerLimites() {
        return this.limites;
    }

    // IMPLEMENTACIÓN DESECHABLE (GM1.5)
    @Override
    public void liberarRecursos() {
        this.destruir();
    }
}
