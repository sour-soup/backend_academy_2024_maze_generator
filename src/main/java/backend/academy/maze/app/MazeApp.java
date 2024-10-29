package backend.academy.maze.app;

import backend.academy.maze.exception.EmptyInputOutputException;
import backend.academy.maze.exception.MazeException;
import backend.academy.maze.generator.DepthFirstGenerator;
import backend.academy.maze.generator.Generator;
import backend.academy.maze.generator.KruskalGenerator;
import backend.academy.maze.generator.MultiPathKruskalGenerator;
import backend.academy.maze.interaction.InputHandler;
import backend.academy.maze.interaction.OutputHandler;
import backend.academy.maze.model.Coordinate;
import backend.academy.maze.model.Maze;
import backend.academy.maze.renderer.Renderer;
import backend.academy.maze.solver.BreadthFirstSolver;
import backend.academy.maze.solver.DijkstraSolver;
import backend.academy.maze.solver.Solver;
import java.util.List;
import java.util.Objects;

public class MazeApp {
    private static final int MAX_HEIGHT = 100;
    private static final int MAX_WIDTH = 100;

    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;
    private final Renderer renderer;

    public MazeApp(InputHandler inputHandler, OutputHandler outputHandler, Renderer renderer) {
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
        this.renderer = renderer;
    }

    public void run() {
        try {
            int height = getValidIntegerInput("Enter the height of the maze", 1, MAX_HEIGHT);
            int width = getValidIntegerInput("Enter the width of the maze", 1, MAX_WIDTH);

            Generator mazeGenerator = selectMazeGenerator();

            Maze maze = Objects.requireNonNull(mazeGenerator).generate(height, width);
            outputHandler.output("Maze generated!");
            outputHandler.output(renderer.render(maze));

            int startRow = getValidIntegerInput("Enter the start row", 0, height - 1);
            int startColumn = getValidIntegerInput("Enter the start column", 0, width - 1);

            Coordinate start = new Coordinate(startRow, startColumn);

            int endRow = getValidIntegerInput("Enter the end row", 0, height - 1);
            int endColumn = getValidIntegerInput("Enter the end column", 0, width - 1);

            Coordinate end = new Coordinate(endRow, endColumn);

            Solver solver = selectSolver();

            List<Coordinate> path = Objects.requireNonNull(solver).solve(maze, start, end);

            if (path.isEmpty()) {
                outputHandler.output("No solution found for the maze.");
            } else {
                outputHandler.output("Solution found!");
                outputHandler.output(renderer.render(maze, path));
            }

        } catch (EmptyInputOutputException e) {
            outputHandler.output("No input provided. Exiting...");
        } catch (MazeException e) {
            outputHandler.output("Error with maze: " + e.getMessage());
        } catch (Exception e) {
            outputHandler.output("An unexpected error occurred: " + e.getMessage());
        }
    }

    private Generator selectMazeGenerator() {
        outputHandler.output("Please choose a maze generator:");
        outputHandler.output("Available options: Kruskal, DFS, Multi-Path");

        while (true) {
            String input = inputHandler.inputString().trim().toLowerCase();

            switch (input) {
                case "kruskal":
                    return new KruskalGenerator();
                case "dfs":
                    return new DepthFirstGenerator();
                case "multi-path":
                    return new MultiPathKruskalGenerator();
                default:
                    outputHandler.output("Invalid generator. Please enter 'Kruskal', 'DFS' or 'Multi-Path'.");
            }
        }
    }

    private Solver selectSolver() {
        outputHandler.output("Please choose a solver:");
        outputHandler.output("Available options: BFS, Dijkstra");

        while (true) {
            String input = inputHandler.inputString().trim().toLowerCase();

            switch (input) {
                case "bfs":
                    return new BreadthFirstSolver();
                case "dijkstra":
                    return new DijkstraSolver();
                default:
                    outputHandler.output("Invalid solver. Please enter 'BFS' or 'Dijkstra'.");
            }
        }
    }

    private int getValidIntegerInput(String prompt, int min, int max) {
        while (true) {
            outputHandler.output(String.format("%s (Please enter a number between %d and %d):", prompt, min, max));
            int input = inputHandler.inputInteger();
            if (input >= min && input <= max) {
                return input;
            } else {
                outputHandler.output(
                    String.format("Input out of range. Please enter a number between %d and %d.", min, max));
            }
        }
    }
}
