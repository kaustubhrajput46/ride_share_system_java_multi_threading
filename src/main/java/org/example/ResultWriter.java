package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ResultWriter {
    private final String outputFile;

    public ResultWriter(String outputFile) {
        this.outputFile = outputFile;
    }

    public synchronized void writeResult(String result) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outputFile, true)))) {
            out.println(result);
        } catch (IOException e) {
            System.err.println("Error writing result: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
