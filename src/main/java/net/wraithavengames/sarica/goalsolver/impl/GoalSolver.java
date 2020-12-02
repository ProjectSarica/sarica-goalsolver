package net.wraithavengames.sarica.goalsolver.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.apache.log4j.Logger;

/**
 * Attempts to solve tasks by distributing them to various task handlers based
 * on heuristic estimates.
 */
public class GoalSolver {
    private static final Logger log = Logger.getLogger(GoalSolver.class);

    private final List<TaskHandler> taskHandlers = Collections.synchronizedList(new ArrayList<>());

    /**
     * Adds a new task handler to this goal solver. (Thread safe)
     * 
     * @param handler - The task handler to add.
     */
    public void addTaskHandler(TaskHandler handler) {
        taskHandlers.add(handler);
    }

    /**
     * Removes a task handler from this goal solver. (Thread safe)
     * 
     * @param handler - The task handler to remove.
     */
    public void removeTaskHandler(TaskHandler handler) {
        taskHandlers.remove(handler);
    }

    /**
     * Starts an asynchronous operation to attempt to solve the given task.
     * 
     * @param task - The task to execute.
     * @return A future object containing the results of that task execution.
     */
    public CompletableFuture<TaskResults> solve(Task task) {
        return determineBestHandler(task).thenApply(h -> {
            if (h == null) {
                return new TaskResults(false);
            }

            try {
                return h.execute(task).get();
            } catch (InterruptedException e) {
                log.error("Failed to retrieve heuristic from task handler! Thread interrupted!", e);
                Thread.currentThread().interrupt();
                return new TaskResults(false);
            } catch (ExecutionException e) {
                log.error("Failed to handle task!", e);
                return new TaskResults(false);
            }
        });
    }

    /**
     * Searches through all task handlers and resolves their heuristics for the
     * given task in order to decide what the best handler to use for the given task
     * would be.
     * 
     * @param task - The task to test against.
     * @return A future containing the task handler that should be used.
     */
    private CompletableFuture<TaskHandler> determineBestHandler(Task task) {
        return CompletableFuture.supplyAsync(() -> {
            TaskHandler best = null;
            float lowestCost = Float.MAX_VALUE;

            var processes = createHeuristicRequests(task);
            for (var heuristicFuture : processes) {
                Heuristic h;
                try {
                    h = heuristicFuture.get();
                } catch (InterruptedException e) {
                    log.error("Failed to retrieve heuristic from task handler! Thread interrupted!", e);
                    Thread.currentThread().interrupt();
                    continue;
                } catch (ExecutionException e) {
                    log.error("Failed to retrieve heuristic from task handler!", e);
                    continue;
                }

                if (!h.canSolve())
                    continue;

                var cost = getHeuristicCost(h);
                if (cost < lowestCost) {
                    best = h.getTaskHandler();
                    lowestCost = cost;
                }
            }

            return best;
        });
    }

    /**
     * Sends a request to all available task handlers to calculate the estimated
     * heuristic value for executing the given task.
     * 
     * @param task - The task to test against.
     * @return A list of futures containing the heuristic results.
     */
    private ArrayList<CompletableFuture<Heuristic>> createHeuristicRequests(Task task) {
        var processes = new ArrayList<CompletableFuture<Heuristic>>();
        for (var handler : taskHandlers) {
            processes.add(handler.getHeuristic(task));
        }

        return processes;
    }

    /**
     * Resolves final heuristic value for the heuristic, taking all child tasks into
     * account up to the given depth.
     * 
     * @return The final heuristic cost estimate.
     */
    private float getHeuristicCost(Heuristic heuristic) {
        // TODO Take child tasks into account
        return heuristic.getCost();
    }
}
