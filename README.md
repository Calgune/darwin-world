# Darwin World

An animal evolution simulation written in Java with a JavaFX interface. Animals roam the map, look for grass, reproduce and pass their genes on (with small mutations). You can watch the population change from day to day and see which strategies survive.

The project was made for the **Object-Oriented Programming** course (AGH University of Krakow, Computer Science).

## Authors

- Paweł Pochwalski
- Justyna Konior

## Video

A short video showing how the application works:

https://github.com/user-attachments/assets/303ab0a5-631a-4d8a-bb95-317e23c03dc4


## How the simulation works

The world is a map shaped like a cylinder: the left and right edges are connected, while the top and bottom are walls. The middle strip of the map is a jungle where grass grows more often; the rest is steppe.

A typical day looks like this:

1. Animals lose energy (daily cost of living).
2. Each animal moves according to its genome.
3. Animals standing on grass eat it and regain energy.
4. Animals with enough energy reproduce. The child gets a mix of its parents' genes, and some genes may mutate.
5. New grass grows on the map.

Dead animals disappear from the map, but their lifespan is still counted in the statistics.

### Modes

- **Normal**: constant conditions throughout the simulation.
- **Seasonal**: alternating summer and winter. In winter the temperature drops and animals have to cope with a higher cost of living. The length of the seasons, the minimum temperature and the distance over which heat spreads are set in the configuration.

## What you can do in the program

- set all simulation parameters (map size, number of animals and grass, energies, reproduction costs, genome length, mutation range and more),
- use ready-made presets: *Bezpłodni*, *Indie*, *Postapo*,
- save and load the configuration from a `.txt` file,
- run several simulations at once, each in its own window,
- pause and resume the simulation,
- watch live statistics: number of animals and grass, empty cells, most popular genotype, average energy, average lifespan and average number of children,
- view charts of these values over time (including temperature in seasonal mode),
- after pausing, click an animal to follow its genome, energy, age, number of children, descendants and plants eaten,
- save the statistics from each day to a CSV file.

## Running

You need **JDK 25** (Gradle will try to provision it if needed). Libraries, including JavaFX and OpenCSV, are downloaded automatically.

```bash
git clone https://github.com/Calgune/Darwin-World.git
cd Darwin-World/oolab
./gradlew run
```

On Windows use `gradlew.bat` instead of `./gradlew`.

You can also open the project in IntelliJ IDEA, wait for Gradle to sync and run the `application > run` task.

If IntelliJ won't start the application from the green arrow, go to the settings (`Build Tools > Gradle`) and set *Build and run using: Gradle*.

## Technologies

- Java 25
- JavaFX 25
- Gradle
- OpenCSV
- JUnit 5

## Project structure

The code is split into packages under `agh.ics.oop`:

- `model` – animals, grass, genomes, the map and its settings,
- `model.maps` – map implementations,
- `model.simulation` – the simulation engine and logic (normal and seasonal),
- `model.observers` – observers of changes on the map and in the statistics,
- `model.util` – helper classes, including CSV writing and configuration file handling,
- `presenter` – JavaFX view controllers.

The views are described in FXML files in `src/main/resources`.
