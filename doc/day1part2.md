# Advent of Code 2025 - Day 1 Part 2: Secret Entrance

En la segunda parte del problema, la funcionalidad del sistema evoluciona: el dial ya no solo evalúa su posición final, 
sino que debe calcular cuántas veces ha cruzado la posición `0` durante toda la trayectoria de la rotación.

## Diferencias Parte A vs Parte B

La evolución de los requisitos entre las partes ilustra la flexibilidad y resiliencia del diseño original:
* **Parte A:** Contabilizaba paradas estáticas (si la posición final tras ejecutar una rotación era exactamente 0).
* **Parte B:** Contabiliza **cruces dinámicos** por el 0. Dado que una rotación puede tener una magnitud mayor al tamaño 
del dial (ej. `L500`), el sistema ahora calcula matemáticamente cuántas vueltas completas (y por ende, cruces por cero) 
se han realizado en un solo movimiento.

## Evolución del Diseño y Nuevas Técnicas Utilizadas

Para soportar la nueva lógica de cruces dinámicos sin ensuciar la clase principal, se ha realizado una refactorización 
profunda aplicando principios avanzados de diseño orientado a objetos:

* **Replace Conditional with Polymorphism:** Se ha creado el enumerado `Direction` para manejar el sentido del giro 
(`LEFT`, `RIGHT`). Esto elimina por completo la necesidad de sentencias `if/else` o `switch` para decidir qué fórmula 
matemática aplicar, delegando el comportamiento polimórficamente a cada valor del enumerado.
* **Tell, Don't Ask:** Se ha invertido el flujo de control para proteger la encapsulación. La clase `Dial` ya no extrae 
la magnitud y la dirección de la rotación para hacer el cálculo externamente. En su lugar, le "ordena" a la rotación que 
lo calcule pasándole el contexto necesario: `rotations.get(i).crossingsFrom(startPosition)`. A su vez, `Rotation` delega 
directamente en `Direction`.
* **Patrón Strategy e Inyección de Dependencias:** Se ha definido la interfaz funcional privada `RotationStrategy` dentro 
de `Direction`. Las fórmulas matemáticas concretas para calcular los cruces se inyectan en la creación de los enumerados 
mediante lambdas `(start, magnitude) -> ...` y referencias a métodos (`Direction::leftCrossing`).
* **Principio de Sustitución de Liskov (LSP):** El diseño garantiza que las clases consumidoras (`Dial`, `Rotation`) 
desconocen absolutamente la implementación matemática de los cruces. Solo dependen del contrato establecido por 
`calculateCrossings(int start, int magnitude)`, garantizando una alta cohesión y permitiendo escalar el sistema sin alterar 
la lógica de las capas superiores.