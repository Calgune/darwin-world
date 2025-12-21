package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.*;

public abstract class AbstractWorldMap implements WorldMap{
    protected final Map<Vector2d, Animal> animals = new HashMap<>();
    protected final MapVisualizer mapVisualizer = new MapVisualizer(this);
    protected  final List<MapChangeListener> observers = new ArrayList<>();
    private final UUID id;

    public AbstractWorldMap(){
        this.id = UUID.randomUUID();
    }

    @Override
    public int getRowsCount(){
        return getCurrentBound().upperRight().getX()-getCurrentBound().lowerLeft().getX()+1;
    }

    @Override
    public int getColumnsCount(){
        return getCurrentBound().upperRight().getY()-getCurrentBound().lowerLeft().getY()+1;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void addObserver(MapChangeListener observer){
        observers.add(observer);
    }

    @Override
    public void removeObserver(MapChangeListener observer){
        observers.remove(observer);
    }

    public void mapChanged(String message){
        for(MapChangeListener observer : observers){
            observer.mapChanged(this, message);
        }
    }

    @Override
    public void place(Animal animal) throws IncorrectPositionException{
        if(canMoveTo(animal.getPosition())){
            animals.put(animal.getPosition(), animal);
            mapChanged(String.format("zwierze zostalo umieszczone na %s", animal.getPosition()));
        }
        else throw new IncorrectPositionException(animal.getPosition());
    }

    @Override
    public void move(Animal animal, MoveDirection direction) {
        if(animals.containsKey(animal.getPosition())){
            Vector2d previousPosition = animal.getPosition();
            MapDirection previousDirection = animal.getDirection();

            animals.remove(animal.getPosition());
            animal.move(direction, this);
            animals.put(animal.getPosition(), animal);

            mapChanged(String.format("zwierze zostalo przesuniete z %s %s na %s %s", previousPosition, previousDirection,
                    animal.getPosition(), animal.getDirection()));
        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return animals.containsKey(position);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !animals.containsKey(position);
    }

    @Override
    public String toString(){
        return mapVisualizer.draw(getCurrentBound().lowerLeft(),
                                  getCurrentBound().upperRight());
    }

    @Override
    public WorldElement objectAt(Vector2d position){
        return animals.get(position);
    }

    @Override
    public Collection<WorldElement> getElements(){
        return new ArrayList<>(animals.values());
    }

    @Override
    abstract public Boundary getCurrentBound();
}
