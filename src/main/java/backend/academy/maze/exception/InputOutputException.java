package backend.academy.maze.exception;

public class InputOutputException extends RuntimeException {
    public InputOutputException(String message) {
        super(message);
    }

    public InputOutputException(String message, Throwable cause) {
        super(message, cause);
    }
}
