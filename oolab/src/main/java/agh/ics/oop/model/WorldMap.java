package agh.ics.oop.model;

import agh.ics.oop.model.observers.MapChangeListener;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * The interface responsible for interacting with the map of the world.
 * Assumes that Vector2d and MoveDirection classes are defined.
 *
 * @author apohllo, idzik
 */
public interface WorldMap extends MoveValidator {

    public List<Animal> getAnimalsAt(Vector2d position);

    public Map<Vector2d, List<Animal>> getAnimalHashMap();

    public Boundary getJungleBoundary();
    /**
     * Place a new animal on the map.
     *
     * @param animal The animal to be placed on the map.
     * @return True if the animal was placed. The rules for valid placement are the same as for movement.
     */
    void placeAnimal(Animal animal);

    void placeGrass(Grass grass);

    /**
     * Moves an animal (if it is present on the map) according to specified direction.
     * If the move is not possible, this method has no effect.
     */
    void move(Animal animal);

    /**
     * Return true if given position on the map is occupied. Should not be
     * confused with canMoveTo since there might be empty positions where the animal
     * cannot move.
     *
     * @param position Position to check.
     * @return True if the position is occupied.
     */
    boolean isOccupied(Vector2d position);

    /**
     * Return an animal at a given position.
     *
     * @param position The position of the animal.
     * @return animal or null if the position is not occupied.
     */
    WorldElement objectAt(Vector2d position);

    Collection<WorldElement> getElements();

    Boundary getBoundary();

    int getRowsCount();
    int getColumnsCount();

    void addObserver(MapChangeListener observer);

    void removeObserver(MapChangeListener observer);

    UUID getId();

    public Boundary getSouthSteppes();
    public Boundary getNorthSteppes();
    public Collection<Animal> getAnimalsList();
    public void removeAnimal(Animal animal);
    public void removeGrass(Grass grass);
    public Collection<Grass> getGrassesList();
    public boolean isOccupiedByAnimal(Vector2d position);
    public boolean isOccupiedByGrass(Vector2d position);
    public int animalsCountOnCell(Vector2d position);
    Collection<List<Animal>> getReproductionCells();
    public int getAnimalsCount();
    public int getGrassesCount();
    public int getEmptySpacesCount();
    //public void advanceDay();
}