package backend.academy.maze.model;

import backend.academy.maze.exception.MazeException;
import backend.academy.maze.model.Cell.Direction;
import backend.academy.maze.model.Cell.SurfaceType;
import lombok.Getter;

public final class Maze {
    private static final String INVALID_COORDINATES = "Invalid cell coordinates";

    @Getter private final int height;
    @Getter private final int width;
    private final Cell[][] grid;

    public Maze(int height, int width) {
        this.height = height;
        this.width = width;
        grid = new Cell[height][width];

        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                grid[row][column] = new Cell(row, column, SurfaceType.NORMAL);
            }
        }
    }

    public Cell getCell(int row, int column) {
        if (isValidCoordinate(row, column)) {
            return grid[row][column];
        } else {
            throw new MazeException(INVALID_COORDINATES);
        }
    }

    public void setCell(int row, int column, SurfaceType surfaceType) {
        if (isValidCoordinate(row, column)) {
            grid[row][column] = new Cell(row, column, surfaceType);
        } else {
            throw new MazeException(INVALID_COORDINATES);
        }
    }

    public void connectCells(Coordinate coordinateA, Coordinate coordinateB) {
        if (isValidCoordinate(coordinateA.row(), coordinateA.column())
            && isValidCoordinate(coordinateB.row(), coordinateB.column())) {

            Cell cellA = getCell(coordinateA.row(), coordinateA.column());
            Cell cellB = getCell(coordinateB.row(), coordinateB.column());

            if (cellA.row() + 1 == cellB.row()) {
                cellA.setConnection(Direction.DOWN, true);
                cellB.setConnection(Direction.UP, true);
            } else if (cellA.row() == cellB.row() + 1) {
                cellA.setConnection(Direction.UP, true);
                cellB.setConnection(Direction.DOWN, true);
            } else if (cellA.column() + 1 == cellB.column()) {
                cellA.setConnection(Direction.RIGHT, true);
                cellB.setConnection(Direction.LEFT, true);
            } else if (cellA.column() == cellB.column() + 1) {
                cellA.setConnection(Direction.LEFT, true);
                cellB.setConnection(Direction.RIGHT, true);
            } else {
                throw new MazeException("The cells must be adjacent");
            }

        } else {
            throw new MazeException(INVALID_COORDINATES);
        }
    }

    private boolean isValidCoordinate(int row, int col) {
        return row >= 0 && row < height && col >= 0 && col < width;
    }
}
