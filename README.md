# Advent of Code 2025 - Ingeniería del Software II

Este repositorio contiene las soluciones para los retos del *Advent of Code 2025*, desarrolladas bajo un enfoque de **Ingeniería del Software II (ULPGC)**.

Más allá de la resolución algorítmica de los puzzles, el objetivo principal de este proyecto ha sido aplicar de forma práctica los **principios de diseño** y **patrones** vistos en la asignatura para gestionar la evolución de los requisitos (parte 1 → parte 2 de cada día) de la forma más resiliente posible.

---

## Filosofía de Diseño

### 1. Principios SOLID

* **Principio de Responsabilidad Única (SRP):** separación entre clases orquestadoras (`Dial`, `Warehouse`, `Reactor`, `Playground`, `MovieTheater`...) que coordinan el flujo, y clases de dominio (`Range`, `Position`, `Rectangle`, `Present`...) que encapsulan únicamente su propia lógica.
* **Principio Abierto/Cerrado (OCP):** en varios días, la entidad de dominio (`Range`, `Device`, `JunctionBox`, `Position`) se mantiene **sin modificar** entre la parte 1 y la parte 2, mientras que el comportamiento nuevo se añade en clases o implementaciones adicionales (por ejemplo, el `WorksheetReader` del día 6, que se extiende con una segunda implementación sin tocar la interfaz ni la primera).
* **Principio de Sustitución de Liskov (LSP):** las distintas implementaciones de una misma interfaz (como las dos versiones de `CephalopodWorksheetReader` del día 6) son intercambiables allí donde se espera un `WorksheetReader`, sin que el código cliente (`WorksheetSolver`) necesite saber cuál de ellas está usando.
* **Principio de Segregación de la Interfaz (ISP):** las interfaces del proyecto son deliberadamente pequeñas y de un único método (`WorksheetReader`, o las interfaces funcionales internas de los días 1 y 12), evitando obligar a una clase a implementar comportamiento que no necesita.
* **Principio de Inversión de Dependencias (DIP):** las clases orquestadoras dependen de abstracciones y no de implementaciones concretas; por ejemplo, `WorksheetSolver` solo conoce la interfaz `WorksheetReader`, nunca la clase concreta que la implementa.

### 2. Otros principios de diseño

* **Composición sobre Herencia:** en lugar de crear jerarquías de clases, el comportamiento variable se inyecta como un colaborador (por ejemplo, los enumerados `Direction` y `Angle` componen una función de rotación en vez de heredar de una clase base).
* **Ley de Demeter:** las clases solo interactúan con sus colaboradores directos, evitando cadenas largas de llamadas (`objeto.getX().getY().getZ()`).
* **Principio de no Repetir Código (DRY):** las clases de dominio compartidas entre la parte 1 y la parte 2 de un mismo día (`Range` del día 2, `Operation`/`Operator` del día 6, `TachyonCell`/`TachyonGrid` del día 7, `JunctionBox`/`Pair` del día 8, `Device` del día 11) se definen una sola vez y se reutilizan en ambas partes.
* **Convención sobre Configuración (CoC):** se sigue la estructura estándar de un proyecto Maven (`src/main/java` / `src/test/java`), con un paquete por día y por parte, y cada clase de test nombrada siguiendo la convención `<Clase>Test`, sin necesidad de configuración adicional.
* **Principio YAGNI:** cada clase expone únicamente la funcionalidad necesaria para resolver el puzzle correspondiente, evitando abstracciones genéricas o configurables pensadas para usos futuros que no se han llegado a necesitar.

### 3. Patrones de diseño aplicados

* **Factory Method:** la construcción de instancias se delega a métodos estáticos (`Dial.create()`, `Rotation.from()`, `Range.from()`, `Present.with()`, etc.) en lugar de exponer constructores públicos, lo que permite validar y centralizar la creación de objetos.
* **Iterator:** el día 6 (*Trash Compactor*) implementa explícitamente la interfaz `Iterator` de Java para recorrer las operaciones de la hoja de cálculo fila a fila (parte 1) o columna a columna (parte 2), sin exponer la estructura interna de los datos.

---

## Índice de Soluciones

|   Día    | Título                | Documentación           | Código Fuente | Principios y Patrones Aplicados |
|:--------:|-----------------------|-------------------------|---|---|
| **1.1**  | *Secret Entrance*     | [Doc](doc/day1part1.md) | [Main](src/main/java/software/aoc/day1/part1) | SRP, Factory Method. |
| **1.2**  | *Secret Entrance*     | [Doc](doc/day1part2.md) | [Main](src/main/java/software/aoc/day1/part2) | ISP, Composición sobre Herencia, Factory Method. |
| **2.1**  | *Gift Shop*           | [Doc](doc/day2part1.md) | [Main](src/main/java/software/aoc/day2/part1) | SRP, DRY, Factory Method. |
| **2.2**  | *Gift Shop*           | [Doc](doc/day2part2.md) | [Main](src/main/java/software/aoc/day2/part2) | OCP, DRY, Factory Method. |
| **3.1**  | *Lobby*               | [Doc](doc/day3part1.md) | [Main](src/main/java/software/aoc/day3/part1) | SRP, Factory Method. |
| **3.2**  | *Lobby*               | [Doc](doc/day3part2-b.md) | [Main](src/main/java/software/aoc/day3/part2) | SRP, Factory Method. |
| **4.1**  | *Printing Department* | [Doc](doc/day4part1.md) | [Main](src/main/java/software/aoc/day4/part1) | SRP, Factory Method. |
| **4.2**  | *Printing Department* | [Doc](doc/day4part2.md) | [Main](src/main/java/software/aoc/day4/part2) | OCP, DRY, Factory Method. |
| **5.1**  | *Cafeteria*           | [Doc](doc/day5part1.md) | [Main](src/main/java/software/aoc/day5/part1) | SRP, Factory Method. |
| **5.2**  | *Cafeteria*           | [Doc](doc/day5part2.md) | [Main](src/main/java/software/aoc/day5/part2) | SRP, Factory Method. |
| **6.1**  | *Trash Compactor*     | [Doc](doc/day6part1.md) | [Main](src/main/java/software/aoc/day6/part1) | SRP, ISP, DIP, Iterator, Factory Method. |
| **6.2**  | *Trash Compactor*     | [Doc](doc/day6part2.md) | [Main](src/main/java/software/aoc/day6/part2) | OCP, LSP, Iterator, DRY. |
| **7.1**  | *Laboratories*        | [Doc](doc/day7part1.md) | [Main](src/main/java/software/aoc/day7/part1) | SRP, DRY, Factory Method. |
| **7.2**  | *Laboratories*        | [Doc](doc/day7part2.md) | [Main](src/main/java/software/aoc/day7/part2) | OCP, DRY, Factory Method. |
| **8.1**  | *Playground*          | [Doc](doc/day8part1.md) | [Main](src/main/java/software/aoc/day8/part1) | SRP, DRY, Factory Method. |
| **8.2**  | *Playground*          | [Doc](doc/day8part2.md) | [Main](src/main/java/software/aoc/day8/part2) | OCP, DRY, Factory Method. |
| **9.1**  | *Movie Theater*       | [Doc](doc/day9part1.md) | [Main](src/main/java/software/aoc/day9/part1) | SRP, Factory Method. |
| **9.2**  | *Movie Theater*       | [Doc](doc/day9part2.md) | [Main](src/main/java/software/aoc/day9/part2) | SRP, Factory Method. |
| **10.1** | *Circuit Breaker*     | [Doc](doc/day10part1.md) | [Main](src/main/java/software/aoc/day10/part1) | SRP, Factory Method. |
| **10.2** | *Circuit Breaker*     | [Doc](doc/day10part2.md) | [Main](src/main/java/software/aoc/day10/part2) | SRP, Factory Method. |
| **11.1** | *Factory*             | [Doc](doc/day11part1.md) | [Main](src/main/java/software/aoc/day11/part1) | SRP, Factory Method. |
| **11.2** | *Factory*             | [Doc](doc/day11part2.md) | [Main](src/main/java/software/aoc/day11/part2) | OCP, DRY, Factory Method. |
|  **12**  | *Christmas Tree Farm* | [Doc](doc/day12.md)     | [Main](src/main/java/software/aoc/day12) | SRP, ISP, Composición sobre Herencia, DRY, Factory Method. |