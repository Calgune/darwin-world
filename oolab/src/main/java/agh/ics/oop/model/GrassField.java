package agh.ics.oop.model;

import agh.ics.oop.World;

import javax.lang.model.element.Element;
import javax.swing.*;
import java.awt.font.NumericShaper;
import java.util.*;

public class GrassField extends AbstractWorldMap{
    private final Map<Vector2d, Grass> grasses = new HashMap<>();

    public GrassField(int grassCount) {
        final int size = (int)Math.sqrt(grassCount*10);
        RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator(size, size, grassCount);

        Iterator<Vector2d> iterator = randomPositionGenerator.iterator();
        while (iterator.hasNext()){
            Vector2d position = iterator.next();
            grasses.put(position, new Grass(position));
        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return super.isOccupied(position) || grasses.containsKey(position);
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        // zwierzeta maja priorytet do wyświetlania wiec są zwracane pierwsze
        if(animals.containsKey(position))
            return super.objectAt(position);
        return grasses.get(position);
    }

    // w przypadku braku jakichkolwiek elementów na mapie, rozmiar będzie 1x1
    // bo niewprowadznie zwierzaków nie jest błędem, więc nie powinno to być osłubiwane

    @Override
    public Collection<WorldElement> getElements(){
         Collection<WorldElement> collection = super.getElements();
         collection.addAll(grasses.values());
         return collection;
    }

    @Override
    public Boundary getCurrentBound() {

        Vector2d upperRight = new Vector2d(Integer.MIN_VALUE, Integer.MIN_VALUE);
        Vector2d lowerLeft = new Vector2d(Integer.MAX_VALUE, Integer.MAX_VALUE);

        Collection<WorldElement> collection = getElements();

        if (collection.isEmpty()){
            upperRight = new Vector2d(0,0);
            lowerLeft = new Vector2d(0, 0);
        }

        for(WorldElement element : collection){
            upperRight = upperRight.upperRight(element.getPosition());
            lowerLeft = lowerLeft.lowerLeft(element.getPosition());
        }

        return new Boundary(lowerLeft, upperRight);
    }


    /**
     * funkcja wyłącznie do testowania!
     * umiesza trawę na mapie
     * funckja powinna zawsze być użwyana przed dodaniem jakiegolowiek zwierzecia
     * aby odzwierciedlić działanie symulacji
     * @param grass
     */
    public void placeGrass(Grass grass){
        grasses.put(grass.getPosition(), grass);
    }




}
