package agh.ics.oop.model;
import agh.ics.oop.model.util.MapSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Animal implements WorldElement {
    private MapDirection direction;
    private Vector2d position;
    // narazie
    private int energy;
    private int age;
    private int kidsCount;
    private final Genotype genotype;

    public Animal(Vector2d position, Genotype genes, int energy) {
        this.position = position;
        this.direction = MapDirection.values()[(int) (Math.random() * 8)];
        this.energy = energy;
        this.genotype = genes;
    }

    @Override
    public String toString() {
        return switch (direction) {
            case NORTH -> "N";
            case SOUTH -> "S";
            case NORTH_EAST -> "NE";
            case EAST -> "E";
            case WEST -> "W";
            case SOUTH_EAST -> "SE";
            case SOUTH_WEST -> "SW";
            case NORTH_WEST -> "NW";
        };
    }

    public boolean isAt(Vector2d position) {
        return Objects.equals(this.position, position);
    }

    public void setDirection(MapDirection newDirection) {
        this.direction = newDirection;
    } // potrzebne do testow

    public void move(MoveValidator moveValidator) {
        int rotation = this.genotype.todaysMove();
        this.direction = this.direction.changeDirection(rotation);
        Vector2d moveVector = this.direction.toUnitVector();
        Vector2d positionAfterMove = this.position.add(moveVector);
        Vector2d targetPosition = moveValidator.nextPosition(positionAfterMove);

        if (moveValidator.canMoveTo(positionAfterMove)) {
            this.position = targetPosition;
        } else {
            this.direction = this.direction.bounceOfTheWall();
            moveVector = this.direction.toUnitVector();
            positionAfterMove = this.position.add(moveVector);
            targetPosition = moveValidator.nextPosition(positionAfterMove);

            if (moveValidator.canMoveTo(positionAfterMove)) {
                this.position = targetPosition;
            }
        }
    }

    public Animal breed(Animal partner, int reproductionCost) {
        //zawsze this.animal silniejszy od partner dzieki filozofii w PriorityHandler
        int energy1 = this.energy;
        int energy2 = partner.getEnergy();

        int genomeLength = this.genotype.length();
        int ourGenomeRatio = (genomeLength * energy1) / (energy1 + energy2);
        int partnersGenomeRatio = genomeLength - ourGenomeRatio;
        boolean ourOnLeft = Math.random() < 0.5;
        Random random = new Random();
        int randomAmount = random.nextInt(genomeLength+1);

        List<Integer> childGenes = new ArrayList<>();

        if (ourOnLeft) {
            childGenes.addAll(this.genotype.getPart(0, ourGenomeRatio));
            childGenes.addAll(partner.getGenotype().getPart(ourGenomeRatio, genomeLength));
        } else {
            childGenes.addAll(this.genotype.getPart(partnersGenomeRatio, genomeLength));
            childGenes.addAll(partner.getGenotype().getPart(0, partnersGenomeRatio));
        }

        for (int i = 0; i < randomAmount; i++) {
            int mutationIndex = random.nextInt(genomeLength);
            int newValue = random.nextInt(8);

            childGenes.set(mutationIndex, newValue);
        }

        Genotype childGenome = new Genotype(childGenes);
        this.kidsCount++;
        partner.kidsCount++;
        this.energy -= reproductionCost;
        partner.energy -= reproductionCost;

        return new Animal(this.position, childGenome, 2 * reproductionCost);
}


    @Override
    public Vector2d getPosition(){
        return position;
    }

    public Genotype getGenotype() {
        return this.genotype;
    }

    public void eat(int energyFromGrass){
        energy += energyFromGrass;
    }

    public void loseEnergy(int decay) {
        energy -= decay;
    }

    public MapDirection getDirection() {
        return direction;
    }

    public boolean hasStarved(){
        return energy <= 0;
    }

    public int getEnergy() {
        return energy;
    }

    public int getAge() {
        return age;
    }

    public int getKidsCount() {
        return kidsCount;
    }
}
