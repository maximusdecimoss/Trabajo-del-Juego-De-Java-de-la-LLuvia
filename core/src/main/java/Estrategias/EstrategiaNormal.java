// Archivo: EstrategiaNormal.java
package Estrategias;

import interfaces.IEstrategiaRecoleccion;

/**
 * Estrategia Concreta: Recolección estándar (1x el puntaje base).
 */
public class EstrategiaNormal implements IEstrategiaRecoleccion {

    @Override
    public int sumarPuntos(int puntajeBase) {
        return puntajeBase;
    }
}
