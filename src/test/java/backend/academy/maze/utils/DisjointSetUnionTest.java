package backend.academy.maze.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class DisjointSetUnionTest {
    private static final int SIZE = 10;

    @Test
    @DisplayName("DisjointSetUnion should created with expected size")
    void DisjointSetUnion_ShouldCreatedWithExpectedSize() {
        // Arrange
        int size = SIZE;

        // Act
        DisjointSetUnion disjointSetUnion = new DisjointSetUnion(size);

        // Assert
        assertThat(disjointSetUnion.size()).isEqualTo(size);
    }

    @Test
    @DisplayName("Union should return true when vertices is not connected")
    void union_ShouldReturnTrue_WhenVerticesIsNotConnected() {
        // Arrange
        DisjointSetUnion disjointSetUnion = new DisjointSetUnion(SIZE);
        int u = 1;
        int v = 2;

        // Act
        boolean result = disjointSetUnion.union(u, v);

        // Assert
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Union should return false when vertices is connected")
    void union_ShouldReturnFalse_WhenVerticesIsConnected() {
        // Arrange
        DisjointSetUnion disjointSetUnion = new DisjointSetUnion(SIZE);
        int u = 1;
        int v = 2;
        disjointSetUnion.union(u, v);

        // Act
        boolean result = disjointSetUnion.union(u, v);

        // Assert
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("Union should return false when vertices is connected")
    void union_ShouldReturnFalse_WhenVerticesIsEquals() {
        // Arrange
        DisjointSetUnion disjointSetUnion = new DisjointSetUnion(SIZE);
        int vertex = 1;

        // Act
        boolean result = disjointSetUnion.union(vertex, vertex);

        // Assert
        assertThat(result).isFalse();
    }

    @ParameterizedTest
    @MethodSource("provideInvalidVertexData")
    @DisplayName("Union should throw exception when vertex out of bounds")
    void union_ShouldThrowException_WhenVertexOutOfBounds(int u, int v) {
        // Arrange
        DisjointSetUnion disjointSetUnion = new DisjointSetUnion(SIZE);

        // Act & Assert
        assertThatThrownBy(() -> disjointSetUnion.union(u, v))
            .isInstanceOf(IndexOutOfBoundsException.class);
    }

    private static Object[][] provideInvalidVertexData() {
        return new Object[][] {
            {-1, 0},
            {0, -1},
            {SIZE, 0},
            {0, SIZE}
        };
    }
}
