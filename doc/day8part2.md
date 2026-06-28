# Advent of Code 2025 - Day 8 Part 2: Playground

En la segunda parte del reto, el objetivo evoluciona de rastrear el tamaño de subcircuitos aislados a unificar toda la red eléctrica. El sistema debe procesar las conexiones hasta que todos los nodos formen un único circuito maestro, identificando específicamente cuáles fueron las dos últimas cajas de derivación que completaron la unión.

## Diferencias Parte A vs Parte B

La transición de requisitos plantea un cambio en la extracción de información sobre el modelo:
* **Parte A:** Limitaba el número de conexiones a procesar y analizaba el estado estático resultante (el tamaño de los circuitos individuales).
* **Parte B:** Procesa todas las conexiones posibles. Exige que la estructura de datos sea capaz de recordar no solo la topología del grafo, sino **el último evento de mutación válido** (qué cajas provocaron la última fusión de circuitos).

## Evolución del Diseño y Nuevas Técnicas Utilizadas

* **Retención de Estado en Estructuras Inmutables:** Para recordar la última conexión sin utilizar variables globales mutables ni romper el encapsulamiento del `reduce`, la clase `CircuitSet` se ha enriquecido añadiendo el array `lastConnected`.
    * Si al llamar a `connect()` se unen dos circuitos distintos, se crea un nuevo `CircuitSet` que registra esas dos cajas.
    * Si las cajas ya pertenecían al mismo circuito, el método retorna `this`. Esto no solo omite la conexión redundante, sino que propaga hacia adelante la última conexión válida registrada, actuando como una memoria histórica perfecta en un entorno funcional.
* **Algoritmo de Kruskal Declarativo:** El diseño ha revelado que la combinación de ordenar los pares por distancia (`sorted(comparingDouble(Pair::distance))`) y fusionar conjuntos disjuntos (`merge`) es, en esencia, una implementación pura y funcional del **Algoritmo de Kruskal** para hallar el Árbol de Expansión Mínima (MST). Se ha logrado sin un solo bucle `while` ni listas de adyacencia complejas.
* **Tolerancia a Fallos y Desbordamiento (*Integer Overflow*):** Como se destacó en la resolución del problema, multiplicar coordenadas tridimensionales de gran magnitud (`int * int`) superaba el límite físico de 32 bits de Java, originando resultados negativos por desbordamiento. El método `multipliedXCoordinatesIn` aplica programación defensiva mediante un *casting* temprano a 64 bits (`(long)`), blindando la lógica de negocio frente a la explosión de los datos de entrada.
* **Mantenimiento del Principio OCP:** A pesar del cambio de objetivo, la forma de generar pares cartesianos, calcular distancias y la representación matemática de la `JunctionBox` han permanecido sin modificaciones, validando la solidez del diseño del dominio original.