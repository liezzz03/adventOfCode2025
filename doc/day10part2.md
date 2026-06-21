# Advent of Code 2025 - Day 10 Part 2: Factory

En la segunda parte del reto, la naturaleza matemática del problema evoluciona radicalmente. El sistema de configuración de la máquina deja de ser una simple conmutación de estados binarios independientes (XOR) y se transforma en un sistema de acumulación aritmética con propagación de acarreo (implícito en la necesidad de dividir los estados por 2). Esto convierte el problema en un desafío de búsqueda en un árbol de estados inmenso.

## Diferencias Parte A vs Parte B

El salto algorítmico requiere abandonar la fuerza bruta pura en favor de una búsqueda estructurada e inteligente:
* **Parte A:** Evaluaba todas las combinaciones posibles de botones ($2^N$) en un solo paso aplicando máscaras de bits, asumiendo que los botones simplemente encendían o apagaban luces independientemente.
* **Parte B:** Introduce dependencias aritméticas entre iteraciones. Un estado `Joltage` se resuelve aplicando iterativamente efectos y reduciendo/dividiendo el estado sobrante (`(value - effect) / 2`). La solución requiere recorrer un árbol de decisiones de profundidad variable, donde el coste final se acumula recursivamente (`presses + 2 * subResult`).

## Evolución del Diseño y Nuevas Técnicas Utilizadas

* **Programación Dinámica con Memoización (Caching):** Dado que múltiples ramas del árbol de decisión pueden converger en el mismo estado intermedio (solapamiento de subproblemas), se ha implementado un mapa de caché: `Map<JoltageState, OptionalInt> knownJoltageStates`. El método `computeIfAbsentOnKnownStates` intercepta las llamadas recursivas; si el estado ya fue calculado previamente, devuelve el resultado almacenado en $O(1)$, evitando una explosión combinatoria de tiempo exponencial y garantizando un rendimiento óptimo.
* **Inmutabilidad y Transiciones de Estado Puras:** El patrón *State* se aplica de forma puramente funcional mediante el *Record* `JoltageState`. Al aplicar un efecto de botones (`JoltageEffect`), el método `nextState` no muta los voltajes actuales, sino que computa la nueva capa matemática y devuelve una **nueva instancia** inmutable, lo que es vital para que la recursividad y la memoización (que depende del `hashCode` y `equals` del estado) funcionen correctamente.
* **Poda Heurística del Espacio de Búsqueda (*Pruning*):** En lugar de explorar todas las combinaciones de botones a ciegas, el estado se defiende con el método `canApply`. Utiliza dos restricciones matemáticas estrictas:
    1.  **Límites:** El efecto no puede superar el voltaje actual (`effect <= values`).
    2.  **Paridad (Bitwise AND):** Usando `matchesParity ((a & 1) == (b & 1))`, el sistema detecta instantáneamente si la resta generaría un número impar (que no podría dividirse por 2 exactamente).
        Esto poda ramas inválidas masivas en $O(1)$ antes de iniciar la costosa llamada recursiva.
* **Manejo Funcional de Caminos sin Salida (*Optionals*):** En los problemas de búsqueda, muchas ramas terminan en un callejón sin salida (no se puede llegar al estado cero). En lugar de usar números mágicos (como devolver `-1` o `Integer.MAX_VALUE`) o lanzar excepciones de control de flujo, se utiliza `OptionalInt`. Al combinarlo con el API de Streams (`flatMapToInt(OptionalInt::stream).min()`), los caminos inválidos (Optionals vacíos) desaparecen silenciosa y elegantemente del flujo, seleccionando automáticamente el mínimo matemático de los caminos que sí encontraron solución.