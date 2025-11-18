// Archivo: IFabricaObjetosLluviosos.java
package interfaces;

import puppy.code.ObjetoLluviosoAbstracto;

public interface IFabricaObjetosLluviosos {

    public ObjetoLluviosoAbstracto crearObjetoMalo(float posicionX);

    public ObjetoLluviosoAbstracto crearObjetoBueno(float posicionX);
}
