package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Simulation implements  Runnable{
    private List<Animal> animals = new ArrayList<>();
    private final List<MoveDirection> moves;
    private final WorldMap map;

    public Simulation(List<Vector2d> positions, WorldMap map, List<MoveDirection> moves){
        this.moves = moves;
        this.map = map;

        for (Vector2d position : positions) {
            Animal animal = new Animal(position);
            try {
                map.place(animal);
                animals.add(animal);
            } catch (IncorrectPositionException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run(){
        // usunięcie martwych zwierzaków
        animals = animals.stream()
                .filter(animal -> !animal.hasStarved())
                .collect(Collectors.toList());

        // ruch wszystkich zwierzaków
        for (int i = 0; i < moves.size(); i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Animal animal = animals.get(i % animals.size());
            map.move(animal, moves.get(i));
        }

        // zjadzanie roślin


        // rozmnażanie

        // wzrost nowych roślin



    }

    public List<Animal> getAnimals() {
        return animals;
    }
}
