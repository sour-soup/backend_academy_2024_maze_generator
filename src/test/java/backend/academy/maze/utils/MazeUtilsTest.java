package backend.academy.maze.utils;

import backend.academy.maze.model.Coordinate;
import backend.academy.maze.model.Maze;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class MazeUtilsTest {
    private static final int HEIGHT = 5;
    private static final int WIDTH = 5;

    @Test
    @DisplayName("IsValidCoordinate should return true when coordinate in grid")
    void isValidCoordinate_ShouldReturnTrue_WhenCoordinateInGrid() {
        // Arrange
        Maze maze = new Maze(HEIGHT, WIDTH);
        Coordinate coordinate = new Coordinate(1, 2);

        // Act
        boolean result = MazeUtils.isValidCoordinate(coordinate, maze);

        // Assert
        assertThat(result).isTrue();
    }

    @ParameterizedTest
    @MethodSource("provideInvalidCoordinates")
    @DisplayName("IsValidCoordinate should return true when coordinate is invalid")
    void isValidCoordinate_ShouldReturnTrue_WhenCoordinateIsInvalid(Coordinate coordinate) {
        // Arrange
        Maze maze = new Maze(HEIGHT, WIDTH);

        // Act
        boolean result = MazeUtils.isValidCoordinate(coordinate, maze);

        // Assert
        assertThat(result).isFalse();
    }

    private static Coordinate[] provideInvalidCoordinates() {
        return new Coordinate[] {
            new Coordinate(-1, 0),
            new Coordinate(0, -1),
            new Coordinate(HEIGHT, 0),
            new Coordinate(0, HEIGHT)
        };
    }
}
