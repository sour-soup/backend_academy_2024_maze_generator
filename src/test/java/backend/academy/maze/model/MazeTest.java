package backend.academy.maze.model;

import backend.academy.maze.exception.MazeException;
import backend.academy.maze.model.Cell.Direction;
import backend.academy.maze.model.Cell.SurfaceType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class MazeTest {
    private static final String INVALID_COORDINATES = "Invalid cell coordinates";
    private static final int HEIGHT = 5;
    private static final int WIDTH = 5;

    @Test
    @DisplayName("Maze initialized with correct height and width after creation")
    void Maze_InitializedWithCorrectHeightAndWidth_AfterCreation() {
        // Act
        Maze maze = new Maze(HEIGHT, WIDTH);

        // Assert
        assertThat(maze.height()).isEqualTo(HEIGHT);
        assertThat(maze.width()).isEqualTo(WIDTH);

        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                assertThat(maze.getCell(row, col).surfaceType()).isEqualTo(SurfaceType.NORMAL);
            }
        }
    }

    @Test
    @DisplayName("GetCell returns correct cell object for valid coordinates")
    void GetCell_ReturnsCorrectCellObject_ForValidCoordinates() {
        // Arrange
        Maze maze = new Maze(HEIGHT, WIDTH);
        int expectedRow = 2;
        int expectedColumn = 3;

        // Act
        Cell cell = maze.getCell(expectedRow, expectedColumn);

        // Assert
        assertThat(cell.row()).isEqualTo(expectedRow);
        assertThat(cell.column()).isEqualTo(expectedColumn);
    }

    @ParameterizedTest
    @MethodSource("provideInvalidCoordinates")
    @DisplayName("Get cell throws MazeException for invalid coordinates")
    void GetCell_ThrowsMazeException_ForInvalidCoordinates(int invalidRow, int invalidColumn) {
        // Arrange
        Maze maze = new Maze(HEIGHT, WIDTH);

        // Act & Assert
        assertThatThrownBy(() -> maze.getCell(invalidRow, invalidColumn))
            .isInstanceOf(MazeException.class)
            .hasMessage(INVALID_COORDINATES);
    }

    @Test
    @DisplayName("Set cell updates surface type correctly for valid coordinates")
    void SetCell_UpdatesSurfaceType_CorrectlyForValidCoordinates() {
        // Arrange
        Maze maze = new Maze(HEIGHT, WIDTH);
        int row = 2;
        int column = 3;
        SurfaceType expectedSurfaceType = SurfaceType.SLOW;

        // Act
        maze.setCell(row, column, expectedSurfaceType);
        Cell cell = maze.getCell(row, column);

        // Assert
        assertThat(cell.row())
            .isEqualTo(row);
        assertThat(cell.column())
            .isEqualTo(column);
        assertThat(cell.surfaceType())
            .isEqualTo(expectedSurfaceType);
    }

    @ParameterizedTest
    @MethodSource("provideInvalidCoordinates")
    @DisplayName("Set cell throws MazeException for invalid coordinates")
    void SetCell_ThrowsMazeException_ForInvalidCoordinates(int invalidRow, int invalidColumn) {
        // Arrange
        Maze maze = new Maze(HEIGHT, WIDTH);

        // Act & Assert
        assertThatThrownBy(() -> maze.setCell(invalidRow, invalidColumn, SurfaceType.NORMAL))
            .isInstanceOf(MazeException.class)
            .hasMessage(INVALID_COORDINATES);
    }

    @ParameterizedTest
    @MethodSource("provideAdjacentCellCoordinates")
    @DisplayName("Connect cells successfully updates connections for adjacent cells")
    void ConnectCells_SuccessfullyUpdatesConnections_ForAdjacentCells(
        Coordinate coordinateA,
        Coordinate coordinateB,
        Direction expectedDirectionA,
        Direction expectedDirectionB
    ) {
        // Arrange
        Maze maze = new Maze(HEIGHT, WIDTH);

        // Act
        maze.connectCells(coordinateA, coordinateB);
        Cell cellA = maze.getCell(coordinateA.row(), coordinateA.column());
        Cell cellB = maze.getCell(coordinateB.row(), coordinateB.column());

        // Assert
        assertThat(cellA.getDirections())
            .containsExactly(expectedDirectionA);
        assertThat(cellB.getDirections())
            .containsExactly(expectedDirectionB);
    }

    @Test
    @DisplayName("Connect cells throws MazeException for non-adjacent cells")
    void ConnectCells_ThrowsMazeException_ForNonAdjacentCells() {
        // Arrange
        Maze maze = new Maze(HEIGHT, WIDTH);
        Coordinate coordinateA = new Coordinate(0, 0);
        Coordinate coordinateB = new Coordinate(0, 2); // Non-adjacent

        // Act & Assert
        assertThatThrownBy(() -> maze.connectCells(coordinateA, coordinateB))
            .isInstanceOf(MazeException.class)
            .hasMessage("The cells must be adjacent");
    }

    @ParameterizedTest
    @MethodSource("provideInvalidCoordinatePairs")
    @DisplayName("Connect cells throws MazeException for invalid coordinates")
    void ConnectCells_ThrowsMazeException_ForInvalidCoordinates(
        Coordinate invalidCoordinateA,
        Coordinate invalidCoordinateB
    ) {
        // Arrange
        Maze maze = new Maze(HEIGHT, WIDTH);

        // Act & Assert
        assertThatThrownBy(() -> maze.connectCells(invalidCoordinateA, invalidCoordinateB))
            .isInstanceOf(MazeException.class)
            .hasMessage(INVALID_COORDINATES);
    }

    private static Object[][] provideInvalidCoordinates() {
        return new Object[][] {
            {-1, 0},
            {0, -1},
            {HEIGHT, 0},
            {0, WIDTH}
        };
    }

    private static Object[][] provideInvalidCoordinatePairs() {
        return new Object[][] {
            {new Coordinate(-1, 0), new Coordinate(0, 0)},
            {new Coordinate(0, 0), new Coordinate(0, -1)},
            {new Coordinate(HEIGHT, 0), new Coordinate(0, 0)},
            {new Coordinate(0, WIDTH), new Coordinate(0, 0)},
        };
    }

    private static Object[][] provideAdjacentCellCoordinates() {
        return new Object[][] {
            {new Coordinate(1, 1), new Coordinate(1, 2), Direction.RIGHT, Direction.LEFT}, // Right
            {new Coordinate(1, 2), new Coordinate(1, 1), Direction.LEFT, Direction.RIGHT}, // Left
            {new Coordinate(1, 1), new Coordinate(2, 1), Direction.DOWN, Direction.UP}, // Down
            {new Coordinate(2, 1), new Coordinate(1, 1), Direction.UP, Direction.DOWN}, // Up
            {new Coordinate(1, 1), new Coordinate(1, 0), Direction.LEFT, Direction.RIGHT}, // Left
            {new Coordinate(1, 0), new Coordinate(1, 1), Direction.RIGHT, Direction.LEFT}, // Right
            {new Coordinate(2, 1), new Coordinate(2, 0), Direction.LEFT, Direction.RIGHT}, // Left
            {new Coordinate(2, 0), new Coordinate(2, 1), Direction.RIGHT, Direction.LEFT} // Right
        };
    }
}
