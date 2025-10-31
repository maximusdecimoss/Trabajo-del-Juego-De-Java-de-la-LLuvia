# PROYECTO FINAL: JUEGO DE LA LLUVIA

A [libGDX](https://libgdx.com/) project generated with [gdx-liftoff](https://github.com/libgdx/gdx-liftoff).

This project was generated with a template including simple application launchers and an `ApplicationAdapter` extension that draws libGDX logo.

Este proyecto implementa un juego de evasión y recolección basado en LibGDX, utilizando la Programación Orientada a Objetos (POO) y Patrones de Diseño para crear una arquitectura flexible, limpia y escalable.

El diseño del juego cumple con el Principio de Responsabilidad Única (GM1.6), delegando la lógica central a clases especializadas para evitar la saturación de la clase principal (GameLluvia).

## ARQUITECTURA GENERAL Y PATRONES DE DISEÑO

El juego está dividido en 5 niveles que cambian el personaje (Receptor) y los ítems que caen (Objetos Lluviosos), logrando la máxima optimización mediante patrones:

## Patrones de Diseño Utilizados

| **Patrón** | **Clase/Rol** | **Propósito en el Juego** |
|-------------|----------------|-----------------------------|
| **Abstract Factory (GM2.4)** | `IFabricaObjetosLluviosos` (Interfaces) y `GestorGotas` (Contexto) | Controla la **creación de ítems** por nivel. El `GestorGotas` solo solicita un objeto "bueno" o "malo", delegando la decisión de instanciar (`Roca`, `Moneda`, `Meteoro`) a la fábrica específica del nivel actual (`FabricaNivelX`). |
| **Singleton (GM2.1)** | `GestorTiempo` y `GestorRecursos` | `GestorTiempo` controla la velocidad global (efectos de Hueso/Lodo). `GestorRecursos` centraliza la carga y liberación de todos los assets (`Texture`, `Sound`, `Music`), optimizando la memoria (GM1.6). |
| **Template Method (GM2.2)** | `ObjetoLluviosoAbstracto.destruir()` | Define el algoritmo de **destrucción** de un objeto en una secuencia fija (limpiar límites → liberar recursos únicos), obligando a las subclases a seguir el proceso. |

## Patrones de Comportamiento (Habilidades del Vikingo)

| **Patrón** | **Clase/Rol** | **Funcionalidad** |
|-------------|----------------|-------------------|
| **Iterator (GM2.2)** | `Vikingo` y `ContenedorPociones` | Gestiona el uso de las **3 pociones de vida** del Vikingo de forma controlada. Permite consumir las pociones una a una sin exponer el array interno. |
| **Strategy (GM2.3)** | `IEstrategiaRecoleccion` y `ReceptorAbstracto` | Permite al Vikingo **cambiar su comportamiento de puntuación** en tiempo real (activar el modo Furia para obtener el doble de puntos). |

## MECÁNICAS DE JUEGO

El objetivo es alcanzar 15,000 puntos o sobrevivir a los 5 niveles de dificultad progresiva.

## Controles y Movimiento

- **Movimiento General:** Flechas Izquierda/Derecha.  
- **Movimiento Especial (Nivel 3 – Persona):** Flechas Arriba/Abajo (Movimiento 2D).  
- **Habilidad (Nivel 5 – Vikingo):**  
  - **[ESPACIO]:** Usa una Poción de Vida *(Iterator)*.  
  - **[F]:** Activa/Desactiva el **Modo Furia** *(Doble Puntuación – Strategy)*.  

## Progresión de Niveles (Metas Fijas)

| **Nivel** | **Personaje (Receptor)** | **Movimiento de ítems** | **Puntos para Siguiente Nivel** |
|------------|----------------------------|----------------------------|----------------------------------|
| **1** | Tarro | Vertical (Caída Normal) | 150 pts |
| **2** | Paraguas | Vertical (Caída Normal) | 1,000 pts |
| **3** | Persona | Lateral (Izq. a Der.) | 5,000 pts |
| **4** | Perro | Vertical (Efectos Singleton) | 10,000 pts |
| **5** | Vikingo | Vertical (Máxima Dificultad) | **Meta Final: 15,000 pts** |

# Lógica de Dificultad

- Penalización Progresiva: Cada golpe resta -1 vida y una penalización de puntos equivalente a Nivel Actual 10 puntos (Ejemplo: -50 puntos en Nivel 5).
- Velocidad: La velocidad de caída y la frecuencia de aparición de ítems aumentan con cada nivel.

## Estructura del Código

El proyecto sigue un diseño modular y limpio, con clases agrupadas por responsabilidad.

- GameLluvia.java: Coordinador de LibGDX (Clase Despejada).
- IniciadorJuego.java: Cableado de dependencias (Factory).
- GestorControlJuego.java: Lógica de juego y HUD.
- GestorGotas.java: Contexto del Abstract Factory.
- GestorTiempo.java: Singleton para efectos de velocidad.
- interfaces/: Contiene Desechable, IFabricaObjetosLluviosos, etc.
- receptores/: Contiene Tarro.java, Vikingo.java, ReceptorAbstracto.java.
