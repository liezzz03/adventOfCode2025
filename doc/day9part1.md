# Advent of Code 2025 - Day 9 Part 1: Movie Theater

Solución diseñada bajo los principios de **Ingeniería del Software II** de la ULPGC, enfocada en la creación de un modelo de dominio rico geométrico, el uso avanzado de *Streams* para combinatoria y la aplicación de patrones de diseño para el manejo seguro de ausencias de valor.

## Modelado Espacial y Patrones de Diseño

* **Prevención de *Primitive Obsession*:** Las coordenadas crudas y las dimensiones no flotan libremente por el sistema. Se han encapsulado en el `Record` inmutable `RedTile` (representando un punto o loseta) y en la clase `Rectangle` (representando el área delimitada por dos losetas). Esto otorga una fuerte cohesión semántica: es el rectángulo quien sabe calcular su propia área.
* **Patrón *Null Object*:** Para manejar de forma segura el caso base de la búsqueda de máximos o listas vacías, se han definido constantes nulas (`Rectangle.Null` y `RedTile.Null`). Al usar `max(...).orElse(Rectangle.Null)`, se elimina por completo la necesidad de devolver valores nulos de Java (`null`) y se evitan las temidas excepciones `NullPointerException`, permitiendo que el sistema responda polimórficamente con un área de `0`.
* **Principio de Responsabilidad Única (SRP):** * `MovieTheater` es el orquestador: sabe cómo leer la entrada y cómo generar las combinaciones de losetas.
    * `Rectangle` conoce la física del dominio: calcula su área geométrica inclusiva aplicando valor absoluto `abs()`.

## Paradigma Funcional y Combinatoria Declarativa

* **Producto Cartesiano sin Bucles Anidados:** Para encontrar el rectángulo más grande, es necesario evaluar todas las parejas posibles de losetas rojas (Complejidad $O(N^2)$). En un diseño imperativo, esto requeriría dos bucles `for` anidados con manipulación de índices. Aquí, se ha resuelto de forma puramente declarativa mediante el aplanamiento de flujos: `IntStream.range().flatMap(...)`. Esto genera un flujo ininterrumpido de objetos `Rectangle` sin mutar ninguna variable de estado.
* **Búsqueda Expresiva de Máximos:** La extracción del resultado final se lee como lenguaje natural gracias al uso de *Method References* y el comparador nativo de Java: `max(comparingLong(Rectangle::area))`. La complejidad ciclomática del método `largestArea` es exactamente cero.

## Robustez y Sanitización de Datos

* **Programación Defensiva Multicapa:** Conscientes de los problemas de formato en los sistemas de archivos (saltos de línea de Windows vs Linux), el sistema aplica una doble capa de sanitización. En `MovieTheater` se realiza un `split("\\r?\\n")` y un filtrado de líneas vacías, y en `RedTile` se aplica un `.trim()` a cada coordenada antes del `parseLong()`. Esto blinda la lógica de negocio frente a corrupciones de lectura invisibles.