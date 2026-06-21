# Advent of Code 2025 - Ingeniería del Software II

Este repositorio contiene las soluciones para los retos del *Advent of Code 2025*, desarrolladas bajo un enfoque estricto de **Ingeniería del Software II (ULPGC)**.

Más allá de la resolución algorítmica de los puzzles, el objetivo principal de este proyecto ha sido la aplicación práctica de **Principios SOLID**, **Clean Code**, **Paradigma Funcional** y **Arquitecturas Desacopladas** para gestionar la evolución de los requisitos de forma resiliente.

---

## Filosofía de Diseño y Arquitectura

El proyecto se centra en mantener una arquitectura estructurada, inmutable y altamente expresiva. Se ha priorizado la legibilidad y el correcto modelado del dominio sobre la micro-optimización imperativa prematura.

### 1. Principios SOLID y Modelado de Dominio
* **Single Responsibility Principle (SRP):** Estricta separación entre capas. Clases orquestadoras (`Playground`, `MovieTheater`) coordinan el flujo, mientras que las entidades de dominio (`Rectangle`, `Present`, `Range`) encapsulan la lógica física o matemática.
* **Open/Closed Principle (OCP):** El diseño ha demostrado estar abierto a la extensión y cerrado a la modificación. Múltiples segundas partes (como el Día 4b o 6b) se resolvieron añadiendo nuevo comportamiento sin alterar las clases centrales del dominio.
* **Tell, Don't Ask:** En lugar de utilizar las clases como contenedores pasivos de datos (getters/setters), se les envían mensajes para que ejecuten su propio comportamiento (ej. pedirle a un `Range` si contiene a otro).
* **Prevención de *Primitive Obsession*:** Uso intensivo de `Records` para elevar enteros sueltos a entidades con significado semántico (ej. `Position`, `RedTile`, `JoltageState`).

### 2. Paradigma Funcional y Clean Code
* **Inmutabilidad Absoluta:** Ausencia total de efectos secundarios (*side-effects*). Se evita el uso de listas mutables o matrices globales modificables. Cada transformación o paso recursivo genera nuevas instancias del estado.
* **Programación Declarativa:** Uso avanzado de la **API de Streams** de Java. Se han eliminado bucles anidados `for/while` en favor de productos cartesianos aplanados (`flatMap`), evaluación perezosa (`takeWhile`, cortocircuitos) y reducciones customizadas (`reduce`).
* **Patrones de Diseño Aplicados:**
    * **Factory Method:** Construcción de grafos e instancias delegada a métodos estáticos como `.with()` o `.from()`.
    * **Strategy:** Evitar `switch/if` masivos delegando comportamiento polimórfico a enumerados (ej. rotaciones del Día 12, direcciones del Día 1).
    * **Null Object Pattern:** Manejo seguro de casos base evitando `NullPointerException` (ej. `Rectangle.Null`).
    * **Memoization / Programación Dinámica:** Caché de estados inmutables en algoritmos recursivos costosos para evitar explosiones combinatorias.

---

## Uso de la IA

Se ha utilizado inteligencia artificial como herramienta de apoyo académico para los siguientes procesos:
* Asistencia en la redacción, revisión y estructuración de la documentación técnica en formato Markdown.
* Al iniciar el proyecto, se utilizó para descubrir estrategias funcionales y patrones de la API de Streams de Java. Con la adquisición de experiencia, su uso evolucionó a corroborar y optimizar flujos declarativos.

---

## Índice de Soluciones

|   Día    | Título                | Documentación | Código Fuente | Técnicas y Estrategias Aplicadas |
|:--------:|-----------------------|---|---|---|
| **1.1**  | *Secret Entrance*     | [Doc](doc/day01-a.md) | [Main](src/main/java/software/aoc/day1/part1) | Modelado de Dominio, Tell Don't Ask, SRP. |
| **1.2**  | *Secret Entrance*     | [Doc](doc/day01-b.md) | [Main](src/main/java/software/aoc/day1/part2) | Strategy, Inyección de Dependencias, Polymorphism, LSP. |
| **2.1**  | *Gift Shop*           | [Doc](doc/day02-a.md) | [Main](src/main/java/software/aoc/day2/part1) | Encapsulación Regex, Eliminación Complejidad Ciclomática. |
| **2.2**  | *Gift Shop*           | [Doc](doc/day02-b.md) | [Main](src/main/java/software/aoc/day2/part2) | OCP (Open/Closed Principle), Mantenibilidad Declarativa. |
| **3.1**  | *Lobby*               | [Doc](doc/day03-a.md) | [Main](src/main/java/software/aoc/day3/part1) | Funciones Reductoras Declarativas, Sanitización Defensiva. |
| **3.2**  | *Lobby*               | [Doc](doc/day03-b.md) | [Main](src/main/java/software/aoc/day3/part2) | State Object (Selector), Streams Generativos (`iterate`). |
| **4.1**  | *Printing Department* | [Doc](doc/day04-a.md) | [Main](src/main/java/software/aoc/day4/part1) | Travesía Declarativa de Matrices (`flatMap`), Rich Domain. |
| **4.2**  | *Printing Department* | [Doc](doc/day04-b.md) | [Main](src/main/java/software/aoc/day4/part2) | Simulación Inmutable (Autómata Celular), Proyección Lógica. |
| **5.1**  | *Cafeteria*           | [Doc](doc/day05-a.md) | [Main](src/main/java/software/aoc/day5/part1) | Short-Circuiting Evaluation (`anyMatch`), Value Objects. |
| **5.2**  | *Cafeteria*           | [Doc](doc/day05-b.md) | [Main](src/main/java/software/aoc/day5/part2) | Algoritmo Merge Intervals, Null Object Pattern, Fold Funcional. |
| **6.1**  | *Trash Compactor*     | [Doc](doc/day06-a.md) | [Main](src/main/java/software/aoc/day6/part1) | Iterator Pattern, Lazy Evaluation, Strategy (Operadores). |
| **6.2**  | *Trash Compactor*     | [Doc](doc/day06-b.md) | [Main](src/main/java/software/aoc/day6/part2) | OCP Perfecto (cero cambios al dominio), Transposición de Matrices. |
| **7.1**  | *Laboratories*        | [Doc](doc/day07-a.md) | [Main](src/main/java/software/aoc/day7/part1) | Recursividad Inmutable, Evasión Primitive Obsession. |
| **7.2**  | *Laboratories*        | [Doc](doc/day07-b.md) | [Main](src/main/java/software/aoc/day7/part2) | Programación Dinámica, Reducción Espacio de Estados. |
| **8.1**  | *Playground*          | [Doc](doc/day08-a.md) | [Main](src/main/java/software/aoc/day8/part1) | Estructuras Disjoint Sets, Producto Cartesiano Funcional. |
| **8.2**  | *Playground*          | [Doc](doc/day08-b.md) | [Main](src/main/java/software/aoc/day8/part2) | Algoritmo Kruskal Declarativo, Retención de Estados. |
| **9.1**  | *Movie Theater*       | [Doc](doc/day09-a.md) | [Main](src/main/java/software/aoc/day9/part1) | Geometría Funcional, Expresividad de Búsqueda de Máximos. |
| **9.2**  | *Movie Theater*       | [Doc](doc/day09-b.md) | [Main](src/main/java/software/aoc/day9/part2) | Ray-Casting/Scanline, Búsqueda Binaria, Evaluación Perezosa. |
| **10.1** | *Circuit Breaker*     | [Doc](doc/day10-a.md) | [Main](src/main/java/software/aoc/day10/part1) | Bitwise Operations de alta eficiencia, Parseo Regex Funcional. |
| **10.2** | *Circuit Breaker*     | [Doc](doc/day10-b.md) | [Main](src/main/java/software/aoc/day10/part2) | Memoización, Poda Heurística (Pruning), Optionals Funcionales. |
| **11.1** | *Factory*             | [Doc](doc/day11-a.md) | [Main](src/main/java/software/aoc/day11/part1) | Búsqueda en Profundidad (DFS) Inmutable, Listas Adyacencia. |
| **11.2** | *Factory*             | [Doc](doc/day11-b.md) | [Main](src/main/java/software/aoc/day11/part2) | Patrón Memoize, Transiciones Puras, State Object Pattern. |
|  **12**  | *Christmas Tree Farm* | [Doc](doc/day12-a.md) | [Main](src/main/java/software/aoc/day12) | 2D Bin Packing, Strategy de Ángulos, Heurística Largest-First. |

---
*Proyecto realizado como parte de la asignatura de Ingeniería del Software II - ULPGC.*