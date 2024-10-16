package backend.academy.maze.utils;

import backend.academy.maze.exception.RandomUtilsException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class RandomUtils {
    private static final SecureRandom RANDOM = new SecureRandom();

    private RandomUtils() {
    }

    public static <T> T getRandomElement(Collection<T> collection) {
        if (collection == null || collection.isEmpty()) {
            throw new RandomUtilsException("Collection must not be null or empty");
        }

        return collection.stream()
            .skip(RANDOM.nextInt(collection.size()))
            .toList()
            .getFirst();
    }

    public static int getRandomInt(int bound) {
        if (bound <= 0) {
            throw new RandomUtilsException("Bound must be positive");
        }
        return RANDOM.nextInt(bound);
    }

    public static double getRandomDouble() {
        return RANDOM.nextDouble();
    }

    public static <T> List<T> shuffle(Collection<T> collection) {
        List<T> elements = new ArrayList<>(collection);
        Collections.shuffle(elements, RANDOM);
        return elements;
    }
}
