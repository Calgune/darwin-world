package agh.ics.oop.model.util;

import agh.ics.oop.model.Animal;

import java.util.ArrayList;
import java.util.List;

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

        if (animals == null || animals.isEmpty()) {
            return null;
        }

        Animal a = animals.get(0);

        for (int i = 1; i < animals.size(); i++) {
            Animal b = animals.get(i);
            if (compareAnimals(b, a)) {
                a = b;
            }
        }

        return a;
    }
}
