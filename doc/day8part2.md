# Advent of Code 2025 - Day 8 Part 2: Playground

En la segunda parte del reto, el objetivo evoluciona de rastrear el tamaño de subcircuitos aislados a unificar toda la red eléctrica. El sistema debe procesar las conexiones hasta que todos los nodos formen un único circuito maestro, identificando específicamente cuáles fueron las dos últimas cajas de derivación que completaron la unión.

## Diferencias Parte A vs Parte B

La transición de requisitos plantea un cambio en la extracción de información sobre el modelo:
* **Parte A:** Limitaba el número de conexiones a procesar y analizaba el estado estático resultante (el tamaño de los circuitos individuales).
* **Parte B:** Procesa todas las conexiones posibles. Exige que la estructura de datos sea capaz de recordar no solo la topología del grafo, sino **el último evento de mutación válido** (qué cajas provocaron la última fusión de circuitos).

## Evolución del Diseño y Nuevas Técnicas Utilizadas

* **Retención de Estado en Estructuras Inmutables:** Para recordar la última conexión sin