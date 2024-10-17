package backend.academy.maze.solver;

import backend.academy.maze.exception.MazeException;
import backend.academy.maze.model.Cell;
import backend.academy.maze.model.Cell.Direction;
import backend.academy.maze.model.Coordinate;
import backend.academy.maze.model.Maze;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import static backend.academy.maze.utils.MazeUtils.getNextCoordinate;
import static backend.academy.maze.utils.MazeUtils.isValidCoordinate;

public class BreadthFirstSolver implements Solver {
    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        if (!isValidCoordinate(start, maze) || !isValidCoordinate(end, maze)) {
            throw new MazeException("Invalid start or end coordinates");
        }

        Coordinate[][] parent = new Coordinate[maze.height()][maze.width()];
        Queue<Coordinate> queue = new LinkedList<>();

        queue.add(start);
        parent[start.row()][start.column()] = new Coordinate(-1, -1);

        while (!queue.isEmpty()) {
            Coordinate current = queue.poll();

            Cell cell = maze.getCell(current.row(), current.column());
            for (Direction direction : cell.getDirections()) {
                Coordinate next = getNextCoordinate(current, direction);
                if (isValidCoordinate(next, maze) && parent[next.row()][next.column()] == null) {
                    parent[next.row()][next.column()] = current;
                    queue.add(next);
                }
            }
        }

        return constructPath(parent, start, end);
    }
}
