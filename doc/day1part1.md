# Advent of Code 2025 - Día 1 Part 1: Secret Entrance

Solución diseñada bajo los principios de **Ingeniería del Software II** de la ULPGC, priorizando la robustez arquitectónica, 
la inmutabilidad y la legibilidad sobre la implementación directa o la fuerza bruta.

### Patrones Creacionales
* **Factory Method:** Se utiliza para ocultar la lógica de instanciación y parseo. Ejemplos claros son `Dial.create()` 
para inicializar el estado del sistema, y `Rotation.from(String order)` para traducir la entrada de texto en objetos 
de dominio.

### Lógica Estructural y SOLID
* **Principio de Responsabilidad Única (SRP):**
    * **`Dial`:** Se encarga exclusivamente de orquestar la lista de rotaciones, calcular la posición final mediante 
  aritmética modular y contabilizar las veces que se alcanza la posición cero.
    * **`Rotation`:** Su única responsabilidad es interpretar las órdenes en formato texto (ej. "L50") y encapsular la 
  magnitud y la dirección matemática del movimiento.
* **Alta Modularidad y Bajo Acoplamiento:** La clase `Dial` es completamente agnóstica al formato de texto del input. El 
método `execute` actúa como un adaptador que delega en el constructor interno, protegiendo la lógica central de cambios 
en el formato de los datos de entrada.
* **Inmutabilidad:** El sistema está libre de efectos secundarios (*side-effects*). `Rotation` está implementado como un 
`Record` nativo de Java. Además, métodos como `add()` en el `Dial` devuelven siempre una nueva instancia en lugar de 
mutar la lista original, facilitando un entorno seguro para pruebas aisladas (*TDD*).

### Clean Code y Programación Declarativa
* **Paradigma Funcional:** Se ha sustituido la iteración imperativa clásica (bucles `for` o `while`) por el uso intensivo 
del API `Stream` de Java (`IntStream`, `map`, `filter`, `sum`).
* **Complejidad Ciclomática Nula:** Gracias al uso de aritmética modular (`% 100`) para simular la naturaleza circular 
del dial y a las operaciones de filtrado de los Streams, se ha evitado por completo el uso de bloques condicionales 
anidados (`if/else`).
* **Código Autodocumentado:** El uso de una *Fluent API* y métodos descriptivos (`sumPartial()`, `normalize()`, `count()`) 
permite que la lógica de negocio se lea de forma natural, haciendo innecesarios los comentarios explicativos.