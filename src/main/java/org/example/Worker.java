package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Worker implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(Worker.class);

    private final int workerId;
    private final TaskQueue taskQueue;
    private final ResultWriter resultWriter;

    public Worker(int workerId, TaskQueue taskQueue, ResultWriter resultWriter) {
        this.workerId = workerId;
        this.taskQueue = taskQueue;
        this.resultWriter = resultWriter;
    }

    @Override
    public void run() {
        logger.info("Worker {} started.", workerId);

        while (true) {
            try {
                Task task = taskQueue.getTask();
                if (task.getData().equals("POISON")) break;  // Signal to stop

                // Simulate processing
                Thread.sleep((int) (Math.random() * 1000));

                String result = "Worker " + workerId + " processed Task " + task.getTaskId() + " with data: " + task.getData();
                resultWriter.writeResult(result);

                logger.info("Worker {} completed Task {}", workerId, task.getTaskId());

            } catch (InterruptedException e) {
                logger.warn("Worker {} was interrupted.", workerId);
                // Restore the interrupted status
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                logger.error("Worker {} encountered an error", workerId, e);
            }
        }

        logger.info("Worker {} stopped.", workerId);
    }
}