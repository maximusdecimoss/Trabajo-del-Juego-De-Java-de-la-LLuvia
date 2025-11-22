// Archivo: FabricaNivel4.java
package Estrategias;

import com.badlogic.gdx.graphics.Texture;
import interfaces.IFabricaObjetosLluviosos;
import objetosQueCaen.Hueso;
import objetosQueCaen.Lodo;
import puppy.code.ObjetoLluviosoAbstracto;

public class FabricaNivel4 implements IFabricaObjetosLluviosos {

    // Las texturas de Hueso y Lodo se almacenan aquí
    private final Texture texturaLodo;
    private final Texture texturaHueso;

    public FabricaNivel4(Texture texLodo, Texture texHueso) {
        this.texturaLodo = texLodo;
        this.texturaHueso = texHueso;
    }

    @Override
    public ObjetoLluviosoAbstracto crearObjetoMalo(float posicionX) {
        // Devuelve el objeto malo del nivel: Lodo
        return new Lodo(this.texturaLodo, posicionX);
    }

    @Override
    public ObjetoLluviosoAbstracto crearObjetoBueno(float posicionX) {
        // Devuelve el objeto bueno del nivel: Hueso
        return new Hueso(this.texturaHueso, posicionX);
    }
}
