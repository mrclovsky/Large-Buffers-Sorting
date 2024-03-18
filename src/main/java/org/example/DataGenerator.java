package org.example;

import java.io.*;
import java.util.Random;

public class DataGenerator {

    public static void generateRandomData(String filename, Integer recordsNum) {
        try {
            FileIO fileIO = new FileIO(filename);
            fileIO.openFileForWriting();

            Random random = new Random();

            for (int i = 0; i < recordsNum; i++) {
                double side1 = random.nextDouble() * 100;
                double side2 = random.nextDouble() * 100;
                double side3 = random.nextDouble() * 100;
                double side4 = random.nextDouble() * 100;
                double side5 = random.nextDouble() * 100;

                Pentagon pentagon = new Pentagon(side1, side2, side3, side4, side5);
                fileIO.writePentagon(pentagon);
            }

            fileIO.closeFile();
            System.out.println("Random data generated and saved to file.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeManualData(String filename, Integer recordsNum) {
        try {
            FileIO fileIO = new FileIO(filename);
            fileIO.openFileForWriting();

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter pentagon sides (side1 side2 side3 side4 side5):");

            for (int i = 0; i < recordsNum; i++) {
                String input = reader.readLine();
                String[] sides = input.split(" ");

                if (sides.length == 5) {
                    double side1 = Double.parseDouble(sides[0]);
                    double side2 = Double.parseDouble(sides[1]);
                    double side3 = Double.parseDouble(sides[2]);
                    double side4 = Double.parseDouble(sides[3]);
                    double side5 = Double.parseDouble(sides[4]);

                    Pentagon pentagon = new Pentagon(side1, side2, side3, side4, side5);
                    fileIO.writePentagon(pentagon);
                } else {
                    System.out.println("Invalid input. Please enter 5 sides separated by spaces.");
                    i--;
                }
            }

            fileIO.closeFile();
            System.out.println("Manual data input completed and saved to file.");

        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
