package puppy.code;

import Gestores.GestorNiveles;
import Gestores.GestorRecursos;
import Gestores.ReceptorAbstracto;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import interfaces.IEstrategiaMovimiento;
import puppy.code.EstrategiaCaidaVertical;

/**
 * Clase padre abstracta para todos los objetos que caen.
 * PATRÓN TEMPLATE METHOD (GM2.2) aplicado aquí.
 */
public abstract class ObjetoLluviosoAbstracto {

    protected IEstrategiaMovimiento estrategiaMovimiento;
    protected Rectangle limites;
    protected Texture textura;
    protected float velocidad = 200f;

    public ObjetoLluviosoAbstracto(Texture tex, float posicionX) {
        this.textura = tex;
        this.limites = new Rectangle(posicionX, 480, 48, 48);
        this.estrategiaMovimiento = new EstrategiaCaidaVertical();
    }

    public void setEstrategiaMovimiento(IEstrategiaMovimiento nuevaEstrategia) {
        this.estrategiaMovimiento = nuevaEstrategia;
    }

    public void actualizar(float factorVelocidad) {
        estrategiaMovimiento.mover(limites, velocidad, factorVelocidad);
    }

    public void dibujar(SpriteBatch batch) {
        batch.draw(textura, limites.x, limites.y, limites.width, limites.height);
    }

    public boolean estaFueraDePantalla() {
        return limites.y + limites.height < 0;
    }

    public Rectangle obtenerLimites() {
        return limites;
    }

    // ====================== TEMPLATE METHOD (GM2.2) ======================
    public final void procesarColision(ReceptorAbstracto receptor, GestorNiveles gestor) {
        reproducirSonidoColision();
        aplicarEfectoComun(receptor, gestor);
        aplicarEfectoEspecifico(receptor, gestor);
    }

    private void reproducirSonidoColision() {
        GestorRecursos.getInstancia().getSDrop().play();
    }

    protected void aplicarEfectoComun(ReceptorAbstracto receptor, GestorNiveles gestor) {
        // Hook para lógica común futura (por ejemplo: todos suman 5 puntos base)
    }

    protected abstract void aplicarEfectoEspecifico(ReceptorAbstracto receptor, GestorNiveles gestor);
    // =====================================================================
}
