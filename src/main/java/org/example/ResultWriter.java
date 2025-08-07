package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ResultWriter implements AutoCloseable {
    private final PrintWriter writer;

    public ResultWriter(String outputFile) throws IOException {
        // Clear the file on start
        new FileWriter(outputFile, false).close();
        this.writer = new PrintWriter(new BufferedWriter(new FileWriter(outputFile, true)));
    }

    public synchronized void writeResult(String result) {
        writer.println(result);
    }

    @Override
    public void close() throws IOException {
        writer.close();
    }
}