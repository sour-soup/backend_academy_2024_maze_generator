package backend.academy.maze.utils;

import backend.academy.maze.model.Cell;
import backend.academy.maze.model.Coordinate;
import backend.academy.maze.model.Maze;

public final class CoordinateMazeUtils {
    private CoordinateMazeUtils() {
    }

    public static Coordinate getNextCoordinate(Coordinate current, Cell.Direction direction) {
        return switch (direction) {
            case UP -> new Coordinate(current.row() - 1, current.column());
            case DOWN -> new Coordinate(current.row() + 1, current.column());
            case LEFT -> new Coordinate(current.row(), current.column() - 1);
            case RIGHT -> new Coordinate(current.row(), current.column() + 1);
        };
    }

    public static boolean isValidCoordinate(Coordinate coordinate, Maze maze) {
        return coordinate.row() >= 0 && coordinate.row() < maze.height()
               && coordinate.column() >= 0 && coordinate.column() < maze.width();
    }

    public static int getIndex(Coordinate coordinate, Maze maze) {
        return coordinate.row() * maze.width() + coordinate.column();
    }
}
