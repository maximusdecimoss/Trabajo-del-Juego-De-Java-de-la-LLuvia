// Archivo: EstrategiaDoblePunto.java
package puppy.code;

/**
 * Estrategia Concreta: Recolección doble (2x el puntaje base).
 */
public class EstrategiaDoblePunto implements IEstrategiaRecoleccion {

    @Override
    public int sumarPuntos(int puntajeBase) {
        return puntajeBase * 2;
    }
}
