package backend.academy.maze;

import backend.academy.maze.app.MazeApp;
import backend.academy.maze.interaction.ConsoleInputHandler;
import backend.academy.maze.interaction.ConsoleOutputHandler;
import backend.academy.maze.renderer.TileRenderer;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    public static void main(String[] args) {
        MazeApp app = new MazeApp(
            new ConsoleInputHandler(System.in),
            new ConsoleOutputHandler(System.out),
            new TileRenderer());

        app.run();
    }
}
