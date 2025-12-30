package agh.ics.oop.model.util;

import com.opencsv.CSVWriter;

import java.awt.*;
import java.util.UUID;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteToCSV {
    private final String filePath;

    public WriteToCSV() {
        String uuid = UUID.randomUUID().toString().substring(0, 4);
        this.filePath = "simulation_" + uuid + ".csv";
        writeHeader();
    }

    private void writeHeader() {
        File file = new File(filePath);
        if (!file.exists()) {
            writeData(new String[]{
                    "Day", "Animals", "Grasses", "EmptyCells",
                    "MostPopularGenotype", "AvgEnergy", "AvgKids", "AvgLifespan"
            });
        }
    }

    public void writeData(String[] data)
    {
        File file = new File(filePath);
        try {
            FileWriter outputfile = new FileWriter(file, true); //zeby kolejne dni sie dopisywaly
            CSVWriter writer = new CSVWriter(outputfile);
            writer.writeNext(data);
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openFile() {
        try {
            File file = new File(filePath);

            if (!file.exists()) {
                System.out.println("Plik CSV nie istnieje.");
                return;
            }

            Desktop.getDesktop().open(file);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
