package net.wraithavengames.sarica.goalsolver.impl;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the returned heuristic for a task passed to the given task
 * handler.
 */
public class Heuristic {
    private final Task task;
    private final TaskHandler handler;
    private final boolean canSolve;
    private final float cost;
    private final List<Task> childTasks = new ArrayList<>();

    /**
     * Creates a new heuristic representation for passing the given task to the task
     * handler.
     * 
     * @param task     - The task to execute.
     * @param handler  - The handler that was tested against.
     * @param canSolve - Whether or not the handler can complete the task.
     * @param cost     - The estimated cost of completing that task.
     */
    public Heuristic(Task task, TaskHandler handler, boolean canSolve, float cost) {
        this.task = task;
        this.handler = handler;
        this.canSolve = canSolve;
        this.cost = cost;
    }

    /**
     * Gets the task to execute.
     * 
     * @return The task.
     */
    public Task getTask() {
        return task;
    }

    /**
     * Gets the task handler that was tested against.
     * 
     * @return The task handler.
     */
    public TaskHandler getTaskHandler() {
        return handler;
    }

    /**
     * Gets whether or not the given task handler can complete this task.
     * 
     * @return True if the handler can complete this task, false otherwise.
     */
    public boolean canSolve() {
        return canSolve;
    }

    /**
     * Gets the estimated cost for this handler completing the given task.
     * 
     * @return The estimated cost.
     */
    public float getCost() {
        return cost;
    }

    /**
     * Gets the estimated list of child tasks the handler would create while
     * executing the given task.
     * 
     * @return The list of child tasks.
     */
    public List<Task> getChildTasks() {
        return childTasks;
    }
}
