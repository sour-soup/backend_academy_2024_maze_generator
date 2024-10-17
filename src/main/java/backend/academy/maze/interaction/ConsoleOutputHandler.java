package backend.academy.maze.interaction;

import backend.academy.maze.exception.InputOutputException;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class ConsoleOutputHandler implements OutputHandler {
    private final BufferedWriter bufferedWriter;

    public ConsoleOutputHandler(OutputStream outputStream) {
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
    }

    @Override
    public void output(String output) {
        try {
            bufferedWriter.write(output);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            throw new InputOutputException("Error writing output message", e);
        }
    }
}
