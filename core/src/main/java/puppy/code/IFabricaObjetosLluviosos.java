// Archivo: IFabricaObjetosLluviosos.java
package puppy.code;

/**
 * Patrón Abstract Factory (GM2.4).
 * Define la interfaz para crear los objetos que caen (productos).
 */
public interface IFabricaObjetosLluviosos {

    // Método para crear el Objeto Malo (Gota Mala, Roca, etc.)
    public ObjetoLluviosoAbstracto crearObjetoMalo(float posicionX);

    // Método para crear el Objeto Bueno (Gota Buena, Escudo, etc.)
    public ObjetoLluviosoAbstracto crearObjetoBueno(float posicionX);
}
