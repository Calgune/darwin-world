package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.util.MapSettings;
import com.sun.scenario.Settings;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Simulation implements  Runnable{
    private List<Animal> animals = new ArrayList<>();
    private final WorldMap map;
    private final MapSettings settings;

    public Simulation(List<Vector2d> positions, WorldMap map, int genomeLength, MapSettings settings){ //POPRAWIC
//     public Simulation(List<Vector2d> positions, WorldMap map, List<MoveDirection> moves, MapSettings settings){
//         this.moves = moves;
        this.map = map;
        this.settings = settings;

        for (Vector2d position : positions) {
            Genotype genes = Genotype.generateRandom(genomeLength);
            Animal animal = new Animal(position, genes);
            try {
                map.place(animal);
                animals.add(animal);
            } catch (IncorrectPositionException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run(){ // bedzie trzeba to zamienic na while True. Na razie dam tutaj po
        // prostu jakas stala.

        // usunięcie martwych zwierzaków
        // dodanie traw

        int grassInJungle = (int)(Math.ceil(settings.getInitialGrassCount()*0.8f));;
        int grassOnSteppes = (int)(Math.floor(settings.getInitialGrassCount()*0.2f));

        RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator(map.getJungleBoundary(), grassInJungle);
        randomPositionGenerator.forEach(position -> {map.place(new Grass(position));});

        randomPositionGenerator = new RandomPositionGenerator(map.getNorthSteppes(), (int)Math.ceil(grassOnSteppes/ 2.0f));
        randomPositionGenerator.forEach(position -> {map.place(new Grass(position));});

        randomPositionGenerator = new RandomPositionGenerator(map.getSouthSteppes(), (int)Math.floor(grassOnSteppes/ 2.0f));
        randomPositionGenerator.forEach(position -> {map.place(new Grass(position));});

        // dodanie zwierzaków
//         positions.forEach(position -> {
//             Animal animal = new Animal(position);
//             map.place(animal);
//             animals.add(animal);
//         });
      
        for (int i = 0; i < 10; i++) { // ten for powinien obejmowac wszystkie metody,
        // ale dopoki ich nie ma to rusza tylko zwierzakami.
        for (Animal animal : animals) {
            map.move(animal);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    }

//     @Override
//     public void run(){
//         // 1 usunięcie martwych zwierzaków
//         // zwierzeta trzeba jeszcze z mapy usunąć
//         animals = animals.stream()
//             .filter(animal -> !animal.hasStarved())
//             .collect(Collectors.toList());

//         // ruch wszystkich zwierzaków
//         for (int i = 0; i < 10; i++) { // ten for powinien obejmowac wszystkie metody,
//             // ale dopoki ich nie ma to rusza tylko zwierzakami.
//             for (Animal animal : animals) {
//                 map.move(animal);
//                 try {
//                     Thread.sleep(1000);
//                 } catch (InterruptedException e) {
//                     throw new RuntimeException(e);
//                 }
//             }
//         }

        // 3 zjadzanie roślin
            // aktualizowanie energi

            // usunięcie rośliny


        // 4 rozmnażanie
            // aktualizowanie energi

            // usunięcie rośliny


        // 5 wzrost nowych roślin

        // 6 utrata energi zewzględu na przeminięcie dnia



    }

    public List<Animal> getAnimals() {
        return animals;
    }
}
