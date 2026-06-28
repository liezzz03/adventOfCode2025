# Advent of Code 2025 - Day 5 Part 1: Cafeteria

Solución diseñada bajo los principios de **Ingeniería del Software II** de la ULPGC, destacando el uso de expresiones de cortocircuito (*short-circuiting*), la delegación de responsabilidades a través de objetos de dominio y la programación defensiva frente a entradas de datos inestables.

## Modelado del Dominio y Encapsulación

* **Abstracción de Intervalos Matemáticos:** Se utiliza el `Record` inmutable `Range` para abstraer el concepto de intervalo numérico. En lugar de manejar los límites superior e inferior de forma aislada en la lógica de negocio, `Range` encapsula estas propiedades y provee una semántica rica.
* **Diseño por Contrato (Tell, Don't Ask):** La clase orquestadora `FreshIngredientIdentifier` no rompe el encapsulamiento de los rangos para extraer sus valores (`start` o `end`) y hacer comparaciones externas. En su lugar, simplemente le pasa el identificador al rango y le pregunta si lo contiene delegando la validación matemática: `r.includes(i)`.
* **Principio de Responsabilidad Única (SRP):** * `Range` se encarga exclusivamente de parsearse a sí mismo desde una cadena (`X-Y`) y de la lógica de contención escalar.
    * `FreshIngredientIdentifier` gestiona el ciclo de vida, dividiendo el texto en bloques y coordinando el flujo de los identificadores contra la colección de rangos.

## Paradigma Funcional y Optimización Declarativa

* **Evaluación Perezosa y Cortocircuito (*Short-Circuiting*):** Para determinar si un ingrediente es fresco, basta con que pertenezca a **un solo rango** de la lista. En lugar de usar bucles imperativos con `break`, se hace uso del método `anyMatch()` de la API de Streams (`ranges.stream().anyMatch(...)`). Esta es una operación de cortocircuito: en cuanto la condición se cumple para el primer rango válido, Java detiene la iteración del resto de la lista, optimizando el rendimiento del sistema de forma elegante y declarativa.
* **Conversión Segura de Tipos:** El flujo de validación transforma la entrada directamente a primitivos de 64 bits mediante `mapToLong(Long::parseLong)`, previniendo problemas de memoria y desbordamiento antes de realizar las comprobaciones lógicas.

## Robustez y Programación Defensiva

* **Sanitización de Datos Integrada:** A raíz de los problemas clásicos de lectura de ficheros (como los saltos de línea `\r\n` de Windows o líneas en blanco al final de los archivos), los *pipelines* de construcción y conteo incluyen etapas explícitas de limpieza estructural: `split("\\r?\\n")`, `trim()` y `!s.isEmpty()`. Esto convierte a la lógica de negocio en un sistema invulnerable ante anomalías de formato originadas por el sistema operativo o el portapapeles.