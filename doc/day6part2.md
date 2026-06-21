# Advent of Code 2025 - Day 6 Part 2: Trash Compactor

En la segunda parte del reto, la topología de los datos de entrada cambia radicalmente: la hoja de cálculo de los cefalópodos pasa de leerse en formato de filas horizontales a leerse en bloques de columnas verticales (leyéndose de derecha a izquierda).

## Diferencias Parte A vs Parte B

El cambio de orientación de los datos pone a prueba la resiliencia de la arquitectura:
* **Parte A:** El iterador (`RowIterator`) leía líneas completas separadas por espacios, extrayendo los operandos y el operador final.
* **Parte B:** El iterador (`ColumnIterator`) debe agrupar columnas de caracteres adyacentes hasta encontrar una columna en blanco, aislar el operador al final de la cadena y reconstruir los operandos filtrando el "ruido" visual.

## Evolución del Diseño y Nuevas Técnicas Utilizadas

* **Principio de Abierto/Cerrado (OCP de SOLID):** Este es el triunfo arquitectónico de la solución. El hecho de que **solo haya cambiado la clase lectora** demuestra que el sistema estaba perfectamente cerrado a la modificación en su lógica de negocio. Clases como `WorksheetSolver`, `Operation` y `Operator` permanecieron intactas, demostrando que su dominio era completamente agnóstico al formato de entrada de los datos.
* **Transformación de Dominio (Transposición de Matrices):** En lugar de crear un iterador complejo que navegue por los índices `(x, y)` de una cuadrícula bidimensional de forma no lineal, se aplicó una transformación funcional temprana: `transposeColumnsIn()`. Al transponer la matriz (convertir columnas en filas y leerlas en orden inverso), el problema tridimensional se aplana, permitiendo reutilizar la lógica de lectura secuencial estándar de Java.
* **Tolerancia a Fallos y Limpieza de Ruido (Regex):** Dado que la lectura vertical generaba "ruido" (espacios de relleno y caracteres no numéricos al unir las columnas), se implementó programación defensiva utilizando expresiones regulares. El método `replaceAll("[^0-9]", "")` purga cualquier carácter visual indeseado, garantizando que el paso de conversión `Long::parseLong` nunca arroje excepciones por formato inválido.
* **Evaluación Perezosa y Agrupación Dinámica:** La extracción de una operación completa ya no se basa en un solo `split`, sino en el consumo bajo demanda de elementos del iterador mediante `Stream.generate(...).takeWhile(col -> !col.isBlank())`. Este enfoque de *consumo por lotes* permite procesar operandos de ancho variable sin cargar todo el archivo en memoria.