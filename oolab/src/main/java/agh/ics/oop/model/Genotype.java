package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Genotype {
    private final List<Integer> genes;
    private int currGeneIndex = 0;

    public Genotype(List<Integer> genes) {
        this.genes = genes;
    }

    public int length() {
        return genes.size();
    }

    public List<Integer> getPart(int start, int end) {
        return this.genes.subList(start, end);
    }

    public static Genotype generateRandom(int length) {
        List<Integer> randomGenes = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            randomGenes.add(random.nextInt(8));
        }
        return new Genotype(randomGenes);
    }

    public int todaysMove() {
        int move = genes.get(currGeneIndex);
        currGeneIndex = (currGeneIndex+1) % genes.size();
        return move;
    }
}
