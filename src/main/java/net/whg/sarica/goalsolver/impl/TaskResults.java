package net.whg.sarica.goalsolver.impl;

/**
 * Represents the results of a completed task.
 */
public class TaskResults {
    private final boolean taskCompleted;

    /**
     * Creates a new TaskResults object.
     * 
     * @param taskCompleted - Whether or not the task could be completed.
     */
    public TaskResults(boolean taskCompleted) {
        this.taskCompleted = taskCompleted;
    }

    /**
     * Gets whether or not the task could be completed.
     * 
     * @return True if the task was completed, false otherwise.
     */
    public boolean taskCompleted() {
        return taskCompleted;
    }
}
