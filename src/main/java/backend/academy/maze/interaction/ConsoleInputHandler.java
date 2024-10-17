package backend.academy.maze.interaction;

import backend.academy.maze.exception.EmptyInputOutputException;
import backend.academy.maze.exception.InputOutputException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ConsoleInputHandler implements InputHandler {
    private final BufferedReader bufferedReader;

    public ConsoleInputHandler(InputStream inputStream) {
        this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    }

    @Override
    public String inputString() {
        try {
            String input = bufferedReader.readLine();
            if (input == null) {
                throw new EmptyInputOutputException("No input received");
            }
            return input;
        } catch (IOException e) {
            throw new InputOutputException("Error reading string input", e);
        }
    }

    @Override
    public Integer inputInteger() {
        try {
            return Integer.valueOf(inputString());
        } catch (NumberFormatException e) {
            throw new InputOutputException("Error parsing integer input", e);
        }
    }
}
