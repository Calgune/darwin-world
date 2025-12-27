package integration;

import agh.ics.oop.model.*;
import agh.ics.oop.model.maps.CylindricalMap;
import agh.ics.oop.model.maps.MoveValidator;
import agh.ics.oop.model.util.MapSettings;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnimalMovementTest {
    @Test
    void testAnimalMovement() {
        MapSettings settings = new MapSettings.Builder()
                .boundary(new Boundary(new Vector2d(0, 0), new Vector2d(4, 4)))
                .genomeLength(6)
                .initialAnimalEnergy(100)
                .build();

        MoveValidator map = new CylindricalMap(settings);
        Genome genes = new Genome(List.of(0, 4, 2, 6, 1, 5));
        Animal animal = new Animal(new Vector2d(2, 2), genes, settings.getInitialAnimalEnergy());
        animal.setDirection(MapDirection.NORTH);

        animal.move(map);
        assertEquals(new Vector2d(2, 3), animal.getPosition());
        assertEquals(MapDirection.NORTH, animal.getDirection());

        animal.move(map);
        assertEquals(new Vector2d(2, 2), animal.getPosition());
        assertEquals(MapDirection.SOUTH, animal.getDirection());

        animal.move(map);
        assertEquals(new Vector2d(1, 2), animal.getPosition());
        assertEquals(MapDirection.WEST, animal.getDirection());

        animal.move(map);
        assertEquals(new Vector2d(1, 1), animal.getPosition());
        assertEquals(MapDirection.SOUTH, animal.getDirection());
    }
}
