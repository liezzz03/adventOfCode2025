# Advent of Code 2025 - Day 4 Part 2: Printing Department

En la segunda parte del reto, el problema evoluciona de ser una consulta estática de topología a una **simulación dinámica de estados** (similar a un Autómata Celular). La carretilla elevadora retira los rollos accesibles, lo que modifica la cuadrícula y expone nuevos rollos en el siguiente turno. Este proceso iterativo continúa hasta que ya no quedan rollos accesibles.

## Diferencias Parte A vs Parte B

El salto de complejidad reside en la mutación del entorno a lo largo del tiempo:
* **Parte A:** Evaluaba un único estado inmutable de la matriz para devolver un recuento.
* **Parte B:** Evalúa una secuencia de generaciones de la matriz, donde el estado `N+1` depende estrictamente de los cálculos del estado `N`, acumulando los recuentos parciales de cada iteración hasta alcanzar un punto de detención.

## Evolución del Diseño y Nuevas Técnicas Utilizadas

* **Simulación Inmutable (Ausencia de *Side-Effects*):** En los problemas de simulación de cuadrículas, modificar la matriz "in-place" mientras se evalúa genera lecturas sucias (un rollo retirado prematuramente afectaría al cálculo de sus vecinos en el mismo turno). El diseño evita esto haciendo que el método `next()` devuelva siempre una **nueva instancia** de `Warehouse` con una matriz completamente nueva (`updatedGrid`). El estado original nunca se muta.
* **Generación Declarativa de Estados (`Stream.iterate`):** Se ha evitado el clásico bucle imperativo `while (estado != null)` auxiliado por variables acumuladoras externas. En la clase `ForkliftManager`, la transición de estados temporales se logra mediante `Stream.iterate(warehouse, Objects::nonNull, Warehouse::next)`. Este *pipeline* genera un flujo continuo de almacenes que se detiene elegantemente en el momento en que `next()` devuelve `null`.
* **Proyección Lógica (Mapeo de Reglas de Negocio):** La lógica de transición para la siguiente generación se expresa de forma natural y declarativa en el método `updatedRow()`. Un punto del almacén mantendrá un rollo si y solo si `hasRollAtPosition(p) && !isAccessible(p)` (es decir, el rollo existía y la carretilla no pudo llevárselo).
* **Principio de Abierto/Cerrado (OCP de SOLID):** La robustez del diseño inicial se demuestra al observar que el *Record* `Position` y toda su lógica de cálculo de vecindades han permanecido intactos. Estaban modelados de forma tan pura y aislada que soportaron el drástico cambio de requisitos sin necesidad de ser modificados.