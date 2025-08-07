package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        int numWorkers = 4;
        int numTasks = 20;
        String outputFile = "results.txt";

        TaskQueue taskQueue = new TaskQueue();
        ResultWriter resultWriter = new ResultWriter(outputFile);
        ExecutorService executor = Executors.newFixedThreadPool(numWorkers);

        // Add tasks
        for (int i = 1; i <= numTasks; i++) {
            taskQueue.addTask(new Task(i, "RideRequest#" + i));
        }

        // Add poison pills to stop workers
        for (int i = 0; i < numWorkers; i++) {
            taskQueue.addTask(new Task(-1, "POISON"));
        }

        // Start workers
        for (int i = 1; i <= numWorkers; i++) {
            executor.execute(new Worker(i, taskQueue, resultWriter));
        }

        // Shutdown
        executor.shutdown();
        System.out.println("All workers submitted.");
    }
}
