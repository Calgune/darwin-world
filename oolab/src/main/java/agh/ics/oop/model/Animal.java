package agh.ics.oop.model;

import java.util.*;

import agh.ics.oop.model.maps.MoveValidator;
import agh.ics.oop.model.util.MapSettings;
import javafx.scene.paint.Color;

public class Animal implements WorldElement {
    private MapDirection direction;
    private Vector2d position;
    // narazie
    private int energy;
    private int age;
    private int kidsCount;
    private final Genome genome;
    private int plantsEaten;
    private Integer deathDay = null;
    private final List<Animal> children = new ArrayList<>();

    public Animal(Vector2d position, Genome genes, int energy) {
        this.position = position;
        this.direction = MapDirection.values()[(int) (Math.random() * 8)];
        this.energy = energy;
        this.age = 0;
        this.plantsEaten = 0;
        this.kidsCount = 0;
        this.genome = genes;
    }

    public void addChildren(Animal child) {
        this.children.add(child);
    }

    public void age(){
        age++;
    }

    @Override
    public String toString() {
        return "\uD83D\uDC3A";
    }

    public Color getColor(int dominantGenotype) {
        if (this.getGenome().getGenotype() == dominantGenotype) {
            return Color.rgb(211, 175, 55);
        }
        return Color.rgb(102, 51, 0);
    }

    public boolean isAt(Vector2d position) {
        return Objects.equals(this.position, position);
    }

    public void setPosition(Vector2d newPosition) {
        this.position = newPosition;
    }

    public void setDirection(MapDirection newDirection) {
        this.direction = newDirection;
    } // potrzebne do testow

    public void move(MoveValidator moveValidator) {
        int rotation = this.genome.todaysMove();
        this.direction = this.direction.changeDirection(rotation);
        Vector2d moveVector = this.direction.toUnitVector();
        Vector2d positionAfterMove = this.position.add(moveVector);

        if (moveValidator.canMoveTo(positionAfterMove)) {
            this.position = moveValidator.wrapAround(positionAfterMove);
        } else {
            this.direction = this.direction.bounceOfTheWall();
        }
    }

    public Animal breed(Animal partner, MapSettings settings) {
        //zawsze this.animal silniejszy od partner dzieki filozofii w PriorityHandler
        int energy1 = this.energy;
        int energy2 = partner.getEnergy();

        int reproductionCost = settings.getReproductionEnergyCost();
        int minMutationLength = settings.getMinMutationLength();
        int maxMutationLength = settings.getMaxMutationLength();
        int genomeLength = this.genome.length();
        int ourGenomeRatio = (genomeLength * energy1) / (energy1 + energy2);
        int partnersGenomeRatio = genomeLength - ourGenomeRatio;
        boolean ourOnLeft = Math.random() < 0.5;
        Random random = new Random();
        int randomAmount = random.nextInt(minMutationLength, maxMutationLength);

        List<Integer> childGenes = new ArrayList<>();

        if (ourOnLeft) {
            childGenes.addAll(this.genome.getPart(0, ourGenomeRatio));
            childGenes.addAll(partner.getGenome().getPart(ourGenomeRatio, genomeLength));
        } else {
            childGenes.addAll(this.genome.getPart(partnersGenomeRatio, genomeLength));
            childGenes.addAll(partner.getGenome().getPart(0, partnersGenomeRatio));
        }

        for (int i = 0; i < randomAmount; i++) {
            int mutationIndex = random.nextInt(genomeLength);
            int newValue = random.nextInt(8);

            childGenes.set(mutationIndex, newValue);
        }

        Genome childGenome = new Genome(childGenes);
        this.kidsCount++;
        partner.kidsCount++;
        this.energy -= reproductionCost;
        partner.energy -= reproductionCost;

        Animal child = new Animal(this.position, childGenome, 2 * reproductionCost);
        this.addChildren(child);
        partner.addChildren(child);

        return child;
}

    public int countDescendants() {
        Set<Animal> allDescendants = new HashSet<>();
        List<Animal> q = new ArrayList<>(this.children);

        int index = 0;
        while (index < q.size()) {
            Animal curr = q.get(index);
            index++;

            if (!allDescendants.contains(curr)) {
                allDescendants.add(curr);
                q.addAll(curr.children);
            }
        }
        return allDescendants.size();
    }


    @Override
    public Vector2d getPosition(){
        return position;
    }

    public Genome getGenome() {
        return this.genome;
    }

    public Integer getDeathDay() {
        return deathDay;
    }

    public int getPlantsEaten() {
        return plantsEaten;
    }

    public int getDescendantsCount() {
        return this.countDescendants();
    }

    public void eat(int energyFromGrass){
        plantsEaten += 1;
        energy += energyFromGrass;
    }

    public void loseEnergy(int decay) {
        energy = Math.max(0, energy - decay);
    }

    public MapDirection getDirection() {
        return direction;
    }

    public boolean hasStarved(int currDay){
        if (energy <= 0 && deathDay == null) {
            this.deathDay = currDay;
            return true;
        }
        return this.energy <= 0;
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
