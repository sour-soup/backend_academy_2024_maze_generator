package backend.academy.maze.renderer;

import backend.academy.maze.model.Cell.Direction;
import backend.academy.maze.model.Cell.SurfaceType;
import backend.academy.maze.model.Coordinate;
import backend.academy.maze.model.Maze;
import java.util.Arrays;
import java.util.List;
import static backend.academy.maze.utils.MazeUtils.getDirection;
import static backend.academy.maze.utils.MazeUtils.getNextCoordinate;

public class TileRenderer implements Renderer {
    private static final String WALL = "⬜";
    private static final String NORMAL_SURFACE = "⬛";
    private static final String SLOW_SURFACE = "🪨";
    private static final String SPEED_SURFACE = "🚗";
    private static final String START = "🟩";
    private static final String END = "🟥";
    private static final String NORMAL_PATH = "🟨";
    private static final String SLOW_PATH = "🐢";
    private static final String SPEED_PATH = "🚀";

    @Override
    public String render(Maze maze) {
        String[][] renderedMaze = initializeMazeWithWallsAndSpaces(maze);

        fillMazeBlocks(maze, renderedMaze);

        return buildStringFromBlocks(renderedMaze);
    }

    @Override
    public String render(Maze maze, List<Coordinate> path) {
        String[][] renderedMaze = initializeMazeWithWallsAndSpaces(maze);

        fillMazeBlocks(maze, renderedMaze);

        renderPath(maze, path, renderedMaze);

        return buildStringFromBlocks(renderedMaze);
    }

    private static String[][] initializeMazeWithWallsAndSpaces(Maze maze) {
        int blocksHeight = 2 * maze.height() + 1;
        int blocksWidth = 2 * maze.width() + 1;

        String[][] renderedMaze = new String[blocksHeight][blocksWidth];

        for (int row = 0; row < blocksHeight; row++) {
            Arrays.fill(renderedMaze[row], NORMAL_SURFACE);
        }

        for (int row = 0; row < blocksHeight; row += 2) {
            for (int col = 0; col < blocksWidth; col += 2) {
                renderedMaze[row][col] = WALL;
            }
        }
        return renderedMaze;
    }

    private static void fillMazeBlocks(Maze maze, String[][] renderedMaze) {
        for (int row = 0; row < maze.height(); row++) {
            for (int col = 0; col < maze.width(); col++) {
                Coordinate block = new Coordinate(2 * row + 1, 2 * col + 1);
                SurfaceType surfaceType = maze.getCell(row, col).surfaceType();
                renderedMaze[block.row()][block.column()] = getSurfaceSymbol(surfaceType);

                List<Direction> openDirections = maze.getCell(row, col).getDirections();
                for (Direction direction : Direction.values()) {
                    if (!openDirections.contains(direction)) {
                        Coordinate wall = getNextCoordinate(block, direction);
                        renderedMaze[wall.row()][wall.column()] = WALL;
                    }
                }
            }
        }
    }

    private static void renderPath(Maze maze, List<Coordinate> path, String[][] renderedMaze) {
        Coordinate start = path.getFirst();
        Coordinate end = path.getLast();

        for (Coordinate current : path) {
            Coordinate block = new Coordinate(2 * current.row() + 1, 2 * current.column() + 1);
            SurfaceType surfaceType = maze.getCell(current.row(), current.column()).surfaceType();

            renderedMaze[block.row()][block.column()] = getPathSymbol(surfaceType);
            if (current.equals(start)) {
                renderedMaze[block.row()][block.column()] = START;
            } else if (current.equals(end)) {
                renderedMaze[block.row()][block.column()] = END;
            }
        }

        for (int i = 0; i + 1 < path.size(); i++) {
            Coordinate current = path.get(i);
            Coordinate next = path.get(i + 1);
            Coordinate block = new Coordinate(2 * current.row() + 1, 2 * current.column() + 1);
            Direction direction = getDirection(current, next);
            Coordinate passage = getNextCoordinate(block, direction);

            renderedMaze[passage.row()][passage.column()] = NORMAL_PATH;
        }
    }

    private static String buildStringFromBlocks(String[][] blocks) {
        StringBuilder result = new StringBuilder();

        int rows = blocks.length;
        int columns = rows > 0 ? blocks[0].length : 0;

        String dotPrefix = "⸱⸱⸱⸱⸱⸱⸱⸱";
        result.append(dotPrefix);
        for (int col = 0; col < columns; col++) {
            if ((col & 1) != 0) {
                result.append(String.format("%2d", col / 2));
            } else {
                result.append("⸱⸱⸱⸱⸱⸱");
            }
        }
        result.append(System.lineSeparator());

        for (int row = 0; row < rows; row++) {
            if ((row & 1) != 0) {
                result.append(String.format("%2d ", row / 2));
            } else {
                result.append(dotPrefix);
            }
            for (String block : blocks[row]) {
                result.append(String.format("%s", block));
            }
            result.append(System.lineSeparator());
        }
        return result.toString();
    }

    private static String getSurfaceSymbol(SurfaceType surfaceType) {
        return switch (surfaceType) {
            case SLOW -> SLOW_SURFACE;
            case SPEED -> SPEED_SURFACE;
            case NORMAL -> NORMAL_SURFACE;
        };
    }

    private static String getPathSymbol(SurfaceType surfaceType) {
        return switch (surfaceType) {
            case SLOW -> SLOW_PATH;
            case SPEED -> SPEED_PATH;
            case NORMAL -> NORMAL_PATH;
        };
    }
}
