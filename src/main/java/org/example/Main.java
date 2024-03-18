package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("Choose option:");
            System.out.println("1. Generate random data");
            System.out.println("2. Input data manually");
            System.out.println("3. Load data from file");
            System.out.println("4. Sort file");
            System.out.println("5. Close program");
            System.out.print("Enter choice: ");

            try {
                int choice = Integer.parseInt(reader.readLine());
                String filename;
                Integer recordsNum;

                switch (choice) {
                    case 1:
                        System.out.print("Enter filename: ");
                        String userInput = reader.readLine();
                        System.out.print("Enter number of records: ");
                        recordsNum = Integer.parseInt(reader.readLine());
                        filename = userInput + ".txt";
                        DataGenerator.generateRandomData(filename, recordsNum);
                        break;
                    case 2:
                        System.out.print("Enter filename: ");
                        String userInput2 = reader.readLine();
                        System.out.print("Enter number of records: ");
                        recordsNum = Integer.parseInt(reader.readLine());
                        filename = userInput2 + ".txt";
                        DataGenerator.writeManualData(filename, recordsNum);
                        break;
                    case 3:
                        System.out.print("Enter filename: ");
                        String userInput3 = reader.readLine();
                        filename = userInput3 + ".txt";
                        DataLoader.loadData(filename);
                        break;
                    case 4:
                        System.out.print("Enter filename: ");
                        String userInput4 = reader.readLine();
                        filename = userInput4 + ".txt";
                        FileSorter.sortFile(filename, 100);
                        break;
                    case 5:
                        isRunning = false;
                        System.out.println("Program closing.");
                        break;
                    default:
                        System.out.println("Incorrect choice.");
                        break;
                }

            } catch (IOException | NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }
}