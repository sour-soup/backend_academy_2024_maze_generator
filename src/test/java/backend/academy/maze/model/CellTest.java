package backend.academy.maze.model;

import backend.academy.maze.model.Cell.Direction;
import backend.academy.maze.model.Cell.SurfaceType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class CellTest {
    @Test
    @DisplayName("Cell initialized with correct values")
    void Cell_InitializedWithCorrectValues_AfterCreation() {
        // Arrange
        int expectedRow = 1;
        int expectedColumn = 2;
        SurfaceType expectedSurfaceType = SurfaceType.NORMAL;

        // Act
        Cell cell = new Cell(expectedRow, expectedColumn, expectedSurfaceType);

        // Arrange
        assertThat(cell.row()).isEqualTo(expectedRow);
        assertThat(cell.column()).isEqualTo(expectedColumn);
        assertThat(cell.surfaceType()).isEqualTo(expectedSurfaceType);

        assertThat(cell.getDirections()).isEmpty();
    }

    @Test
    @DisplayName("SetConnection should establish connection")
    void setConnection_ShouldEstablishConnection_WhenValueIsTrue() {
        // Arrange
        Cell cell = new Cell(1, 2, SurfaceType.NORMAL);
        Direction direction = Direction.UP;

        // Act
        cell.setConnection(direction, true);

        // Assert
        assertThat(cell.getDirections()).contains(direction);
    }

    @Test
    @DisplayName("SetConnection should remove connection")
    void setConnection_ShouldRemoveConnection_WhenValueIsFalse() {
        // Arrange
        Cell cell = new Cell(1, 2, SurfaceType.NORMAL);
        cell.setConnection(Direction.DOWN, true);

        // Act
        cell.setConnection(Direction.DOWN, false);

        // Assert
        assertThat(cell.getDirections()).doesNotContain(Direction.DOWN);
    }

    @Test
    @DisplayName("SetConnection should establish multiple connections")
    void setConnection_ShouldEstablishMultipleConnections_WhenConnectionsAreSet() {
        // Arrange
        Cell cell = new Cell(1, 2, SurfaceType.NORMAL);
        cell.setConnection(Direction.LEFT, true);
        cell.setConnection(Direction.RIGHT, true);

        // Act
        var directions = cell.getDirections();

        // Assert
        assertThat(directions).contains(Direction.LEFT);
        assertThat(directions).contains(Direction.RIGHT);
        assertThat(directions).doesNotContain(Direction.UP);
        assertThat(directions).doesNotContain(Direction.DOWN);
    }

    @Test
    @DisplayName("GetDirections should return empty list")
    void getDirections_ShouldReturnEmptyList_WhenNoConnections() {
        // Arrange
        Cell cell = new Cell(1, 2, SurfaceType.NORMAL);

        // Act
        var directions = cell.getDirections();

        // Assert
        assertThat(directions).isEmpty();
    }

    @ParameterizedTest
    @EnumSource(Direction.class)
    @DisplayName("GetDirections should return established direction")
    void getDirections_ShouldReturnEstablishedDirection_WhenConnectionIsSet(Direction direction) {
        // Arrange
        Cell cell = new Cell(1, 2, SurfaceType.NORMAL);
        cell.setConnection(direction, true);

        // Act
        var directions = cell.getDirections();

        // Assert
        assertThat(directions).containsExactly(direction);
    }

    @Test
    @DisplayName("GetDirections should return several directions")
    void getDirections_ShouldReturnSeveralDirections_WhenSeveralConnectionsAreEstablished() {
        // Arrange
        Cell cell = new Cell(1, 2, SurfaceType.NORMAL);
        cell.setConnection(Direction.UP, true);
        cell.setConnection(Direction.LEFT, true);

        // Act
        var directions = cell.getDirections();

        // Assert
        assertThat(directions).contains(Direction.UP, Direction.LEFT);
        assertThat(directions).doesNotContain(Direction.DOWN);
        assertThat(directions).doesNotContain(Direction.RIGHT);
    }
}
