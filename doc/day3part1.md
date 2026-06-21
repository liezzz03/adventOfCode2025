# Advent of Code 2025 - Day 3 Part 1: Lobby

Solución diseñada bajo los principios de **Ingeniería del Software II** de la ULPGC, priorizando la delegación de responsabilidades, la robustez frente a entradas anómalas y el uso intensivo del paradigma funcional para la resolución de algoritmos de optimización.

## Modelado del Dominio y Diseño por Contrato

* **Principio de Responsabilidad Única (SRP):**
    * **`EscalatorEmergencyPower`:** Actúa como el orquestador principal. Su única responsabilidad es procesar el texto en bruto, instanciar los bancos de baterías y totalizar el voltaje del sistema.
    * **`BatteryBank`:** Encapsula un array de baterías individuales y contiene toda la lógica de negocio necesaria para encontrar la combinación óptima (el máximo voltaje posible respetando el orden secuencial).
* **Tell, Don't Ask:** El orquestador no extrae el array de baterías para calcular el voltaje por su cuenta. En su lugar, se limita a enviar un mensaje (`maxJoltage()`) a cada banco, confiando en que estos saben cómo calcular su propia aportación al sistema. Esto garantiza un alto nivel de encapsulamiento.
* **Inmutabilidad:** Al igual que en días anteriores, el modelado del dominio se apoya en `Java Records` para definir los bancos de baterías, asegurando que su estado no sea alterado una vez instanciado.

## Paradigma Funcional y Algoritmia Declarativa

* **Resolución Declarativa de Búsqueda:** Para calcular el `maxJoltage`, se debe encontrar el valor máximo y, posteriormente, el valor máximo del subconjunto restante. En lugar de utilizar bucles anidados (`for`) y variables de estado mutables para rastrear los índices, se ha implementado un enfoque puramente funcional:
    * **Uso de `reduce`:** El método `bestFirstBatteryIndex` utiliza `IntStream.range().reduce(this::pickBest)` para encontrar el índice del valor más alto de forma declarativa.
    * **Streams Delimitados:** Para el segundo valor, se utiliza `Arrays.stream(batteries, start, end).max()`, lo que permite acotar la búsqueda al sub-array válido sin extraer ni copiar datos en memoria, log