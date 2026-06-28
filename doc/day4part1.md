# Advent of Code 2025 - Day 4 Part 1: Printing Department

Solución diseñada bajo los principios de **Ingeniería del Software II** de la ULPGC, demostrando cómo aplicar la programación funcional y un modelado de dominio rico para resolver problemas clásicos de matrices bidimensionales y vecindad espacial.

## Modelado Espacial y Diseño Orientado a Objetos

* **Estructuras de Datos Inteligentes (Rich Domain Model):** Se ha evitado el anti-patrón de usar un simple par de primitivas `(x, y)` para las coordenadas. En su lugar, el *Record* `Position` encapsula su estado y su propio comportamiento espacial. Es la propia posición quien sabe cómo generar su vecindad de Moore (los 8 puntos adyacentes) mediante el método `neighbors()`, aliviando a la clase `Warehouse` de la carga de calcular *offsets* espaciales.
* **Principio de Responsabilidad Única (SRP):**
    * **`ForkliftManager`:** Funciona estrictamente como un orquestador o fachada. Define el punto de entrada y coordina la llamada para contar los rollos accesibles, sin conocer los detalles del mapeo interno.
    * **`Warehouse`:** Actúa como el contenedor lógico de la cuadrícula. Conoce los límites del mapa (`isValid`) y las reglas de accesibilidad (`MaxAdjacentRolls`).
* **Eliminación de *Magic Numbers*:** El criterio lógico para determinar si un rollo es accesible o no se ha extraído a una constante semántica (`MaxAdjacentRolls = 4`), mejorando drásticamente la legibilidad y facilitando futuros cambios en las reglas de negocio.

## Travesía Declarativa de Matrices (Grid Traversal)

* **Supresión de Bucles Anidados:** El procesamiento clásico de matrices en 2D suele requerir bucles imperativos anidados (`for(row) { for(col) { ... } }`), lo que aumenta la complejidad ciclomática y reduce la legibilidad. En esta implementación, se ha aplanado la cuadrícula utilizando `IntStream` y `flatMap`.
  * En `Warehouse`, la generación de coordenadas se logra declarativamente combinando el índice de las filas con la longitud de las columnas.
  * En `Position`, la generación de vecinos se logra cruzando rangos de `-1 a 1` de forma puramente funcional, filtrando el punto central (`!p.equals(this)`).

## Robustez y Tolerancia a Fallos del Sistema

* **Sanitización Defensiva de la Entrada:** Basado en la experiencia de días anteriores, el método `diagramWith` integra un *pipeline* de sanitización riguroso antes de convertir el texto a la matriz booleana. Al aplicar `split("\\r?\\n")`, `trim()` y descartar líneas vacías, el sistema garantiza un rectángulo perfecto (matriz no irregular) y se inmuniza contra excepciones de desbordamiento de índices (`ArrayIndexOutOfBoundsException`) provocadas por los retornos de carro invisibles de diferentes sistemas operativos.
