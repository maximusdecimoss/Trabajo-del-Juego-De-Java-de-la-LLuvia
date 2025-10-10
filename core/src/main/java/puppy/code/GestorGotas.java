package puppy.code;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import puppy.code.IFabricaObjetosLluviosos; // La interfaz que usan las fábricas
import puppy.code.FabricaNivel1;             // Necesaria para Nivel 1 y default
import puppy.code.FabricaNivel2;             // Necesaria para Nivel 2
import puppy.code.FabricaNivel3;


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

    // Texturas PARA NIVEL 3!
    private final Texture texturaGloboAgua;
    private final Texture texturaMoneda;

    // NUEVO ATRIBUTO CLAVE: La Fábrica Abstracta que usamos para crear ítems (GM2.4)
    private IFabricaObjetosLluviosos fabricaActual;


    // CONSTRUCTOR: Recibe TODAS las texturas necesarias
    public GestorGotas(Texture texBuena, Texture texMala, Sound sonido, Music musica,
                       Texture texRoca, Texture texEscudo,
                       Texture texGloboAgua, Texture texMoneda) { // <-- ¡Nuevos Parámetros!
        this.musicaLluvia = musica;
        this.sonidoGota = sonido;
        this.texturaGotaBuena = texBuena;
        this.texturaGotaMala = texMala;
        this.texturaRoca = texRoca;
        this.texturaEscudo = texEscudo;

        // Asignar nuevas texturas
        this.texturaGloboAgua = texGloboAgua;
        this.texturaMoneda = texMoneda;
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

        // Probabilidad: 30% Malo (3 o menos), 70% Bueno (4 o más)
        if (MathUtils.random(1, 10) <= 3) { // <-- ¡CORRECCIÓN! Usar <= 3
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

            // El nivel ha cambiado o es la primera vez:
            this.fabricaActual = this.seleccionarFabrica(nivelActual);

            // ¡CORRECCIÓN CLAVE! Vaciamos la lista de objetos lluviosos.
            // Esto es necesario para que los ítems del nivel anterior (Rocas/Escudos)
            // no sigan cayendo después de que se selecciona la nueva fábrica (Moneda/Globo).
            this.objetosLluviosos.clear();
        }

        // GM1.7: Aplicar Factor de Velocidad basado en el Nivel
        float factorVelocidad = 1.0f + (nivelActual - 1) * 0.2f;

        // Generar nueva gota: Usa la fábrica seleccionada
        if (TimeUtils.nanoTime() - this.tiempoUltimaGota > 1000000000L / nivelActual) {
            this.crearObjetoLluvioso();
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
        if (nivel == 3) return this.fabricaActual instanceof FabricaNivel3; // <-- ¡NIVEL 3!
        // AGREGAR LÓGICA PARA NIVELES 4 y 5
        return false;
    }

    private IFabricaObjetosLluviosos seleccionarFabrica(int nivel) {
        // Instancia la fábrica correcta basada en el nivel
        switch (nivel) {
            case 1:
                return new FabricaNivel1(this.texturaGotaMala, this.texturaGotaBuena);
            case 2:
                return new FabricaNivel2(this.texturaRoca, this.texturaEscudo);
            case 3:
                return new FabricaNivel3(this.texturaGloboAgua, this.texturaMoneda); // ¡Añadir fábrica para nivel 3!
            // TODO: Agregar casos para niveles 4 y 5 si tienen ítems específicos
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
        this.texturaGloboAgua.dispose(); // ¡Añadir!
        this.texturaMoneda.dispose();
        // AGREGAR dispose() PARA LAS TEXTURAS DE NIVELES 3, 4, 5
    }
}
