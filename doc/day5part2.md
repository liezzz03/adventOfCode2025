# Advent of Code 2025 - Day 5 Part 2: Cafeteria

En la segunda parte del problema, la magnitud de los datos imposibilita comprobar cada ingrediente individualmente contra todos los rangos. El enfoque evoluciona de un problema de búsqueda lineal a un problema algorítmico clásico de **fusión de intervalos superpuestos** (*Merge Intervals*). El objetivo ahora es consolidar todos los rangos válidos y calcular el tamaño matemático total que abarcan.

## Diferencias Parte A vs Parte B

El cambio de requisitos exige un salto drástico en la optimización computacional:
* **Parte A:** Evaluaba de forma perezosa si un ID específico existía dentro de algún rango.
* **Parte B:** Prescinde de la lista de ingredientes a comprobar. En su lugar, el sistema unifica los rangos solapados o adyacentes en una nueva lista de rangos mutuamente excluyentes, y calcula la extensión total (`rangeExtension`) de cada uno para obtener la cuenta final de ingredientes frescos válidos.

## Evolución del Diseño y Nuevas Técnicas Utilizadas

* **Patrón de Diseño *Null Object*:** Para representar el estado inicial del combinador de rangos sin recurrir a referencias nulas (lo cual viola los principios de *Clean Code* al forzar múltiples verificaciones `if (current != null)`), se ha introducido la constante `Range.Null = new Range(-1, -1)`. Esto garantiza que los métodos puedan interactuar polimórficamente con el objeto inicial sin riesgo de `NullPointerException`.
* **Fold/Reduce Funcional para la Gestión de Estado:** El proceso de unificación de rangos se ha aislado en la nueva entidad `RangeMerger`. En lugar de utilizar una lista mutable y un bucle `for` convencional, se ha implementado un pliegue funcional (*fold*) mediante `reduce`. El combinador actúa como un acumulador inmutable: cada llamada a `mergeNext` evalúa si el rango debe fusionarse con el actual o si debe iniciar uno nuevo, devolviendo siempre una **nueva instancia** de `RangeMerger` libre de *side-effects*.
* **Delegación de la Fusión (Tell, Don't Ask):** La lógica matemática para determinar si dos rangos se tocan y cómo se combinan no reside en el orquestador, sino que se ha encapsulado estrictamente dentro del *Record* `Range`. Métodos como `mergeableWith()` evalúan incluso la adyacencia exacta (`this.end >= range.start() - 1`), y `merge()` devuelve un nuevo `Range` aplicando funciones matemáticas puras (`Math.max`, `Math.min`).
* **Optimización Algorítmica (Sorting + Single Pass):** Al implementar la interfaz `Comparable<Range>` para ordenar los rangos por su punto de inicio (`start`), el sistema garantiza que la unificación en `RangeMerger` pueda realizarse en una única pasada lineal O(N) tras la ordenación previa O(N log N). Esto asegura que el código pueda manejar millones de rangos con un rendimiento óptimo y un consumo de memoria predecible.