package org.example;

import java.io.*;

class FileIO {
    private String filename;
    private RandomAccessFile file;

    public FileIO(String filename) {
        this.filename = filename;
    }

    public void openFileForReading() throws IOException {
        file = new RandomAccessFile(filename, "r");
    }

    public void openFileForWriting() throws IOException {
        file = new RandomAccessFile(filename, "rw");
    }

    public void closeFile() throws IOException {
        if (file != null) {
            file.close();
        }
    }

    public Pentagon readPentagon() throws IOException {
        double s1 = file.readDouble();
        double s2 = file.readDouble();
        double s3 = file.readDouble();
        double s4 = file.readDouble();
        double s5 = file.readDouble();
        return new Pentagon(s1, s2, s3, s4, s5);
    }

    public void writePentagon(Pentagon pentagon) throws IOException {
        file.writeDouble(pentagon.side1);
        file.writeDouble(pentagon.side2);
        file.writeDouble(pentagon.side3);
        file.writeDouble(pentagon.side4);
        file.writeDouble(pentagon.side5);
    }
}
