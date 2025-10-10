// Archivo: IContenedorSuministros.java
package puppy.code;

/**
 * Define la interfaz para el Contenedor (Collection) del Patrón Iterator (GM2.2).
 * Obliga a la clase a tener un método para crear un Iterador para sus elementos.
 */
public interface IContenedorSuministros {

    /**
     * Crea y devuelve un objeto iterador para recorrer la colección de suministros.
     * @return El iterador específico para esta colección.
     */
    IIteradorSuministros crearIterador();
}
