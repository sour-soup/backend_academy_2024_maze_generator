package backend.academy.maze.solver;

import backend.academy.maze.exception.MazeException;
import backend.academy.maze.model.Cell;
import backend.academy.maze.model.Cell.SurfaceType;
import backend.academy.maze.model.Coordinate;
import backend.academy.maze.model.Maze;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static backend.academy.maze.utils.MazeUtils.getDirection;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DijkstraSolverTest {
    private static final int HEIGHT = 3;
    private static final int WIDTH = 3;

    private final DijkstraSolver solver = new DijkstraSolver();
    private Maze maze;

    @BeforeEach
    void setUp() {
        maze = mock(Maze.class);
        when(maze.height()).thenReturn(HEIGHT);
        when(maze.width()).thenReturn(WIDTH);
    }

    @Test
    @DisplayName("Solve returns correct path for valid start and end")
    void Solve_ReturnsCorrectPath_ForValidStartAndEnd() {
        // Arrange
        Coordinate start = new Coordinate(0, 0);
        Coordinate middle = new Coordinate(0, 1);
        Coordinate end = new Coordinate(1, 1);

        mockMazeConnections(maze, start, middle, end);

        // Act
        List<Coordinate> path = solver.solve(maze, start, end);

        // Assert
        assertThat(path).containsExactly(start, middle, end);
    }

    @Test
    @DisplayName("Solve returns empty list when no path exists")
    void Solve_ReturnsEmptyList_WhenNoPathExists() {
        // Arrange
        Coordinate start = new Coordinate(0, 0);
        Coordinate middle = new Coordinate(0, 1);
        Coordinate end = new Coordinate(1, 1);

        mockMazeConnections(maze, start, middle);

        // Act
        List<Coordinate> path = solver.solve(maze, start, end);

        // Assert
        assertThat(path).isEmpty();
    }

    @ParameterizedTest
    @MethodSource("provideInvalidCoordinates")
    @DisplayName("Solve throws MazeException for invalid coordinates")
    void Solve_ThrowsMazeException_ForInvalidCoordinates(Coordinate start, Coordinate end) {
        // Act & Assert
        assertThatThrownBy(() -> solver.solve(maze, start, end))
            .isInstanceOf(MazeException.class)
            .hasMessage("Invalid start or end coordinates");
    }

    private static Object[][] provideInvalidCoordinates() {
        return new Object[][] {
            {new Coordinate(-1, 0), new Coordinate(0, 0)},
            {new Coordinate(0, 0), new Coordinate(-1, 0)},
            {new Coordinate(HEIGHT, 0), new Coordinate(0, 0)},
            {new Coordinate(0, WIDTH), new Coordinate(0, 0)},
        };
    }

    private void mockMazeConnections(Maze maze, Coordinate... coordinates) {
        for (int i = 0; i < coordinates.length - 1; i++) {
            Coordinate a = coordinates[i];
            Coordinate b = coordinates[i + 1];
            Cell cellA = mock(Cell.class);
            Cell cellB = mock(Cell.class);

            when(maze.getCell(a.row(), a.column())).thenReturn(cellA);
            when(maze.getCell(b.row(), b.column())).thenReturn(cellB);

            when(cellA.getDirections()).thenReturn(List.of(getDirection(a, b)));
            when(cellB.getDirections()).thenReturn(List.of(getDirection(b, a)));
            when(cellA.surfaceType()).thenReturn(SurfaceType.NORMAL);
            when(cellB.surfaceType()).thenReturn(SurfaceType.NORMAL);
        }
    }
}
