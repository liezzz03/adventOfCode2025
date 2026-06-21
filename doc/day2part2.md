# Advent of Code 2025 - Day 2 Part 2: Gift Shop

En la segunda parte del reto, la regla de negocio para clasificar un identificador de producto como "inválido" evoluciona: ya no se buscan únicamente secuencias repetidas exactamente dos veces, sino secuencias que se repitan **dos o más veces** consecutivas.

## Diferencias Parte A vs Parte B

La transición entre la Parte A y la Parte B es el ejemplo perfecto de cómo una buena arquitectura minimiza drásticamente el impacto del cambio ante la evolución de los requisitos:
* **Parte A:** Validaba repeticiones dobles estrictas con la expresión regular `^([0-9]+)\1$`.
* **Parte B:** Valida repeticiones múltiples añadiendo simplemente el cuantificador de una o más repeticiones (`+`), quedando la expresión como `^([0-9]+)\1+$`.

## Principios de Diseño Demostrados

* **Principio de Abierto/Cerrado (OCP de SOLID):** El diseño ha demostrado estar abierto a la extensión (nuevas reglas de validación) pero cerrado a la modificación en sus capas superiores o coordinadoras. Las clases `GiftShopDatabase` y `Range` han permanecido intactas, demostrando que su lógica central (orquestar y generar flujos de datos) es completamente agnóstica a la semántica de qué hace a un producto válido o no.
* **Alta Cohesión y Encapsulamiento de Reglas de Negocio:** El cambio en los requisitos del dominio ha impactado única y exclusivamente a la entidad responsable de conocer esa información: el *Record* `Product`. Esto certifica que las responsabilidades estaban correctamente aisladas desde la primera iteración del diseño.
* **Mantenibilidad y Escalabilidad Declarativa:** Al haber delegado la comprobación de patrones numéricos al motor de *Regex* en lugar de implementar algoritmos imperativos (manipulación de *substrings* o bucles anidados), adaptar la lógica a "múltiples repeticiones" requirió modificar un único carácter en el código fuente. Se mantiene la complejidad ciclomática en cero y la intencionalidad del código clara.