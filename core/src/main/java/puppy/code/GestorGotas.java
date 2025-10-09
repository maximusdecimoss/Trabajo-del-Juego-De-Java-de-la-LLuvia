package puppy.code;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Gestor de los objetos que caen.
 * Actúa como el Contexto que utiliza el ObjetoLluviosoAbstracto. (Cumple GM1.4)
 * IMPLEMENTA EL PATRÓN ABSTRACT FACTORY (GM2.4)
 */
public class GestorGotas implements Desechable {

    // ATRIBUTOS
    private Array<ObjetoLluviosoAbstracto> objetosLluviosos;

    private long tiempoUltimaGota;
    private Sound sonidoGota;
    private Music musicaLluvia;

    // TEXTURAS DEL NIVEL 1
    private final Texture texturaGotaBuena;
    private final Texture texturaGotaMala;

    // TEXTURAS DEL NIVEL 2
    private final Texture texturaRoca;
    private final Texture texturaEscudo;

    // NUEVO ATRIBUTO CLAVE: La Fábrica Abstracta que usamos para crear ítems (GM2.4)
    private IFabricaObjetosLluviosos fabricaActual;


    // CONSTRUCTOR: Recibe TODAS las texturas necesarias
    public GestorGotas(Texture texBuena, Texture texMala, Sound sonido, Music musica, Texture texRoca, Texture texEscudo) {
        this.musicaLluvia = musica;
        this.sonidoGota = sonido;
        this.texturaGotaBuena = texBuena;
        this.texturaGotaMala = texMala;
        this.texturaRoca = texRoca;
        this.texturaEscudo = texEscudo;
    }

    // CREACIÓN
    public void crear() {
        this.objetosLluviosos = new Array<ObjetoLluviosoAbstracto>();
        this.musicaLluvia.setLooping(true);
        this.musicaLluvia.play();
    }

    // LÓGICA DE FÁBRICA: Usa la fábrica seleccionada para crear ítems (GM2.4)
    private void crearObjetoLluvioso() {
        float posicionX = (float)MathUtils.random(0, 736);
        ObjetoLluviosoAbstracto nuevoObjeto = null;

        // Probabilidad: 70% Bueno, 30% Malo (Esta lógica es constante para todos los niveles)
        if (MathUtils.random(1, 10) < 3) {
            // Usar la fábrica actual para crear el Objeto Malo del Nivel
            nuevoObjeto = this.fabricaActual.crearObjetoMalo(posicionX);
        } else {
            // Usar la fábrica actual para crear el Objeto Bueno del Nivel
            nuevoObjeto = this.fabricaActual.crearObjetoBueno(posicionX);
        }

        // Añadir el objeto si se creó
        if (nuevoObjeto != null) {
            this.objetosLluviosos.add(nuevoObjeto);
        }

        this.tiempoUltimaGota = TimeUtils.nanoTime();
    }

    // ACTUALIZACIÓN DE MOVIMIENTO Y COLISIONES
    public void actualizarMovimiento(ReceptorAbstracto receptor, GestorNiveles gestorNiveles) {

        int nivelActual = gestorNiveles.getNivelActual();

        // GM2.4: LÓGICA DEL ABSTRACT FACTORY: Elegir/Actualizar la fábrica
        if (this.fabricaActual == null || !this.esFabricaCorrecta(nivelActual)) {
            this.fabricaActual = this.seleccionarFabrica(nivelActual);
        }

        // GM1.7: Aplicar Factor de Velocidad basado en el Nivel
        float factorVelocidad = 1.0f + (nivelActual - 1) * 0.2f;

        // Generar nueva gota: Usa la fábrica seleccionada
        if (TimeUtils.nanoTime() - this.tiempoUltimaGota > 1000000000L / nivelActual) {
            this.crearObjetoLluvioso(); // Ya no necesita el gestor como parámetro
        }

        // Iterar y actualizar todos los objetos
        for(int i = 0; i < this.objetosLluviosos.size; ++i) {
            ObjetoLluviosoAbstracto objeto = this.objetosLluviosos.get(i);

            // Llama al método actualizado que recibe el factor de velocidad
            objeto.actualizar(factorVelocidad);

            // 1. Eliminar si está fuera de pantalla
            if (objeto.estaFueraDePantalla()) {
                this.objetosLluviosos.removeIndex(i);
                --i;
                continue;
            }

            // 2. Chequeo de colisión
            if (objeto.obtenerLimites().overlaps(receptor.getArea())) {

                // POLIMORFISMO
                objeto.aplicarEfecto(receptor, gestorNiveles);

                this.sonidoGota.play();

                this.objetosLluviosos.removeIndex(i);
                --i;
            }
        }
    }

    // MÉTODOS AUXILIARES PARA EL ABSTRACT FACTORY (GM2.4)
    private boolean esFabricaCorrecta(int nivel) {
        // Usa 'instanceof' para verificar el tipo de fábrica actual
        if (nivel == 1) return this.fabricaActual instanceof FabricaNivel1;
        if (nivel == 2) return this.fabricaActual instanceof FabricaNivel2;
        // AGREGAR LÓGICA PARA NIVELES 3, 4 y 5
        return false;
    }

    private IFabricaObjetosLluviosos seleccionarFabrica(int nivel) {
        // Instancia la fábrica correcta basada en el nivel
        switch (nivel) {
            case 1:
                return new FabricaNivel1(this.texturaGotaMala, this.texturaGotaBuena);
            case 2:
                return new FabricaNivel2(this.texturaRoca, this.texturaEscudo);
            // AGREGAR CASES PARA NIVELES 3, 4, 5
            default:
                return new FabricaNivel1(this.texturaGotaMala, this.texturaGotaBuena);
        }
    }
    // ----------------------------------------------------------------------------------

    // DIBUJO POLIMÓRFICO
    public void actualizarDibujoLluvia(SpriteBatch batch) {
        for(ObjetoLluviosoAbstracto objeto : this.objetosLluviosos) {
            objeto.dibujar(batch);
        }
    }

    // LIBERACIÓN DE RECURSOS (GM1.5)
    @Override
    public void liberarRecursos() {
        this.sonidoGota.dispose();
        this.musicaLluvia.dispose();
        this.texturaGotaBuena.dispose();
        this.texturaGotaMala.dispose();
        this.texturaRoca.dispose();
        this.texturaEscudo.dispose();
        // AGREGAR dispose() PARA LAS TEXTURAS DE NIVELES 3, 4, 5
    }
}
