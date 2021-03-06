package astar.example;

import astar.AStarSolver;
import astar.ShortestPathsSolver;
import astar.ShortestPathsSolver.SolverOutcome;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * Provides sanity tests based on weighted directed graphs from lecture.
 * NOTE: YOU MUST REPLACE LazySolver WITH AStarSolver OR THIS DEMO WON'T WORK!
 */
public class SanityTests {
    private WeightedDirectedGraph lectureGraph() {
        WeightedDirectedGraph wdg = new WeightedDirectedGraph(7);
        /* Edges from vertex 0. */
        wdg.addEdge(0, 1, 2);
        wdg.addEdge(0, 2, 1);

        wdg.addEdge(1, 2, 5);
        wdg.addEdge(1, 3, 11);
        wdg.addEdge(1, 4, 3);

        wdg.addEdge(2, 5, 15);

        wdg.addEdge(3, 4, 2);

        wdg.addEdge(4, 2, 1);
        wdg.addEdge(4, 5, 4);
        wdg.addEdge(4, 6, 5);

        wdg.addEdge(6, 3, 1);
        wdg.addEdge(6, 5, 1);
        return wdg;
    }

    @Test
    public void testStart0Goal6() {
        WeightedDirectedGraph wdg = lectureGraph();
        int start = 0;
        int goal = 6;
        ShortestPathsSolver<Integer> solver = new AStarSolver<>(wdg, start, goal, 10);
        // ShortestPathsSolver<Integer> solver = new LazySolver<>(wdg, start, goal, 10);
        List<Integer> actual = solver.solution();
        List<Integer> expected = List.of(0, 1, 4, 6);
        assertEquals(expected, actual);

        SolverOutcome actualOutcome = solver.outcome();
        SolverOutcome expectedOutcome = SolverOutcome.SOLVED;
        assertEquals(expectedOutcome, actualOutcome);
    }

    @Test
    public void testStart0Goal0() {
        WeightedDirectedGraph wdg = lectureGraph();
        int start = 0;
        int goal = 0;
        ShortestPathsSolver<Integer> solver = new AStarSolver<>(wdg, start, goal, 10);
        // ShortestPathsSolver<Integer> solver = new LazySolver<>(wdg, start, goal, 10);
        List<Integer> actual = solver.solution();
        List<Integer> expected = List.of(0);
        assertEquals(expected, actual);

        SolverOutcome actualOutcome = solver.outcome();
        SolverOutcome expectedOutcome = SolverOutcome.SOLVED;
        assertEquals(expectedOutcome, actualOutcome);
    }

    @Test
    public void testStart6Goal0() {
        WeightedDirectedGraph wdg = lectureGraph();
        int start = 6;
        int goal = 0;
        ShortestPathsSolver<Integer> solver = new AStarSolver<>(wdg, start, goal, 10);
        // ShortestPathsSolver<Integer> solver = new LazySolver<>(wdg, start, goal, 10);
        List<Integer> actual = solver.solution();
        List<Integer> expected = List.of();
        assertEquals(expected, actual);

        SolverOutcome actualOutcome = solver.outcome();
        SolverOutcome expectedOutcome = SolverOutcome.UNSOLVABLE;
        assertEquals(expectedOutcome, actualOutcome);
    }
}
