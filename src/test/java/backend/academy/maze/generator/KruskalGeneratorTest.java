package backend.academy.maze.generator;

import backend.academy.maze.model.Cell;
import backend.academy.maze.model.Coordinate;
import backend.academy.maze.model.Maze;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static backend.academy.maze.utils.MazeUtils.getNextCoordinate;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class KruskalGeneratorTest {
    private final KruskalGenerator generator = new KruskalGenerator();

    @Test
    @DisplayName("Generate should create a maze with correct dimensions")
    void generate_ShouldCreateMazeWithCorrectDimensions() {
        // Arrange
        int height = 10;
        int width = 10;

        // Act
        Maze maze = generator.generate(height, width);

        // Assert
        assertThat(maze.height()).isEqualTo(height);
        assertThat(maze.width()).isEqualTo(width);
    }

    @Test
    @DisplayName("Generate should connect all cells")
    void generate_ShouldConnectAllCells() {
        // Arrange
        int height = 10;
        int width = 10;

        // Act
        Maze maze = generator.generate(height, width);

        // Assert
        boolean[][] visited = new boolean[height][width];
        Coordinate start = new Coordinate(0, 0);
        visitMaze(start, visited, maze);

        boolean allReachable = true;
        for (boolean[] row : visited) {
            for (boolean cell : row) {
                if (!cell) {
                    allReachable = false;
                    break;
                }
            }
        }

        assertThat(allReachable).isTrue();
    }

    @Test
    @DisplayName("Generate should create a acyclic maze")
    void generate_ShouldCreateAcyclicMaze() {
        // Arrange
        int height = 10;
        int width = 10;

        // Act
        Maze maze = generator.generate(height, width);

        int countEdges = 0;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Cell cell = maze.getCell(row, col);
                countEdges += cell.getDirections().size();
            }
        }
        countEdges /= 2;

        int countVertices = height * width;
        assertThat(countVertices).isEqualTo(countEdges + 1);
    }

    private void visitMaze(Coordinate current, boolean[][] visited, Maze maze) {
        visited[current.row()][current.column()] = true;

        Cell currentCell = maze.getCell(current.row(), current.column());
        for (Cell.Direction direction : currentCell.getDirections()) {
            Coordinate next = getNextCoordinate(current, direction);
            if (!visited[next.row()][next.column()]) {
                visitMaze(next, visited, maze);
            }
        }
    }
}
