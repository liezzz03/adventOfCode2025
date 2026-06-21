# Advent of Code 2025 - Day 7 Part 1: Laboratories

Solución diseñada bajo los principios de **Ingeniería del Software II** de la ULPGC. Esta implementación destaca por el uso avanzado de recursividad para la gestión del estado, la encapsulación rigurosa de los límites espaciales y la evasión de la obsesión por los tipos primitivos.

## Modelado del Dominio y Abstracción Espacial

* **Evasión de *Primitive Obsession*:** En lugar de manipular matrices de caracteres (`char[][]`) desnudos y comparar literales (`'.'`, `'S'`, `'^'`), el dominio se ha modelado utilizando el enumerado `TachyonCell`. Esto no solo centraliza el mapeo visual-lógico, sino que aporta una semántica fuerte al sistema: las celdas ya no son letras, son conceptos (`EMPTY`, `BEAM`, `SPLITTER`).
* **Programación Defensiva y Encapsulación (Information Hiding):** La clase `TachyonGrid` actúa como una barrera protectora alrededor de la matriz bidimensional. Al encapsular la verificación de límites en el método `isOutOfBounds`, permite que la capa superior solicite coordenadas libremente. Si una coordenada excede el tablero, devuelve polimórficamente una celda `EMPTY`, evitando por completo las excepciones `ArrayIndexOutOfBoundsException` sin ensuciar la lógica de propagación con múltiples `if`.

## Gestión de Estado mediante Recursividad e Inmutabilidad

* **Simulación por Capas sin Efectos Secundarios:** En lugar de utilizar bucles anidados (`for/while`) mutando variables de estado (como un contador de divisiones o una matriz mutable temporal), el avance del rayo se ha modelado como un **proceso recursivo** (`propagateBeam`). Cada capa (fila) se calcula en base a la capa anterior.
* **Encapsulación del Estado Temporal (*State Object*):** Para transmitir los resultados entre las llamadas recursivas, se utiliza el *Record* privado `Step`, que agrupa inmutablemente la configuración de la fila actual (`List<TachyonCell>`) y el conteo acumulado de divisiones (`nSplits`). Cada iteración devuelve un `Step` completamente nuevo, garantizando la pureza funcional del algoritmo.

## Paradigma Funcional y Diseño Declarativo

* **Cálculo de Transiciones con Streams:** La propagación del rayo a la siguiente fila no se construye instruyendo *cómo* colocar cada rayo, sino definiendo *qué* debe haber en cada posición. El método `nextLayer` utiliza `IntStream.range().mapToObj(...)` para generar declarativamente el estado íntegro de la siguiente fila.
* **Granularidad y Alta Cohesión:** La complejidad de saber si un rayo continúa o se divide se ha descompuesto en funciones puramente evaluativas y de muy alta cohesión: `beamPropagating`, `beamSplit` y `beamSplitFrom`. Esto hace que el método `nextCell` se lea como lenguaje natural, cumpliendo de forma excelente la premisa de *código autodocumentado*.