package net.whg.sarica.goalsolver.impl;

import java.util.concurrent.CompletableFuture;

/**
 * A wrapper for connecting and communicating with the task handler process.
 */
public class TaskHandler {
    private final TaskType taskType;

    /**
     * Creates a new task handler connection instance.
     * 
     * @param taskType - The type of task this handler can work with.
     */
    public TaskHandler(TaskType taskType) {
        this.taskType = taskType;
    }

    /**
     * Pings the handler process to estimate a heuristic value for executing the
     * given task.
     * 
     * @param task - The task to test against.
     * @return A future containing the heuristic estimates.
     */
    public CompletableFuture<Heuristic> getHeuristic(Task task) {
        // TODO
        return null;
    }

    /**
     * Pings the handler to execute the given task.
     * 
     * @param task - The task to execute.
     * @return A future containing the results of the task.
     */
    public CompletableFuture<TaskResults> execute(Task task) {
        // TODO
        return null;
    }

    /**
     * Gets the type of tasks this handler is able to work with.
     * 
     * @return The target task type.
     */
    public TaskType getTargetTaskType() {
        return taskType;
    }
}
