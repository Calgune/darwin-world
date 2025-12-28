package agh.ics.oop.model;

import agh.ics.oop.model.maps.CylindricalMap;
import agh.ics.oop.model.util.MapSettings;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CylindricalMapTest {
    @Test
    void checkVerticalWrap() {
        MapSettings settings = new MapSettings.Builder()
                .boundary(new Boundary(new Vector2d(0, 0), new Vector2d(4, 4)))
                .genomeLength(1)
                .initialAnimalEnergy(100)
                .build();
        CylindricalMap map = new CylindricalMap(settings);

        Animal animal = new Animal(new Vector2d(4, 4), new Genome(List.of(0)), 100);
        animal.setDirection(MapDirection.EAST);

        animal.move(map);
        assertEquals(new Vector2d(0, 4), animal.getPosition());

        animal.setDirection(MapDirection.NORTH);
        animal.move(map);
        assertEquals(new Vector2d(0, 4), animal.getPosition());
        assertEquals(MapDirection.SOUTH, animal.getDirection());

        animal.setPosition(new Vector2d(0, 4));
        animal.setDirection(MapDirection.NORTH_WEST);
        animal.move(map);
        assertEquals(new Vector2d(0, 4), animal.getPosition());
        assertEquals(MapDirection.SOUTH_WEST, animal.getDirection());
    }

    @Test
    void checkHorizontalBoundaries() {
        MapSettings settings = new MapSettings.Builder()
                .boundary(new Boundary(new Vector2d(0, 0), new Vector2d(4, 4)))
                .genomeLength(1)
                .initialAnimalEnergy(100)
                .build();
        CylindricalMap map = new CylindricalMap(settings);

        Animal southAnimal = new Animal(new Vector2d(2, 0), new Genome(List.of(0)), 100);
        southAnimal.setDirection(MapDirection.SOUTH);
        southAnimal.move(map);
        assertEquals(new Vector2d(2, 0), southAnimal.getPosition());
        assertEquals(MapDirection.NORTH, southAnimal.getDirection());

        Animal westAnimal = new Animal(new Vector2d(0, 2), new Genome(List.of(0)), 100);
        westAnimal.setDirection(MapDirection.WEST);
        westAnimal.move(map);
        assertEquals(new Vector2d(4, 2), westAnimal.getPosition());

        Animal seAnimal = new Animal(new Vector2d(1, 0), new Genome(List.of(0)), 100);
        seAnimal.setDirection(MapDirection.SOUTH_EAST);
        seAnimal.move(map);
        assertEquals(new Vector2d(1, 0), seAnimal.getPosition());
        assertEquals(MapDirection.NORTH_EAST, seAnimal.getDirection());
    }

    @Test
    void checkCorners() {
        MapSettings settings = new MapSettings.Builder()
                .boundary(new Boundary(new Vector2d(0, 0), new Vector2d(4, 4)))
                .genomeLength(1)
                .initialAnimalEnergy(100)
                .build();
        CylindricalMap map = new CylindricalMap(settings);

        Animal cornerAnimal = new Animal(new Vector2d(4, 4), new Genome(List.of(0)), 100);
        cornerAnimal.setDirection(MapDirection.NORTH_EAST);
        cornerAnimal.move(map);

        assertEquals(new Vector2d(4, 4), cornerAnimal.getPosition());
        assertEquals(MapDirection.SOUTH_EAST, cornerAnimal.getDirection());
    }
}
