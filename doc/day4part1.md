# Advent of Code 2025 - Day 4 Part 1: Printing Department

Solución diseñada bajo los principios de **Ingeniería del Software II** de la ULPGC, demostrando cómo aplicar la programación funcional y un modelado de dominio rico para resolver problemas clásicos de matrices bidimensionales y vecindad espacial.

## Modelado Espacial y Diseño Orientado a Objetos

* **Estructuras de Datos Inteligentes (Rich Domain Model):** Se ha evitado el anti-patrón de usar un simple par de primitivas `(x, y)` para las coordenadas. En su lugar, el *Record* `Position` encapsula su estado y su propio comportamiento espacial. Es la propia posición quien sabe cómo generar su vecindad de Moore (los 8 puntos adyacentes) mediante el método `neighbors()`, aliviando a la clase `Warehouse` de la carga de calcular *offsets* espaciales.
* **Principio de Responsabilidad Única (SRP):**
    * **`ForkliftManager`:** Funciona estrictamente como un orquestador o fachada. Define el punto de entrada y coordina la llamada para contar los rollos accesibles, sin conocer los
