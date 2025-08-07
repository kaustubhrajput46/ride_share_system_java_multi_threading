package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        int numWorkers = 4;
        int numTasks = 20;
        String outputFile = "results.txt";

        logger.info("Starting the Ride Sharing System simulation.");

        TaskQueue taskQueue = new TaskQueue();
        ExecutorService executor = Executors.newFixedThreadPool(numWorkers);
        ResultWriter resultWriter = null;

        try {
            resultWriter = new ResultWriter(outputFile);

            logger.info("Submitting {} tasks to the queue.", numTasks);
            for (int i = 1; i <= numTasks; i++) {
                taskQueue.addTask(new Task(i, "RideRequest#" + i));
            }

            logger.info("Adding poison pills to stop workers.");
            for (int i = 0; i < numWorkers; i++) {
                taskQueue.addTask(new Task(-1, "POISON"));
            }

            logger.info("Starting {} worker threads.", numWorkers);
            for (int i = 1; i <= numWorkers; i++) {
                executor.execute(new Worker(i, taskQueue, resultWriter));
            }

        } catch (IOException e) {
            logger.error("Failed to initialize ResultWriter", e);
            return; // Exit if we can't write results
        } finally {
            executor.shutdown();
            logger.info("Executor shutdown initiated. Waiting for tasks to complete.");
            try {
                if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                    logger.error("Executor did not terminate in the specified time.");
                    executor.shutdownNow();
                } else {
                    logger.info("All tasks have completed.");
                }
            } catch (InterruptedException e) {
                logger.error("Termination interrupted.", e);
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }

            if (resultWriter != null) {
                try {
                    resultWriter.close();
                    logger.info("Results successfully written to {}", outputFile);
                } catch (IOException e) {
                    logger.error("Failed to close ResultWriter", e);
                }
            }
            logger.info("Ride Sharing System simulation finished.");
        }
    }
}