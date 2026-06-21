# Advent of Code 2025 - Day 6 Part 1: Trash Compactor

Solución diseñada bajo los principios de **Ingeniería del Software II** de la ULPGC, destacando el uso avanzado de patrones de diseño de comportamiento, el manejo de colecciones mediante iteradores personalizados y la evaluación perezosa (*lazy evaluation*).

## Patrones de Diseño y Modelado Estructural

* **Patrón Strategy (Polimorfismo sobre Condicionales):** Para resolver la evaluación de las operaciones matemáticas, se ha evitado el uso de sentencias `switch` o `if/else`. En su lugar, el enumerado `Operator` implementa el patrón Strategy almacenando referencias a funciones puras (`Function<LongStream, Long>`). De este modo, evaluar un operador es tan sencillo e inmutable como delegar en su función asociada (`LongStream::sum` o la reducción de multiplicación).
* **Patrón Iterator:** Dado que la lectura de la hoja de cálculo requiere cruzar datos de múltiples filas para formar una única columna lógica (los operandos arriba y el operador al final), la clase `CephalopodWorksheetReader` encapsula esta complejidad implementando su propio `RowIterator`. Esto permite extraer operaciones de forma secuencial sin cargar matrices pesadas en memoria.
* **Principio de Inversión de Dependencias (DIP de SOLID):** La clase orquestadora `WorksheetSolver` no depende de la implementación concreta del lector (`CephalopodWorksheetReader`), sino de la abstracción `WorksheetReader` (que a su vez extiende `Iterable<Operation>`). Esto garantiza que el sistema pueda adaptarse a nuevos formatos de entrada en el futuro sin modificar la lógica de resolución.

## Clean Code y Paradigma Funcional

* **Diseño por Contrato (Tell, Don't Ask):** Se respeta estrictamente la encapsulación en el flujo de ejecución. El `WorksheetSolver` pide a la `Operation` que le devuelva su resultado. La `Operation`, a su vez, no inspecciona qué operador tiene para hacer el cálculo, sino que le pasa sus operandos al `Operator` (`operator.apply(...)`). Cada clase es responsable de su propio comportamiento.
* **Evaluación Perezosa con Streams (*Lazy Evaluation*):** En lugar de cargar todas las operaciones en una lista tradicional antes de procesarlas, el `WorksheetSolver` convierte el iterador en un flujo de datos dinámico utilizando `StreamSupport.stream(reader.spliterator(), false)`. Las operaciones se procesan bajo demanda, transformándose en `long` y sumándose al vuelo, lo que optimiza el uso de memoria RAM.
* **Inmutabilidad y *Records*:** La clase `Operation` se define como un `Record`, garantizando que una vez el iterador ensambla un operador con sus operandos, este conjunto de datos sea final e inmutable, previniendo efectos secundarios durante la fase de cálculo.