package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PriorityHandler {
// handler dostaje pozycje na mapie i zwraca,
// ktore zwierzeta kopuluja/ ktore zwierzeta jedza

    private static boolean compareAnimals(Animal a, Animal b) {
        if (a.getEnergy() != b.getEnergy())
            return a.getEnergy() > b.getEnergy();
        if (a.getAge() != b.getAge())
            return a.getAge() > b.getAge();
        if (a.getKidsCount() != b.getKidsCount())
            return a.getKidsCount() > b.getKidsCount();
        return Math.random() <= 0.5;
    }

    public static List<Animal> whoBreeds(List<Animal> animals) {

        if (animals == null || animals.isEmpty()) {
            return new ArrayList<>();
        }

        if (animals.size() == 1) {
            return new ArrayList<>(animals);
        }

        Animal a = animals.get(0);
        Animal b = animals.get(1);

        if (compareAnimals(b, a)) {
            Animal temp = a;
            a = b;
            b = temp;
        }

        for (int i = 2; i < animals.size(); i++) {
            Animal c = animals.get(i);
            if (compareAnimals(c, a)) {
                b = a;
                a = c;
            } else if (compareAnimals(c, b)) {
                b = c;
            }
        }

        List<Animal> bestTwo = new ArrayList<>();
        bestTwo.add(a);
        bestTwo.add(b);
        return bestTwo;
    }

    public static Animal whoEats(List<Animal> animals) {
        List<Animal> bestTwo = whoBreeds(animals);
        return bestTwo.getFirst();
    }
}
