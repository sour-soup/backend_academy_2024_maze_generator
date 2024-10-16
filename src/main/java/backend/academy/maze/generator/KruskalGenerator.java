package backend.academy.maze.generator;

import backend.academy.maze.model.Edge;
import backend.academy.maze.model.Maze;
import backend.academy.maze.utils.DisjointSetUnion;
import java.util.List;
import static backend.academy.maze.utils.MazeUtils.getAllEdgesFromMaze;
import static backend.academy.maze.utils.MazeUtils.getIndex;
import static backend.academy.maze.utils.RandomUtils.shuffle;

public class KruskalGenerator implements Generator {
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
}
