# Advent of Code 2025 - Day 7 Part 2: Laboratories

En la segunda parte del reto, la naturaleza del problema experimenta un cambio radical. Ya no se nos pide simular el estado visual de la propagación, sino calcular la cantidad total de **rutas o caminos distintos** que puede tomar un rayo tras atravesar múltiples divisores (*splitters*). Esto introduce el riesgo de una explosión combinatoria (crecimiento exponencial).

## Diferencias Parte A vs Parte B

La evolución computacional requiere un cambio de paradigma algorítmico:
* **Parte A:** Era una simulación de estados. Se rastreaba *qué* celdas contenían un rayo (`BEAM` o `EMPTY`), siendo suficiente almacenar referencias al enumerado `TachyonCell`.
* **Parte B:** Es un problema de combinatoria. Como un rayo se divide en dos, simular cada rayo individualmente consumiría memoria y tiempo infinitos (Complejidad $O(2^N)$). La solución requiere transicionar hacia un enfoque de **Programación Dinámica**, donde lo que viaja de capa en capa no es la presencia del rayo, sino la *cantidad acumulada de caminos* que llegan a una celda (`long`).

## Evolución del Diseño y Nuevas Técnicas Utilizadas

* **Programación Dinámica (State-Space Reduction):** La arquitectura abandona el rastreo individual para adoptar un modelo de solapamiento de subproblemas. El *Record* `Step` evoluciona: en lugar de guardar una `List<TachyonCell>`, ahora encapsula una `List<Long> pathsToLayer`. La cantidad total de caminos que llegan a una celda en la fila $N$ es simplemente la suma de los caminos de las celdas conectadas en la fila $N-1$. Esto reduce la complejidad espacial y temporal a $O(W \times H)$.
* **Prevención de Desbordamiento (*Integer Overflow*):** Previendo el crecimiento exponencial de las ramificaciones, el tipo de dato subyacente para el conteo se migra tempranamente de primitivas de 32 bits (`int`) a 64 bits (`long`). Toda la cadena de valor (`mapToLong`, `sum()`) absorbe este cambio sin alterar la estructura funcional.
* **Composición de Funciones Puras y Alta Cohesión:** El cálculo del valor de una celda se divide en tres funciones puras independientes y descriptivas: `pathsFromAbove`, `pathsFromSplittingLeft`, y `pathsFromSplittingRight`. Esta segmentación elimina bloques condicionales masivos y permite que la función principal `countNewPaths` sea una simple suma aritmética declarativa.
* **Resiliencia Estructural (Recursión Inmutable):** A pesar del drástico cambio algorítmico, el motor central del sistema (el método recursivo `propagateBeam`) ha permanecido intacto en su forma. Sigue siendo una función recursiva de cola (*tail recursion*) que genera nuevos estados inmutables sin mutar variables externas, demostrando que el esqueleto arquitectónico diseñado en la Parte A era robusto y altamente escalable.