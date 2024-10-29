package backend.academy.maze.solver;

import backend.academy.maze.exception.MazeException;
import backend.academy.maze.model.Cell.Direction;
import backend.academy.maze.model.Coordinate;
import backend.academy.maze.model.Maze;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import static backend.academy.maze.utils.MazeUtils.calculateWeight;
import static backend.academy.maze.utils.MazeUtils.getNextCoordinate;
import static backend.academy.maze.utils.MazeUtils.isValidCoordinate;

public class DijkstraSolver implements Solver {
    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        if (!isValidCoordinate(start, maze) || !isValidCoordinate(end, maze)) {
            throw new MazeException("Invalid start or end coordinates");
        }
        if (start.equals(end)) {
            throw new MazeException("Start and end coordinates must not be the same");
        }

        int height = maze.height();
        int width = maze.width();
        double[][] cost = new double[height][width];
        Coordinate[][] parent = new Coordinate[height][width];
        boolean[][] visited = new boolean[height][width];

        for (int row = 0; row < height; row++) {
            Arrays.fill(cost[row], Double.MAX_VALUE);
            Arrays.fill(visited[row], false);
        }
        cost[start.row()][start.column()] = 0;

        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingDouble(Node::cost));
        queue.add(new Node(start, 0));

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            Coordinate current = currentNode.coordinate();

            if (visited[current.row()][current.column()]) {
                continue;
            }
            visited[current.row()][current.column()] = true;

            for (Direction direction : maze.getCell(current.row(), current.column()).getDirections()) {
                Coordinate next = getNextCoordinate(current, direction);
                if (isValidCoordinate(next, maze) && !visited[next.row()][next.column()]) {
                    double movementCost = calculateWeight(maze, current, next);
                    double newCost = cost[current.row()][current.column()] + movementCost;

                    if (newCost < cost[next.row()][next.column()]) {
                        cost[next.row()][next.column()] = newCost;
                        parent[next.row()][next.column()] = current;
                        queue.add(new Node(next, newCost));
                    }
                }
            }
        }

        return constructPath(parent, start, end);
    }

    private record Node(Coordinate coordinate, double cost) {
    }
}
