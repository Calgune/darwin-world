package agh.ics.oop.model;

import agh.ics.oop.model.observers.MapChangeListener;
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
    // private int day;

    public AbstractWorldMap(MapSettings settings){
        this.settings = settings;
        this.id = UUID.randomUUID();
        this.jungleBoundary = calculateJungleBoundary();
        this.northSteppes = calculateNorthSteppes();
        this.southSteppes = calculateSouthSteppes();
        // this.day = 0;
    }

    @Override
    public List<Animal> getAnimalsAt(Vector2d position) {
        List<Animal> animalsAtPos = this.animals.get(position);

        if (animalsAtPos == null || animalsAtPos.isEmpty()) {
            return List.of(); // niemodyfikowalna pusta
        }

        return animalsAtPos;
    }

    public Map<Vector2d, List<Animal>> getAnimalHashMap() {
        return this.animals;
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

    @Override
    public void mapChanged(String message){
        for(MapChangeListener observer : observers){
            observer.mapChanged(this, message);
        }
    }

    @Override
    public void placeAnimal(Animal animal){
        animals.computeIfAbsent(animal.getPosition(), _ -> new ArrayList<>())
                .add(animal);
        mapChanged(String.format("zwierze zostalo umieszczone na %s", animal.getPosition()));
    }

    @Override
    public void placeGrass(Grass grass){
        grasses.put(grass.getPosition(), grass);
        mapChanged(String.format("trawa zostala umieszczone na %s", grass.getPosition()));
    }

    @Override
    public void move(Animal animal) {
        if(animals.containsKey(animal.getPosition())){
            List<Animal> fromCellList = animals.get(animal.getPosition());

            Vector2d previousPosition = animal.getPosition();
            MapDirection previousDirection = animal.getDirection();

            fromCellList.remove(animal);
            animal.move(this);
            animals.computeIfAbsent(animal.getPosition(), _ -> new ArrayList<>())
                    .add(animal);

            mapChanged(String.format("zwierze zostalo przesuniete z %s %s na %s %s", previousPosition, previousDirection,
                    animal.getPosition(), animal.getDirection()));
        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return  isOccupiedByAnimal(position) || isOccupiedByGrass(position);
    }

    @Override
    public boolean isOccupiedByAnimal(Vector2d position){
        return animals.containsKey(position) && !animals.get(position).isEmpty();
    }

    @Override
    public boolean isOccupiedByGrass(Vector2d position){
        return grasses.containsKey(position);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.getY() >= 0 && position.getY() <= getBoundary().upperRight().getY();
    }

    @Override
    public Vector2d wrapAround(Vector2d position) {
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
                max(Comparator.comparingInt(Animal::getEnergy))
                    .orElse(null);
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
    public Collection<Animal> getAnimalsList(){
        List<Animal> result = new ArrayList<>();
        for (List<Animal> list : animals.values()) {
            result.addAll(list);
        }
        return result;
    }

    @Override
    public Collection<List<Animal>> getReproductionCells(){
        return animals.values()
                .stream()
                .filter(animalsList -> !animalsList.isEmpty())
                .map(animalsList -> animalsList.stream()
                        .filter(animal -> animal.getEnergy() > settings.getReproductionEnergyThreshold())
                        .toList()
                )
                .filter(animalsList -> animalsList.size() >= 2)
                .toList();
    }

    @Override
    public int getAnimalsCount() {
        return getAnimalsList().size();
    }

    @Override
    public int getGrassesCount() {
        return getGrassesList().size();
    }

    @Override
    public int getEmptySpacesCount() {
        return Math.toIntExact(settings.getBoundary().getCellsCount() - (animals.values().stream()
                .filter(animalsList ->
                        !animalsList.isEmpty() &&
                                !isOccupiedByGrass(animalsList.getFirst().getPosition()))
                .count() + grasses.size()));
    }

    @Override
    public Map<Vector2d, Integer> getNeighborsDensityMap(){
        Map<Vector2d, Integer> neighborsDensity = new HashMap<>();

        getAnimalsList().forEach(animal -> {
            Boundary boundary = settings.getBoundary();
            int R = settings.getDistanceToTransferHeat();
            int x = animal.getPosition().getX();
            int y = animal.getPosition().getY();

            for (int i = x - R; i < x + R + 1; i++){
                for (int j = y - R; j < y + R + 1; j++){
                    Vector2d position = new Vector2d(i, j);
                    //IO.println(position);
                    if (i == x && j == y && canMoveTo(position)) continue;
                    neighborsDensity.put(position,
                            neighborsDensity.getOrDefault(position, 0) + 1);
                }
            }
        });
        return neighborsDensity;
    }


    @Override
    public Collection<Grass> getGrassesList(){
        return grasses.values();
    }

    @Override
    public void removeAnimal(Animal animal){
        List<Animal> animalList = animals.get(animal.getPosition());
        if(animalList == null || animalList.isEmpty()){
            return;
        }
        animalList.remove(animal);
    }

    @Override
    public void removeGrass(Grass grass){
        if(!grasses.containsKey(grass.getPosition())){
            return;
        }
        grasses.remove(grass.getPosition());
    }

    @Override
    public int animalsCountOnCell(Vector2d position){
        return animals.get(position).size();
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
