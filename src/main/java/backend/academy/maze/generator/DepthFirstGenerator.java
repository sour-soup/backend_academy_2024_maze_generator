package backend.academy.maze.generator;

import backend.academy.maze.model.Cell.Direction;
import backend.academy.maze.model.Coordinate;
import backend.academy.maze.model.Maze;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
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

    private Coordinate getNextCoordinate(Coordinate current, Direction direction) {
        return switch (direction) {
            case UP -> new Coordinate(current.row() - 1, current.column());
            case DOWN -> new Coordinate(current.row() + 1, current.column());
            case LEFT -> new Coordinate(current.row(), current.column() - 1);
            case RIGHT -> new Coordinate(current.row(), current.column() + 1);
        };
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

    private boolean isValidCoordinate(Coordinate coordinate, Maze maze) {
        return coordinate.row() >= 0 && coordinate.row() < maze.height()
               && coordinate.column() >= 0 && coordinate.column() < maze.width();
    }
}
