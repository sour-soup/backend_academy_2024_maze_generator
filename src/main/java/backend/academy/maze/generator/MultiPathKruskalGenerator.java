package backend.academy.maze.generator;

import backend.academy.maze.model.Cell.SurfaceType;
import backend.academy.maze.model.Edge;
import backend.academy.maze.model.Maze;
import backend.academy.maze.utils.DisjointSetUnion;
import java.util.List;
import static backend.academy.maze.utils.MazeUtils.getAllEdgesFromMaze;
import static backend.academy.maze.utils.MazeUtils.getIndex;
import static backend.academy.maze.utils.RandomUtils.getRandomDouble;
import static backend.academy.maze.utils.RandomUtils.shuffle;

public class MultiPathKruskalGenerator implements Generator {
    private static final double BREAK_WALL_PROBABILITY = 0.2;

    @Override
    public Maze generate(int height, int width) {
        Maze maze = new Maze(height, width);
        initializeSurfaceTypes(maze);

        int numberOfVertices = height * width;
        DisjointSetUnion dsu = new DisjointSetUnion(numberOfVertices);
        List<Edge> edges = shuffle(getAllEdgesFromMaze(maze));

        for (Edge edge : edges) {
            int fromIndex = getIndex(edge.from(), maze);
            int toIndex = getIndex(edge.to(), maze);

            if (dsu.union(fromIndex, toIndex) || getRandomDouble() < BREAK_WALL_PROBABILITY) {
                maze.connectCells(edge.from(), edge.to());
            }
        }

        return maze;
    }

    private void initializeSurfaceTypes(Maze maze) {
        for (int row = 0; row < maze.height(); row++) {
            for (int col = 0; col < maze.width(); col++) {
                SurfaceType surfaceType = getRandomSurfaceType();
                maze.setCell(row, col, surfaceType);
            }
        }
    }

    private SurfaceType getRandomSurfaceType() {
        double probability = getRandomDouble();
        double cumulativeProbability = 0.0;

        for (SurfaceType surfaceType : SurfaceType.values()) {
            cumulativeProbability += surfaceType.probability();
            if (probability <= cumulativeProbability) {
                return surfaceType;
            }
        }
        return SurfaceType.NORMAL;
    }
}
