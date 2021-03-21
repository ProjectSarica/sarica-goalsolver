package net.whg.sarica.goalsolver.impl;

/**
 * A task type defines a definition for a unique task of task that needs to be
 * preformed. This is a blanket term representing the type of task rather than
 * the configuration of that task.
 */
public class TaskType {
    private final String name;

    /**
     * Creates a new TaskTask with the given name.
     * 
     * @param name - The name of the task type.
     */
    public TaskType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
