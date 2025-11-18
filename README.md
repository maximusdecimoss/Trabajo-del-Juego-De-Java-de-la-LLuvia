Juego de la Lluvia — Proyecto Final (LibGDX)

El Juego de la Lluvia es un videojuego de evasión y recolección desarrollado con el framework LibGDX en IntelliJ IDEA, implementando principios de Programación Orientada a Objetos (POO) y Patrones de Diseño para lograr una arquitectura flexible, limpia y escalable.

Su objetivo es demostrar la correcta aplicación de principios de ingeniería de software dentro de un entorno interactivo y lúdico, evidenciando el uso de técnicas de modularización, reutilización y control de dependencias.

 Descripción General

El jugador controla distintos personajes (Receptores) a lo largo de cinco niveles de dificultad progresiva, donde debe:

AtrapAR objetos buenos para sumar puntos.

Evitar objetos dañinos que restan vidas o velocidad.

Adaptarse a nuevos efectos, velocidades y habilidades en cada fase.

El objetivo final es alcanzar 15.000 puntos o sobrevivir todos los niveles sin perder las 3 vidas iniciales.
Cada nivel introduce una nueva mecánica, un tipo de movimiento distinto y mayor dificultad visual y sonora.

 Progresión de Niveles
Nivel | Personaje | Movimiento | Meta de Puntos
------|------------|-------------|----------------
1 | Tarro | Caída vertical | 150
2 | Paraguas | Caída vertical | 1.000
3 | Persona | Movimiento lateral | 5.000
4 | Perro | Efectos especiales de lentitud al agarrar el lodo | 10.000
5 | Vikingo | Máxima dificultad ) | 15.000 (meta final)

⚙️ Características Técnicas

Motor: LibGDX

Lenguaje: Java 11 o superior

IDE: IntelliJ IDEA

Paradigma: Programación Orientada a Objetos

Arquitectura: Basada en el Principio de Responsabilidad Única (SRP)

Gestión de Recursos: Singleton para texturas, sonidos y tiempo global

Patrones de Diseño Utilizados
Patrón	Clase / Rol	Propósito
Abstract Factory	IFabricaObjetosLluviosos, GestorGotas	Crea los ítems específicos de cada nivel (rocas, monedas, meteoros, etc.)
Singleton	GestorTiempo, GestorRecursos	Control global de velocidad, tiempo y recursos gráficos/sonoros
Template Method	ObjetoLluviosoAbstracto.destruir()	Estandariza la secuencia de destrucción de objetos
Iterator	ContenedorPociones, Vikingo	Controla el uso de pociones del jugador sin exponer el arreglo interno
Strategy	IEstrategiaRecoleccion, ReceptorAbstracto	Permite cambiar el comportamiento de puntuación en tiempo real (modo Furia)

 Controles
 
Acción	Tecla
Mover Izquierda / Derecha	⬅️ ➡️
Mover Arriba / Abajo (Nivel 3)	⬆️ ⬇️
Usar Poción (Vikingo)	Espacio
Activar / Desactivar Furia	F


 Inspiración y Enfoque de Diseño

El Juego de la Lluvia nace como una metáfora del caos y adaptación:
cada nivel representa una tormenta diferente, donde el jugador debe mantener el control y la precisión ante un entorno que se vuelve cada vez más impredecible.

Se buscó equilibrar diversión, reto y claridad visual, aplicando mecánicas simples pero efectivas que reflejan principios sólidos de ingeniería de software.

 Desafíos Técnicos Enfrentados

Durante el desarrollo se abordaron varios retos clave:

Control de colisiones: resuelto usando Rectangle.overlaps() de LibGDX.

Optimización de texturas: centralizadas mediante GestorRecursos (Singleton).

Transición de niveles: gestionada por GestorControlJuego con metas fijas por puntaje.

Efectos de velocidad: aplicados con GestorTiempo para modificar dinámicamente la caída de objetos.

Estos desafíos permitieron fortalecer el entendimiento de POO, patrones de diseño y gestión de memoria en entornos gráficos.

Instalación y Ejecución

Clona o descarga el proyecto

git clone https://github.com/maximusdecimoss/Trabajo-del-Juego-De-Java-de-la-LLuvia.git


Abre la carpeta en IntelliJ IDEA

Configura el JDK 11 o superior

Ejecuta la clase principal GameLluvia.java

Pulsa Run ▶️ y disfruta del juego 

 Requisitos del Sistema
 
 Requisitos mínimos:
- Java 11+
- Gradle 8+
- IntelliJ IDEA o Eclipse con plugin de LibGDX
- 4 GB RAM

 Desarrolladores

Víctor Guilarte — Diseño de arquitectura, clases principales y lógica de juego

Nicolás Fuentes — Diseño de niveles y manejo de ítems lluviosos

Eduardo Sandoval — Implementación de interfaz, pruebas y optimización de recursos

Framework: LibGDX
IDE: IntelliJ IDEA
Recursos: Sonidos e imágenes libres de derechos / fuentes propias

 Objetivo del Proyecto

Este proyecto busca integrar la teoría de la Programación Orientada a Objetos con la práctica del diseño de videojuegos, mostrando cómo los patrones de diseño pueden aplicarse en sistemas interactivos para mejorar su mantenibilidad, escalabilidad y legibilidad.

Más que un juego, Juego de la Lluvia es una demostración de cómo los fundamentos de la ingeniería de software pueden dar vida a experiencias dinámicas y visuales.
