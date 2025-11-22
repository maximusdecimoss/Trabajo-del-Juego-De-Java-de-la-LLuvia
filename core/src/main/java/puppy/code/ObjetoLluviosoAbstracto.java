// ObjetoLluviosoAbstracto.java
package puppy.code;

import Estrategias.EstrategiaCaidaVertical;
import Gestores.GestorNiveles;
import Gestores.GestorRecursos;
import Gestores.ReceptorAbstracto;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import interfaces.IEstrategiaMovimiento;

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

    public void setEstrategiaMovimiento(IEstrategiaMovimiento nueva) {
        this.estrategiaMovimiento = nueva;
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

    public final void procesarColision(ReceptorAbstracto receptor, GestorNiveles gestor) {
        pasoGenericoSonido();
        pasoEspecificoEfecto(receptor, gestor);
    }

    private void pasoGenericoSonido() {
        GestorRecursos.getInstancia().getSDrop().play();
    }

    protected abstract void pasoEspecificoEfecto(ReceptorAbstracto receptor, GestorNiveles gestor);
}
