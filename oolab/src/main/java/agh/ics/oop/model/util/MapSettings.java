package agh.ics.oop.model.util;

import agh.ics.oop.model.Boundary;
import agh.ics.oop.model.Vector2d;

public class MapSettings {
    private final Boundary boundary;
    private final int initialGrassCount;
    private final int energyFromGrass;
    private final int newDailyGrasses;
    private final int initialAnimalCount;
    private final int initialAnimalEnergy;
    private final int dailyEnergyDecay;
    private final int reproductionEnergyThreshold;
    private final int reproductionEnergyCost;
    private final int genomeLength;
    private final int mutationRange;
    private final int projectType;

    public MapSettings(Builder builder) {
        this.boundary = builder.boundary;
        this.initialGrassCount = builder.initialGrassCount;
        this.energyFromGrass = builder.energyFromGrass;
        this.newDailyGrasses = builder.newDailyGrasses;
        this.initialAnimalCount = builder.initialAnimalCount;
        this.initialAnimalEnergy = builder.initialAnimalEnergy;
        this.dailyEnergyDecay = builder.dailyEnergyDecay;
        this.reproductionEnergyThreshold = builder.reproductionEnergyThreshold;
        this.reproductionEnergyCost = builder.reproductionEnergyCost;
        this.genomeLength = builder.genomeLength;
        this.mutationRange = builder.mutationRange;
        this.projectType = builder.projectType;
    }

    public Boundary getBoundary() {
        return boundary;
    }

    public int getInitialGrassCount() {
        return initialGrassCount;
    }

    public int getEnergyFromGrass() {
        return energyFromGrass;
    }

    public int getNewDailyGrasses() {
        return newDailyGrasses;
    }

    public int getInitialAnimalCount() {
        return initialAnimalCount;
    }

    public int getInitialAnimalEnergy() {
        return initialAnimalEnergy;
    }

    public int getDailyEnergyDecay() {
        return dailyEnergyDecay;
    }

    public int getReproductionEnergyThreshold() {
        return reproductionEnergyThreshold;
    }

    public int getReproductionEnergyCost() {
        return reproductionEnergyCost;
    }

    public int getGenomeLength() {
        return genomeLength;
    }

    public int getMutationRange() {
        return mutationRange;
    }

    public int getProjectType() {
        return projectType;
    }

    public static class Builder {
        private Boundary boundary = new Boundary(new Vector2d(0, 0), new Vector2d(20, 10));
        private int initialGrassCount = 10;
        private int energyFromGrass = 3;
        private int newDailyGrasses = 5;
        private int initialAnimalCount = 5;
        private int initialAnimalEnergy = 10;
        private int dailyEnergyDecay = 1;
        private int reproductionEnergyThreshold = 10;
        private int reproductionEnergyCost = 5;
        private int genomeLength = 7;
        private int mutationRange = 2;
        private int projectType = 0;

        public Builder boundary(Boundary boundary){
            this.boundary = boundary;
            return this;
        }

        public Builder initialGrassCount(int initialGrassCount){
            this.initialGrassCount = initialGrassCount;
            return this;
        }

        public Builder energyFromGrass(int energyFromGrass){
            this.energyFromGrass = energyFromGrass;
            return this;
        }

        public Builder newDailyGrasses(int newDailyGrasses){
            this.newDailyGrasses = newDailyGrasses;
            return this;
        }

        public Builder initialAnimalCount(int initialAnimalCount){
            this.initialAnimalCount = initialAnimalCount;
            return this;
        }

        public Builder initialAnimalEnergy(int initialAnimalEnergy){
            this.initialAnimalEnergy = initialAnimalEnergy;
            return this;
        }

        public Builder dailyEnergyDecay(int dailyEnergyDecay){
            this.dailyEnergyDecay = dailyEnergyDecay;
            return this;
        }

        public Builder reproductionEnergyThreshold(int reproductionEnergyThreshold) {
            this.reproductionEnergyThreshold = reproductionEnergyThreshold;
            return this;
        }

        public Builder reproductionEnergyCost(int reproductionEnergyCost){
            this.reproductionEnergyCost = reproductionEnergyCost;
            return this;
        }

        public Builder genomeLength(int genomeLength){
            this.genomeLength = genomeLength;
            return this;
        }

        public Builder mutationRange(int mutationRange){
            this.mutationRange = mutationRange;
            return this;
        }

        public Builder projectType(int projectType){
            this.projectType = projectType;
            return this;
        }

        public MapSettings build() {
            return new MapSettings(this);
        }
    }
}
