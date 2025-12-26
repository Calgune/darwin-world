package agh.ics.oop.model.util;

import java.io.*;
import java.util.Properties;

public class ConfigurationHandler {
    public static void saveConfiguration(File file, MapSettings settings) throws IOException {
        Properties config = new Properties();
        config.setProperty("width", String.valueOf(settings.getBoundary().upperRight().getX()));
        config.setProperty("height", String.valueOf(settings.getBoundary().upperRight().getY()));
        config.setProperty("initialAnimals", String.valueOf(settings.getInitialAnimalCount()));
        config.setProperty("initialEnergy", String.valueOf(settings.getInitialAnimalEnergy()));
        config.setProperty("genomeLength", String.valueOf(settings.getGenomeLength()));
        config.setProperty("initialGrass", String.valueOf(settings.getInitialGrassCount()));
        config.setProperty("dailyGrass", String.valueOf(settings.getNewDailyGrasses()));
        config.setProperty("refreshSpeed", String.valueOf(settings.getRefreshSpeed()));
        config.setProperty("energyFromGrass", String.valueOf(settings.getEnergyFromGrass()));
        config.setProperty("dailyEnergyDecay", String.valueOf(settings.getDailyEnergyDecay()));
        config.setProperty("reproductionCost", String.valueOf(settings.getReproductionEnergyCost()));
        config.setProperty("reproductionThreshold", String.valueOf(settings.getReproductionEnergyThreshold()));

        try (OutputStream output = new FileOutputStream(file)) {
            config.store(output, "Konfiguracje symulacji");
        }
    }

    public static Properties loadConfiguration(File file) throws IOException {
        Properties config = new Properties();
        try (InputStream input = new FileInputStream(file)) {
            config.load(input);
        }
        return config;
    }
}