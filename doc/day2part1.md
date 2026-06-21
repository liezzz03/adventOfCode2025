# Advent of Code 2025 - Day 2 Part 1: Gift Shop

Solución diseñada bajo los principios de **Ingeniería del Software II** de la ULPGC, enfocada en la creación de un modelo de dominio rico, la inmutabilidad y la eliminación de la complejidad ciclomática mediante programación declarativa.

## Modelado del Dominio y Lógica Estructural

* **Prevención de la Obsesión por los Tipos Primitivos (*Primitive Obsession*):** En lugar de tratar los identificadores como simples tipos `long` y los rangos como variables inconexas, se han elevado a entidades del dominio usando `Java Records` (`Product` y `Range`). Esto centraliza el comportamiento y aporta significado semántico al sistema.
* **Principio de Responsabilidad Única (SRP):**
    * **`GiftShopDatabase`:** Actúa como orquestador. Se encarga de parsear la entrada separada por comas y de coordinar el flujo de datos para sumar los identificadores inválidos.
    * **`Range`:** Encapsula los límites inferior y superior, y es responsable de generar su propia secuencia de identificadores mediante `LongStream.rangeClosed()`.
    * **`Product`:** Encapsula las reglas de negocio de un producto individual, sabiendo responder a la pregunta de si su ID es inválido o no.
* **Inmutabilidad Absoluta:** Todas las estructuras de datos (`Range`, `Product`, y la lista interna de `GiftShopDatabase`) son inmutables o finales, garantizando la ausencia de efectos secundarios.

## Clean Code y Paradigma Funcional

* **Pipeline de Datos Declarativo:** El cálculo principal en `sumInvalidIds()` se ha diseñado como un flujo continuo de datos usando el API de Streams. La operación `flatMapToLong` aplana eficientemente las secuencias generadas por los múltiples rangos en un único flujo de IDs, que luego son mapeados a objetos, filtrados y sumados de forma expresiva y legible.
* **Delegación de la Complejidad a Expresiones Regulares:** Para determinar si un identificador numérico está formado por una secuencia repetida dos veces (ej. `123123`), se ha evitado la creación de bucles y manipulaciones de `String` costosas. En su lugar, el método `Product.isInvalid()` utiliza el motor de *Regex* con la expresión `^([0-9]+)\1$`, capturando el grupo dinámicamente. Esto reduce la complejidad ciclomática del método a cero.
* **Diseño por Contrato (Tell, Don't Ask):** La base de datos no extrae los números de `Range` para iterarlos manualmente, sino que le pide al rango que le devuelva su *Stream*. De igual modo, no extrae el ID del `Product` para validarlo, sino que le pregunta directamente si es válido (`filter(Product::isInvalid)`).