package backend.academy.maze.generator;

import backend.academy.maze.model.Cell.Direction;
import backend.academy.maze.model.Coordinate;
import backend.academy.maze.model.Edge;
import backend.academy.maze.model.Maze;
import backend.academy.maze.utils.DisjointSetUnion;
import java.util.ArrayList;
import java.util.List;
import static backend.academy.maze.utils.CoordinateMazeUtils.getIndex;
import static backend.academy.maze.utils.CoordinateMazeUtils.getNextCoordinate;
import static backend.academy.maze.utils.CoordinateMazeUtils.isValidCoordinate;
import static backend.academy.maze.utils.RandomUtils.shuffle;

public class KruskalGenerator implements Generator {
    private static final double DEFAULT_WEIGHT = 1;

    @Override public Maze generate(int height, int width) {
        Maze maze = new Maze(height, width);
        int numberOfVertices = height * width;

        DisjointSetUnion dsu = new DisjointSetUnion(numberOfVertices);
        List<Edge> edges = shuffle(getAllEdgesFromMaze(maze));

        for (Edge edge : edges) {
            int fromIndex = getIndex(edge.from(), maze);
            int toIndex = getIndex(edge.to(), maze);
            if (dsu.union(fromIndex, toIndex)) {
                maze.connectCells(edge.from(), edge.to());
            }
        }

        return maze;
    }

    private static List<Edge> getAllEdgesFromMaze(Maze maze) {
        List<Edge> edges = new ArrayList<>();
        for (int row = 0; row < maze.height(); row++) {
            for (int col = 0; col < maze.width(); col++) {
                Coordinate from = new Coordinate(row, col);

                for (Direction direction : Direction.values()) {
                    Coordinate to = getNextCoordinate(from, direction);
                    if (isValidCoordinate(to, maze)) {
                        edges.add(new Edge(from, to, DEFAULT_WEIGHT));
                    }
                }
            }
        }
        return edges;
    }
}
