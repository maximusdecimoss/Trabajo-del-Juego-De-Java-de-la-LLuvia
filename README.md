Juego de la Lluvia — Proyecto Final (LibGDX)

El Juego de la lluvia es un juego de evasión y recolección desarrollado con LibGDX en IntelliJ IDEA, utilizando Programación Orientada a Objetos y Patrones de Diseño para lograr una arquitectura flexible, limpia y escalable.

Descripción General

El jugador debe mover su personaje (Receptor) para atrapar los objetos buenos y evitar los malos.
El objetivo es alcanzar 15.000 puntos o sobrevivir los 5 niveles de dificultad progresiva.

Descripción General

El jugador debe mover su personaje (Receptor) para atrapar los objetos buenos y evitar los malos, mientras se enfrenta a condiciones cada vez mas adversas.
El objetivo es alcanzar 15.000 puntos mientras sobrevives a los 5 niveles de dificultad progresiva.

Características Técnicas

Motor: LibGDX

Lenguaje: Java 11 o superior

IDE: IntelliJ IDEA

Paradigma: Programación Orientada a Objetos

Arquitectura: Basada en el Principio de Responsabilidad Única (SRP)

⚙️ Patrones de Diseño Usados
Patrón	Clase	Propósito
Abstract Factory	IFabricaObjetosLluviosos, GestorGotas	Crea los ítems del nivel actual
Singleton	GestorTiempo, GestorRecursos	Control global de velocidad y recursos
Template Method	ObjetoLluviosoAbstracto.destruir()	Secuencia fija de destrucción
Iterator	ContenedorPociones	Controla las pociones del Vikingo
Strategy	IEstrategiaRecoleccion	Cambia el modo de puntuación (Furia)

Controles


Nivel | Personaje | Movimiento | Meta de Puntos
------|------------|-------------|----------------
1 | Tarro | Caída vertical | 150
2 | Paraguas | Caída vertical | 1.000
3 | Persona | Movimiento lateral | 5.000
4 | Perro | Efectos especiales | 10.000
5 | Vikingo | Máxima dificultad | 15.000 (meta final)
📦 Instalación y Ejecución

Clona o descarga el proyecto

git clone https://github.com/tuusuario/JuegoLluvia.git


Abre la carpeta en IntelliJ IDEA

Verifica que el JDK esté configurado (Java 17 recomendado)

Pulsa Run ▶️ y el juego se iniciará tras compilar

🧩 Créditos

Desarrollador: Víctor Guilarte

Framework: LibGDX

IDE: IntelliJ IDEA

Sonidos e imágenes: Libres de derechos / fuentes propias
