package backend.academy.maze.generator;

import backend.academy.maze.model.Cell.Direction;
import backend.academy.maze.model.Coordinate;
import backend.academy.maze.model.Maze;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import static backend.academy.maze.utils.MazeUtils.getNextCoordinate;
import static backend.academy.maze.utils.MazeUtils.isValidCoordinate;
import static backend.academy.maze.utils.RandomUtils.getRandomElement;
import static backend.academy.maze.utils.RandomUtils.getRandomInt;

public class DepthFirstGenerator implements Generator {
    @Override
    public Maze generate(int height, int width) {
        Maze maze = new Maze(height, width);
        boolean[][] visited = new boolean[height][width];

        Deque<Coordinate> path = new ArrayDeque<>();

        Coordinate start = new Coordinate(getRandomInt(height), getRandomInt(width));
        visited[start.row()][start.column()] = true;
        path.push(start);

        while (!path.isEmpty()) {
            Coordinate current = path.peek();
            List<Direction> unvisitedDirections = getUnvisitedDirections(current, maze, visited);

            if (!unvisitedDirections.isEmpty()) {
                Direction direction = getRandomElement(unvisitedDirections);
                Coordinate next = getNextCoordinate(current, direction);

                maze.connectCells(current, next);
                visited[next.row()][next.column()] = true;

                path.push(next);
            } else {
                path.pop();
            }
        }

        return maze;
    }

    private List<Direction> getUnvisitedDirections(Coordinate current, Maze maze, boolean[][] visited) {
        List<Direction> unvisitedDirections = new ArrayList<>();

        for (Direction direction : Direction.values()) {
            Coordinate next = getNextCoordinate(current, direction);
            if (isValidCoordinate(next, maze) && !visited[next.row()][next.column()]) {
                unvisitedDirections.add(direction);
            }
        }
        return unvisitedDirections;
    }
}
