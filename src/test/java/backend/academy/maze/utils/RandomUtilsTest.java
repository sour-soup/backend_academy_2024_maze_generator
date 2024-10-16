package backend.academy.maze.utils;

import backend.academy.maze.exception.RandomUtilsException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class RandomUtilsTest {
    @Test
    @DisplayName("GetRandomElement should return an element when collection has elements")
    void getRandomElement_ShouldReturnElement_WhenCollectionHasElements() {
        // Arrange
        List<String> elements = List.of("apple", "banana", "cherry");

        // Act
        String randomElement = RandomUtils.getRandomElement(elements);

        // Assert
        assertThat(elements).contains(randomElement);
    }

    @Test
    @DisplayName("getRandomElement should throw RandomUtilsException when collection is empty")
    void getRandomElement_ShouldThrowEmptyCollectionException_WhenCollectionIsEmpty() {
        // Arrange
        List<String> emptyList = List.of();

        // Assert & Act
        assertThatThrownBy(() -> RandomUtils.getRandomElement(emptyList))
            .isInstanceOf(RandomUtilsException.class);
    }

    @Test
    @DisplayName("getRandomInt should return integer in range when bound is positive")
    void getRandomInt_ShouldReturnInt_WhenBoundIsPositive() {
        // Arrange
        int bound = 5;

        // Act
        int randomInt = RandomUtils.getRandomInt(bound);

        // Assert
        assertThat(randomInt)
            .isNotNegative()
            .isLessThan(bound);
    }

    @ParameterizedTest
    @ValueSource(ints = {-5, -1, 0})
    @DisplayName("getRandomInt should throw exception when bound is not positive")
    void getRandomInt_ShouldThrowException_WhenBoundIsNegative(int bound) {
        // Act & Assert
        assertThatThrownBy(() -> RandomUtils.getRandomInt(bound))
            .isInstanceOf(RandomUtilsException.class);
    }
}
