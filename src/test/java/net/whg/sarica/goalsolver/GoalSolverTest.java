package net.whg.sarica.goalsolver;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;

import net.whg.sarica.goalsolver.impl.GoalSolver;
import net.whg.sarica.goalsolver.impl.Heuristic;
import net.whg.sarica.goalsolver.impl.Task;
import net.whg.sarica.goalsolver.impl.TaskHandler;
import net.whg.sarica.goalsolver.impl.TaskResults;
import net.whg.sarica.goalsolver.impl.TaskType;

class GoalSolverTest {
        @Test
        void chooseBest_simpleHandlerList() throws InterruptedException, ExecutionException {
                var taskType = new TaskType("basic-task");

                var task = mock(Task.class);
                when(task.getType()).thenReturn(taskType);

                var handler1 = mock(TaskHandler.class);
                when(handler1.getHeuristic(any())).thenReturn(
                                CompletableFuture.supplyAsync(() -> new Heuristic(task, handler1, false, 0f)));
                when(handler1.execute(any())).thenReturn(CompletableFuture.supplyAsync(() -> new TaskResults(true)));

                var handler2 = mock(TaskHandler.class);
                when(handler2.getHeuristic(any())).thenReturn(
                                CompletableFuture.supplyAsync(() -> new Heuristic(task, handler2, true, 2f)));
                when(handler2.execute(any())).thenReturn(CompletableFuture.supplyAsync(() -> new TaskResults(true)));

                var handler3 = mock(TaskHandler.class);
                when(handler3.getHeuristic(any())).thenReturn(
                                CompletableFuture.supplyAsync(() -> new Heuristic(task, handler3, true, 30f)));
                when(handler3.execute(any())).thenReturn(CompletableFuture.supplyAsync(() -> new TaskResults(true)));

                var goalSolver = new GoalSolver();
                goalSolver.addTaskHandler(handler1);
                goalSolver.addTaskHandler(handler2);
                goalSolver.addTaskHandler(handler3);

                goalSolver.solve(task).get();

                verify(handler1, never()).execute(task);
                verify(handler2, times(1)).execute(task);
                verify(handler3, never()).execute(task);
        }
}