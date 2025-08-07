# Java Multi-Threaded Ride-Sharing System Simulation

## Overview

This project is a simple command-line simulation of a task processing system, modeled after a ride-sharing service. It demonstrates a multi-threaded architecture in Java using the producer-consumer pattern.

The application simulates multiple "worker" threads that concurrently process ride-sharing tasks from a shared queue. The results of the processing are logged to the console and written to an output file.

## Features

*   **Multi-Threading:** Uses a fixed-size thread pool (`ExecutorService`) to manage concurrent worker threads.
*   **Thread-Safe Task Queue:** Employs a `BlockingQueue` for a thread-safe implementation of the producer-consumer pattern.
*   **Structured Logging:** Configured with **SLF4J** for clean, configurable, and level-based logging.
*   **Graceful Shutdown:** Implements a robust shutdown mechanism for the thread pool, ensuring all tasks are completed before the application exits.
*   **File I/O:** Writes the results of the task processing to a `results.txt` file.
*   **Dependency Management:** Uses **Maven** to manage project dependencies.

## Project Structure

*   `src/main/java/org/example/`
    *   `Main.java`: The main entry point of the application. It sets up the task queue, thread pool, and workers, then initiates the simulation.
    *   `Worker.java`: The consumer thread. Each worker runs in its own thread, pulls tasks from the queue, simulates processing, and logs the result.
    *   `Task.java`: A simple data class representing a single task (e.g., a ride request).
    *   `TaskQueue.java`: A thread-safe wrapper around a `BlockingQueue` to hold the tasks.
    *   `ResultWriter.java`: A class responsible for writing the processing results to the output file.
*   `pom.xml`: The Maven Project Object Model file. It defines project dependencies (like SLF4J) and build settings.
*   `results.txt`: The output file where results are stored. This file is created/cleared when the application starts.

## Prerequisites

*   **Java Development Kit (JDK) 17** or later.
*   **Apache Maven** to build the project and manage dependencies.

## How to Build and Run

### 1. Build the Project

Open a terminal or command prompt in the project's root directory and run the following Maven command to compile the code and download the dependencies:

```bash
mvn clean package
```