package net.whg.sarica.goalsolver.impl;

/**
 * A specific task to be preformed.
 */
public interface Task {
    /**
     * Gets the type of this task
     * 
     * @return Gets the task type.
     */
    TaskType getType();
}
