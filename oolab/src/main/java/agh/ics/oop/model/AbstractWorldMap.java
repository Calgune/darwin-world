package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;
import agh.ics.oop.model.util.MapSettings;

import java.util.*;

public abstract class AbstractWorldMap implements WorldMap{
    protected final Map<Vector2d, List<Animal>> animals = new HashMap<>();
    protected final Map<Vector2d, Grass> grasses = new HashMap<>();
    protected final MapVisualizer mapVisualizer = new MapVisualizer(this);
    protected  final List<MapChangeListener> observers = new ArrayList<>();
    private final UUID id;
    private final MapSettings settings;
    private final Boundary jungleBoundary;
    private final Boundary northSteppes;
    private final Boundary southSteppes;

    public AbstractWorldMap(MapSettings settings){
        this.settings = settings;
        this.id = UUID.randomUUID();
        this.jungleBoundary = calculateJungleBoundary();
        this.northSteppes = calculateNorthSteppes();
        this.southSteppes = calculateSouthSteppes();
    }

    private Boundary calculateJungleBoundary() {
        Boundary mapBoundary = settings.getBoundary();
        int height = mapBoundary.upperRight().getY() - mapBoundary.lowerLeft().getY() + 1;
        int jungleHeight = (int)(height * 0.2f);

        int bottomY = height / 2 - jungleHeight / 2;

        return new Boundary(new Vector2d(0, bottomY),
                new Vector2d(mapBoundary.upperRight().getX(), bottomY+jungleHeight));
    }

    private Boundary calculateNorthSteppes(){
        return new Boundary(new Vector2d(jungleBoundary.lowerLeft().getX(), jungleBoundary.upperRight().getY()+1),
                settings.getBoundary().upperRight());
    }

    private Boundary calculateSouthSteppes(){
        return new Boundary(settings.getBoundary().lowerLeft(),
                new Vector2d(jungleBoundary.upperRight().getX(), jungleBoundary.lowerLeft().getY()-1));
    }

    @Override
    public Boundary getJungleBoundary(){
        return jungleBoundary;
    }

    @Override
    public int getRowsCount(){
        return getBoundary().upperRight().getX()-getBoundary().lowerLeft().getX()+1;
    }

    @Override
    public int getColumnsCount(){
        return getBoundary().upperRight().getY()-getBoundary().lowerLeft().getY()+1;
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
    public void place(Animal animal){
        animals.computeIfAbsent(animal.getPosition(), _ -> new ArrayList<>())
                .add(animal);
        mapChanged(String.format("zwierze zostalo umieszczone na %s", animal.getPosition()));
    }

    @Override
    public void place(Grass grass){
        grasses.put(grass.getPosition(), grass);
        mapChanged(String.format("trawa zostala umieszczone na %s", grass.getPosition()));
    }

    @Override
    public void move(Animal animal, MoveDirection direction) {
        if(animals.containsKey(animal.getPosition())){
            List<Animal> fromCellList = animals.get(animal.getPosition());

            Vector2d previousPosition = animal.getPosition();
            MapDirection previousDirection = animal.getDirection();

            fromCellList.remove(animal);
            animal.move(direction, this);
            animals.computeIfAbsent(animal.getPosition(), _ -> new ArrayList<>())
                    .add(animal);

            mapChanged(String.format("zwierze zostalo przesuniete z %s %s na %s %s", previousPosition, previousDirection,
                    animal.getPosition(), animal.getDirection()));
        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return (animals.containsKey(position) && !animals.get(position).isEmpty()) || grasses.containsKey(position);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.getY() >= 0 && position.getY() < getBoundary().upperRight().getY();
    }

    @Override
    public Vector2d nextPosition(Vector2d position) {
        int newX = position.getX();
        int width = getBoundary().upperRight().getX();

        if (newX < 0) {
            newX = width;
        } else if (newX >= width) {
            newX = 0;
        }

        return new Vector2d(newX, position.getY());
    }

    @Override
    public String toString(){
        return mapVisualizer.draw(getBoundary().lowerLeft(),
                getBoundary().upperRight());
    }

    @Override
    public WorldElement objectAt(Vector2d position){
        List<Animal> animalList = animals.get(position);

        if(animalList == null || animalList.isEmpty()){
            return grasses.get(position);
        }
        
        return animalList.stream().
                max(Comparator.comparingInt(Animal::getEnergy)).
                get();
    }

    @Override
    public Collection<WorldElement> getElements(){
        List<WorldElement> elements = new ArrayList<>();
        for(List<Animal> animalList : animals.values()){
            elements.addAll(animalList);
        }
        elements.addAll(grasses.values());
        return elements;
    }

    @Override
    public Boundary getBoundary(){
        return settings.getBoundary();
    }

    @Override
    public Boundary getSouthSteppes() {
        return southSteppes;
    }
    @Override
    public Boundary getNorthSteppes() {
        return northSteppes;
    }
}
