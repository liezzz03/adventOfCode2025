# Advent of Code 2025 - Día 12: Christmas Tree Farm

Solución diseñada bajo los principios de **Ingeniería del Software II** de la ULPGC. Esta implementación se enfrenta a un problema NP-Duro clásico (Empaquetado 2D o *2D Bin Packing / Polyomino Tiling*). Para resolverlo sin caer en tiempos de ejecución infinitos, la arquitectura combina heurísticas algorítmicas, memoización de estados y un diseño de dominio rico y puramente funcional.

## Modelado de Dominio y Polimorfismo Funcional

* **Supresión de Condicionales Geométricos (Strategy Pattern):** El giro de matrices 2D suele requerir múltiples sentencias `if/switch` o bucles complejos. Aquí se ha encapsulado matemáticamente en el enumerado `Angle`. Al usar la interfaz funcional `RotatingFunction`, cada ángulo inyecta su propia fórmula de transformación de coordenadas `(y, x, h, w) -> new int[]{...}`. Esto permite calcular las rotaciones delegando polimórficamente la operación en un solo paso $O(1)$ por celda.
* **Geometría Inmutable y Eliminación de Duplicados:** El *Record* `Present` modela la forma del regalo. Su método `allOrientations()` genera de forma declarativa las 8 orientaciones posibles (4 rotaciones + 4 reflexiones invertidas) y delega en el método `distinct()` de la API de Streams la purga de formas simétricas duplicadas (gracias a la sobrescritura de `equals` y `hashCode` basada en el contenido profundo de la matriz).
* **Transiciones de Estado Seguras (Monadas):** Al colocar un regalo en la cuadrícula, el método `ChristmasTreeRegion.place()` no muta la matriz original. Si el regalo no cabe, devuelve un `Optional.empty()`. Si cabe, clona la matriz subyacente y devuelve un `Optional.of(new ChristmasTreeRegion(...))`. Este enfoque previene por completo la corrupción de datos durante el retroceso (*backtracking*).

## Algoritmia Avanzada y Poda del Espacio de Búsqueda (*Pruning*)

Dado que el empaquetado 2D tiene una complejidad combinatoria factorial, la clase `ChristmasTree` implementa múltiples capas de defensa para podar ramas muertas lo antes posible:

* **Poda Estática Temprana ($O(1)$):** Antes de intentar colocar ninguna pieza, el método `solve()` valida axiomas matemáticos elementales. Si el área mínima total de los regalos excede el espacio del árbol (`minPresentArea() > presentRegion.area()`), o si caben de sobra asumiendo cajas delimitadoras ineficientes, el algoritmo resuelve el problema instantáneamente sin lanzar la búsqueda.
* **Heurística *Largest First* (Ordenación Decreciente):** En problemas de empaquetado, intentar colocar primero las piezas pequeñas deja huecos fragmentados donde las grandes no caben. El método `expandedPresents` ordena los regalos descendentemente por área (`sorted(reverseOrder())`). Esto fuerza al árbol de búsqueda a explorar primero los escenarios más difíciles, alcanzando los fallos mucho más rápido.
* **Memoización de Estados Fallidos (Dynamic Programming):** El sistema implementa una memoria de ramas muertas (`failedStates`). Si una configuración específica de la cuadrícula con un índice de regalo concreto ya demostró ser un callejón sin salida en otra rama del árbol, el método `isKnownFailure()` aborta la exploración inmediatamente. El identificador de estado se genera eficientemente combinando el índice de profundidad y el `Arrays.deepHashCode(region.grid())`.

## Paradigma Funcional y Generación Declarativa

* **Generadores de Búsqueda Aplanados:** El intento de colocar una pieza en todas las orientaciones y posiciones posibles se resuelve sin un solo bucle anidado. `findFitting(Present, Region)` genera un *Stream* de coordenadas válidas y aplica un `flatMap(Optional::stream)`. Esta línea de código filtra automáticamente los anclajes inválidos (los Optionals vacíos) y propaga únicamente las nuevas configuraciones exitosas de la cuadrícula, logrando una complejidad ciclomática de cero.
* **Extensión Dinámica de Cantidades:** Para manejar el mapa de cantidades (`Map<Present, Integer>`), el método `allPresents` utiliza `Stream.generate(entry::getKey).limit(entry.getValue())`, expandiendo un mapa de frecuencias en un flujo secuencial plano listo para ser procesado por el algoritmo de Backtracking.