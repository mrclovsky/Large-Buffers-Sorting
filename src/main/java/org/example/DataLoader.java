package org.example;

import java.io.EOFException;
import java.io.IOException;

public class DataLoader {
    public static void loadData(String filename) {
        try {
            FileIO fileIO = new FileIO(filename);
            fileIO.openFileForReading();

            while (true) {
                try {
                    Pentagon pentagon = fileIO.readPentagon();
                    System.out.println(pentagon);
                } catch (EOFException e) {
                    break;
                }
            }

            fileIO.closeFile();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
