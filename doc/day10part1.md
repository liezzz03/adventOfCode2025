# Advent of Code 2025 - Day 10 Part 1: Factory

Solución diseñada bajo los principios de **Ingeniería del Software II** de la ULPGC. Esta implementación destaca brillantemente por el uso intensivo de operaciones a nivel de bits (*bitwise operations*) para modelar el estado, y por resolver un problema de explosión combinatoria utilizando flujos puramente funcionales y matemáticos.

## Modelado de Estado de Alta Eficiencia (*Bitwise Operations*)

* **Evasión de Colecciones Mutables para Estados Binarios:** En lugar de modelar las luces y los botones utilizando matrices de booleanos (`boolean[]`) o listas mutables que requerirían bucles pesados para actualizarse, el dominio entero se ha colapsado a representaciones enteras de 32 bits (`int`).
    * `LightIndicator` traduce un patrón visual (`[.#.#]`) directamente a su valor binario nativo utilizando `Integer.parseInt(..., 2)`.
    * `Button` construye su máscara de impacto (qué cables activa) desplazando bits a la izquierda (`1 << idx`) y acumulándolos con un OR lógico (`|`).
* **Conmutación Matemática (*Toggling*):** En los problemas donde pulsar un botón alterna un estado (encendido a apagado, y viceversa), la operación matemática perfecta es el OR Exclusivo (**XOR**, `^`). El método `pressAllButtonsIn` aplica un pliegue (`reduce`) usando XOR sobre las máscaras de los botones, simulando las pulsaciones sin alterar ninguna variable de estado externa ni usar sentencias condicionales `if`.

## Búsqueda Combinatoria Declarativa

* **Generador de Subconjuntos mediante Máscaras Numéricas:** Para encontrar la configuración óptima, es necesario probar todas las combinaciones posibles de botones pulsados y no pulsados ($2^N$ combinaciones). En lugar de usar algoritmos de *Backtracking* recursivo complejos o bucles anidados, el sistema modela los subconjuntos como un rango de enteros desde $1$ hasta $2^N$. En el método `isButtonPressed`, se evalúa si un botón pertenece a una combinación específica en $O(1)$ usando la máscara AND (`buttonMask & (1 << idx)`).
* **Funciones Nativas para Optimización:** El problema requiere encontrar el número *mínimo* de pulsaciones que encienden el patrón correcto. Al mapear cada combinación válida a `Integer::bitCount` (una función nativa de Java superoptimizada que cuenta los bits encendidos en un número), el sistema deduce matemáticamente cuántos botones se pulsaron en esa combinación concreta, extrayendo el mínimo (`min().orElse(0)`) de forma directa y semántica.

## Extracción Dinámica de Datos (Expresiones Regulares)

* **Parseo de Flujos Funcionales:** La entrada de las máquinas puede contener un número variable de botones entre paréntesis. En lugar de procesar el texto manualmente (`indexOf`, `substring` iterativos), se utiliza la potencia de la API de *Regex* moderna de Java. El uso de `BUTTON_PATTERN.matcher(machine).results()` genera un *Stream* de coincidencias, permitiendo mapear los resultados capturados directamente a objetos `Button` de forma declarativa y sin bucles explícitos.