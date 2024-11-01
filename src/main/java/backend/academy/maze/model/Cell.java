package backend.academy.maze.model;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;

public final class Cell {
    @Getter private final int row;
    @Getter private final int column;
    @Getter private final SurfaceType surfaceType;
    private final Map<Direction, Boolean> connections;

    public Cell(int row, int column, SurfaceType surfaceType) {
        this.row = row;
        this.column = column;
        this.surfaceType = surfaceType;
        this.connections = new EnumMap<>(Direction.class);

        for (Direction direction : Direction.values()) {
            connections.put(direction, Boolean.FALSE);
        }
    }

    public List<Direction> getDirections() {
        return connections.entrySet().stream()
            .filter(Map.Entry::getValue)
            .map(Map.Entry::getKey)
            .toList();
    }

    public void setConnection(Direction direction, boolean value) {
        connections.put(direction, value);
    }

    @Getter
    public enum SurfaceType {
        NORMAL(1.0, 0.7),
        SLOW(1.0, 0.2),
        SPEED(1.0, 0.1);

        private final double weight;
        private final double probability;

        SurfaceType(double weight, double probability) {
            this.weight = weight;
            this.probability = probability;
        }

    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }
}
