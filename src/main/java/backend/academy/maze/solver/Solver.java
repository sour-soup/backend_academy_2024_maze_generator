package backend.academy.maze.solver;

import backend.academy.maze.model.Coordinate;
import backend.academy.maze.model.Maze;
import java.util.ArrayList;
import java.util.List;

public interface Solver {
    List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end);

    default List<Coordinate> constructPath(Coordinate[][] parent, Coordinate start, Coordinate end) {
        if (parent[end.row()][end.column()] == null) {
            return List.of();
        }
        List<Coordinate> path = new ArrayList<>();
        Coordinate current = end;
        while (current != start) {
            path.add(current);
            current = parent[current.row()][current.column()];
        }
        path.add(start);
        return path.reversed();
    }
}
