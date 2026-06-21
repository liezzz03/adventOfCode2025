# Advent of Code 2025 - Day 8 Part 1: Playground

Solución diseñada bajo los principios de **Ingeniería del Software II** de la ULPGC. Esta implementación destaca por el modelado inmutable de grafos (conjuntos disjuntos), la delegación de responsabilidades y la generación declarativa de productos cartesianos para evitar la complejidad ciclomática de los bucles anidados.

## Modelado de Grafos y Estructuras de Datos (Disjoint Sets)

* **Abstracción del Grafo (CircuitSet):** En lugar de utilizar una matriz de adyacencia mutable o listas enlazadas clásicas con variables de estado, la red de conexiones se ha modelado como un conjunto de conjuntos (`Set<Set<JunctionBox>>`). Cada subconjunto representa un circuito eléctrico independiente.
* **Inmutabilidad en la Fusión de Nodos:** Al conectar dos cajas de derivación (`connect`), la clase `CircuitSet` no muta sus conjuntos internos mediante `.add()` o `.addAll()`. En su lugar, aplica principios de pureza funcional: localiza los circuitos afectados, los fusiona, retiene los no afectados (`unaffectedCircuits`) y devuelve una **nueva instancia** de `CircuitSet`.
* **Domain-Driven Design (DDD):** El cálculo de la física del problema (la distancia euclidiana en el espacio 3D) no ensucia al orquestador. Se delega por completo a la creación del objeto `Pair`, que encapsula los dos nodos y la distancia matemática que los separa, promoviendo una alta cohesión.

## Paradigma Funcional y Combinatoria Declarativa

* **Producto Cartesiano sin Bucles Anidados:** El paso de generar todas las combinaciones posibles de conexiones entre cajas requiere cruzar todos los elementos entre sí (Complejidad $O(N^2)$). En la programación imperativa clásica, esto se traduciría en un doble bucle `for` anidado y una lista mutable. Aquí se ha aplanado de forma elegante mediante el API de Streams: `IntStream.range().flatMap(...)`, logrando una complejidad ciclomática nula en la lectura.
* **Acumulación de Estados (*Fold / Reduce*):** Para aplicar las *N* conexiones más cortas, el orquestador (`Playground`) utiliza el patrón de reducción (`reduce`). Partiendo de un estado inicial donde todas las cajas están desconectadas (`CircuitSet.from`), el *stream* inyecta secuencialmente cada par a conectar, y el acumulador genera la evolución del estado eléctrico sin producir efectos secundarios colaterales (*side-effects*).

## Principio de Responsabilidad Única (SRP)

* **Separación de Lógica Topológica y Operacional:** * `Playground` orquesta las reglas temporales: cuántas cajas leer, cómo generar los pares, cómo ordenarlos y limitar el número de conexiones (`limit(nConnections)`).
    * `CircuitSet` orquesta la topología espacial: sabe buscar a qué circuito pertenece una caja y cómo fusionar dos circuitos topológicamente separados, pero ignora por completo el concepto de distancias o límites de conexiones.