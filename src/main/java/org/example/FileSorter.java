package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileSorter {

    public static void sortFile(String filename, Integer bufferSize) {
        try {
            int phases = 0;
            int diskReads = 0;
            int diskWrites = 0;

            FileIO fileIO = new FileIO(filename);
            fileIO.openFileForReading();
            diskReads++;

            List<String> tempFiles = new ArrayList<>();

            boolean done = false;
            while (!done) {
                List<Pentagon> buffer = new ArrayList<>(bufferSize);
                buffer.clear();

                for (int i = 0; i < bufferSize; i++) {
                    try {
                        Pentagon pentagon = fileIO.readPentagon();
                        diskReads++;
                        if (pentagon != null) {
                            buffer.add(pentagon);
                        } else {
                            done = true;
                            break;
                        }
                    } catch (EOFException e) {
                        done = true;
                        break;
                    }
                }

                if (!buffer.isEmpty()) {
                    Collections.sort(buffer, (p1, p2) -> Double.compare(p1.calculatePerimeter(), p2.calculatePerimeter()));

                    String tempFileName = "temp" + phases + ".txt";
                    tempFiles.add(tempFileName);
                    FileIO tempFileIO = new FileIO(tempFileName);
                    tempFileIO.openFileForWriting();
                    for (Pentagon pentagon : buffer) {
                        tempFileIO.writePentagon(pentagon);
                        diskWrites++;
                    }
                    tempFileIO.closeFile();
                    diskWrites++;

                    phases++;
                }
            }

            fileIO.closeFile();
            diskReads++;

            mergeFiles(tempFiles, filename);

            for (String tempFile : tempFiles) {
                new File(tempFile).delete();
                diskWrites++;
            }

            int totalDiskOperations = diskReads + diskWrites;
            System.out.println("Number of phases: " + phases);
            System.out.println("Number of disk operations: " + totalDiskOperations);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void mergeFiles(List<String> tempFiles, String filename) {
        try {
            FileIO fileIO = new FileIO(filename);
            fileIO.openFileForWriting();
            int diskReads = 0;
            int diskWrites = 0;

            List<FileIO> fileIOs = new ArrayList<>();
            for (String tempFile : tempFiles) {
                FileIO tempFileIO = new FileIO(tempFile);
                tempFileIO.openFileForReading();
                fileIOs.add(tempFileIO);
                diskReads++;
            }

            List<Pentagon> buffer = new ArrayList<>();

            for (FileIO tempFileIO : fileIOs) {
                Pentagon pentagon = tempFileIO.readPentagon();
                diskReads++;
                if (pentagon != null) {
                    buffer.add(pentagon);
                }
            }

            while (!buffer.isEmpty()) {
                Pentagon minPentagon = Collections.min(buffer, (p1, p2) -> Double.compare(p1.calculatePerimeter(), p2.calculatePerimeter()));
                fileIO.writePentagon(minPentagon);
                diskWrites++;

                for (int i = 0; i < buffer.size(); i++) {
                    if (buffer.get(i).equals(minPentagon)) {
                        try {
                            Pentagon pentagon = fileIOs.get(i).readPentagon();
                            diskReads++;
                            if (pentagon != null) {
                                buffer.set(i, pentagon);
                            } else {
                                buffer.remove(i);
                                fileIOs.get(i).closeFile();
                                fileIOs.remove(i);
                                diskReads++;
                            }
                        } catch (EOFException e) {
                            buffer.remove(i);
                            fileIOs.get(i).closeFile();
                            fileIOs.remove(i);
                            diskReads++;
                        }
                    }
                }
            }

            fileIO.closeFile();
            diskWrites++;

            int totalDiskOperations = diskReads + diskWrites;
            System.out.println("Number of disk operations in mergeFiles: " + totalDiskOperations);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
