package agh.ics.oop;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.Grass;
import agh.ics.oop.model.PriorityHandler;
import agh.ics.oop.model.WorldMap;
import agh.ics.oop.model.util.MapSettings;
import com.sun.scenario.Settings;

import java.util.List;

public class SeasonalSimulation extends AbstractSimulation{
    private SeasonManager seasonManager;

    public SeasonalSimulation(WorldMap map, MapSettings settings){
        super(map, settings);
        seasonManager = new SeasonManager(settings);
    }

    @Override
    public void handleGrasses(){
        List<Grass> toRemove = map.getGrassesList().
                stream().
                filter(grass -> map.isOccupiedByAnimal(grass.getPosition())).
                toList();

        toRemove.forEach(grass -> {
            map.removeGrass(grass);
            List<Animal> animals = map.getAnimalsAt(grass.getPosition());
            Animal animal = PriorityHandler.whoEats(animals);
            animal.eat((int) (settings.getEnergyFromGrass()*seasonManager.getEnergyFromGrassModifier(day)));
        });
    }

//    @Override
//    public void breedAnimals(){
//        map.getReproductionCells().forEach(animals -> {
//            List<Animal> parents = PriorityHandler.whoBreeds(animals);
//            if (parents.size() == 2) {
//                Animal child = parents
//                        .getFirst()
//                        .breed(parents.get(1), settings.getReproductionEnergyCost());
//                map.placeAnimal(child);
//            }
//        });
//    }

    @Override
    public void growGrasses(){
        spawnGrasses((int) (settings.getNewDailyGrasses()*seasonManager.getGrassSpawnModifier(day)));
    }

    @Override
    public void loseEnergy(){
        map.getAnimalsList().forEach(animal -> {
            animal.loseEnergy((int) (settings.getDailyEnergyDecay()*seasonManager.getEnergyDecayModifier(day)));
            animal.age();
        });
    }

}
