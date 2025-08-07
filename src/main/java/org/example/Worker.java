package org.example;

public class Worker implements Runnable {
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
        System.out.println("Worker " + workerId + " started.");

        while (true) {
            try {
                Task task = taskQueue.getTask();
                if (task.getData().equals("POISON")) break;  // Signal to stop

                // Simulate processing
                Thread.sleep((int) (Math.random() * 1000));

                String result = "Worker " + workerId + " processed Task " + task.getTaskId() + " with data: " + task.getData();
                resultWriter.writeResult(result);

                System.out.println("Worker " + workerId + " completed Task " + task.getTaskId());

            } catch (InterruptedException e) {
                System.err.println("Worker " + workerId + " was interrupted.");
                break;
            } catch (Exception e) {
                System.err.println("Worker " + workerId + " encountered error: " + e.getMessage());
                e.printStackTrace();
            }
        }

        System.out.println("Worker " + workerId + " stopped.");
    }
}
