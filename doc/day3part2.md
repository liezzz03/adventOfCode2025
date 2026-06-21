# Advent of Code 2025 - Day 3 Part 2: Lobby

En la segunda parte del problema, la complejidad algorítmica aumenta significativamente: ya no basta con encontrar las dos mejores baterías, sino que el sistema debe extraer una **secuencia óptima de 12 dígitos** manteniendo su orden relativo original para maximizar el voltaje total. Además, el tamaño de este número hace obligatorio el salto a enteros de 64 bits (`long`).

## Diferencias Parte A vs Parte B

La evolución estructural demuestra cómo abstraer un algoritmo de búsqueda local a uno secuencial de ventana móvil (*sliding window*):
* **Parte A:** Se buscaban solo dos elementos mediante la división directa del array en dos segmentos (antes y después del primer máximo).
* **Parte B:** Se requiere iterar 12 veces, asegurando en cada paso que queden suficientes elementos en el array para completar la secuencia. Esto introduce una fuerte dependencia de estado (índice actual de búsqueda y elementos restantes) que debe ser gestionada sin ensuciar la lógica funcional.

## Evolución del Diseño y Nuevas Técnicas Utilizadas

* **Encapsulación de Estado con Clases Internas (`Selector`):** Para evitar la creación de bucles `for` o `while` con variables mutables y mantener la pureza funcional de la clase `BatteryBank`, se ha introducido la clase `Selector`. Actúa como un objeto de estado o iterador inteligente que encapsula el progreso de la búsqueda (`currentDigitIndex` y `searchStartIndex`).
* **Inmutabilidad en la Transición de Estado:** La clase `Selector` es completamente inmutable. Al avanzar al siguiente dígito, el método `next()` no muta las variables internas, sino que devuelve una *nueva* instancia del selector con los límites actualizados, un enfoque puramente funcional que previene errores por efectos secundarios.
* **Streams Generativos Avanzados (`Stream.iterate`):** Se ha reemplazado la lógica secuencial imperativa por `Stream.iterate(Selector.start(this), Selector::hasNext, Selector::next)`. Este pipeline genera exactamente 12 instancias de `Selector`, extrae el mejor valor en esa iteración y une el resultado final en un `String`, manteniendo la complejidad ciclomática en cero.
* **Algoritmia Acotada (Sliding Window):** La regla de negocio exige mantener el orden relativo. El cálculo inteligente de los límites en `batteryBank.size() - REQUIRED_DIGITS + currentDigitIndex` garantiza que el buscador de máximos nunca elija un dígito tan cerca del final del array que impida seleccionar el resto de los 12 dígitos obligatorios.
* **Tolerancia a Desbordamientos Numéricos (Integer Overflow):** La base del sistema se ha refactorizado ágilmente para operar con tipos `long` (mediante `mapToLong` y `Long.parseLong`), protegiendo a la aplicación de desbordamientos al tratar con el resultado de 12 dígitos. El orquestador superior (`EscalatorEmergencyPower`) absorbe este cambio sin alterar su arquitectura gracias a su diseño cerrado a la modificación pero abierto a la extensión (OCP).