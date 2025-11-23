// Archivo: EstrategiaDoblePunto.java
package Estrategias;

import interfaces.IEstrategiaRecoleccion;

public class EstrategiaDoblePunto implements IEstrategiaRecoleccion {

    @Override
    public int sumarPuntos(int puntajeBase) {
        return puntajeBase * 2;
    }
}
