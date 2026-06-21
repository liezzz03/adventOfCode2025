# Advent of Code 2025 - Day 9 Part 2: Movie Theater

En la segunda parte del reto, la restricción geométrica aumenta drásticamente: ya no basta con encontrar el rectángulo más grande delimitado por dos losetas rojas, sino que este rectángulo debe estar **estrictamente contenido dentro del perímetro** del teatro (un polígono irregular formado por dichas losetas). Esto convierte el problema en un desafío clásico de geometría computacional (*Shape-in-Polygon*).

## Diferencias Parte A vs Parte B

La evolución de los requisitos transforma un problema combinatorio en un motor de cálculo espacial:
* **Parte A:** Evaluaba el área de cualquier rectángulo generado, ignorando si atravesaba áreas no válidas. Su búsqueda de máximos era exhaustiva (calculaba todos y se quedaba con el mayor).
* **Parte B:** Introduce el concepto de frontera o perímetro (`Polygon`). Exige validar que todo el ancho del rectángulo, en todas sus alturas críticas, recaiga en intervalos interiores del polígono.

## Evolución del Diseño y Nuevas Técnicas Utilizadas

* **Optimización por Evaluación Perezosa y Cortocircuito:** Dado que validar si un rectángulo está dentro de un polígono es computacionalmente costoso, se ha invertido el paradigma de búsqueda. En `MovieTheater`, en lugar de validar todos los rectángulos y luego buscar el de mayor área, la arquitectura primero ordena el *Stream* de mayor a menor área (`sorted(comparingLong(r -> -r.area()))`) y luego delega en `findFirst()`. Gracias a esta evaluación perezosa, el sistema evalúa la geometría solo hasta encontrar el primer rectángulo válido (que, por definición, será el más grande), ahorrando miles de cálculos innecesarios.
* **Modelado Espacial Avanzado (Ray-Casting / Scanline):** Para resolver la contención del rectángulo, se ha encapsulado la complejidad topológica en la clase `Polygon`. El algoritmo implementado emula una técnica de trazado de rayos (*Scanline*):
    * Interseca líneas horizontales (Y) con los bordes del polígono (`xOfEdgesIntersectingAt`).
    * Agrupa las intersecciones ordenadas en intervalos (`Interval`) que representan el interior del polígono (`interiorSpansAt`).
    * Comprueba si el ancho del rectángulo (`horizontalSpan`) cabe enteramente en uno de esos intervalos interiores.
* **Búsqueda Binaria (*Binary Search*) para Rendimiento:** Para garantizar que ninguna esquina cóncava del polígono invada el rectángulo desde dentro, el sistema debe comprobar las alturas (Y) críticas. En lugar de iterar linealmente sobre todos los vértices, el método `startIndexFor` utiliza `Arrays.binarySearch` sobre un array de coordenadas Y previamente ordenado. Esto reduce el tiempo de búsqueda de $O(N)$ a $O(\log N)$ para cada validación.
* **Inmutabilidad Geométrica Reafirmada:** Se ha introducido el *Record* `Interval` para abstraer la lógica matemática de rangos unidimensionales. En conjunto con la inmutabilidad de `Tile`, `Rectangle` y `Polygon`, toda la validación espacial se ejecuta a través de funciones puras (sin mutar estados), lo que blinda al sistema contra condiciones de carrera o corrupción de datos lógicos durante el análisis topológico.