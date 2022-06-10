import playerrole.GameSystem;
import windows.Timer;
import windows.Window;
import graphic.Renderer;

import java.io.PrintStream;
import java.util.Scanner;

public class GameTest {
    private static PrintStream printer = new PrintStream(System.out);
    private static Scanner scanner = new Scanner(System.in);
    private static final String MAP_1 = "default";
    private static final String MAP_2 = "another";
    private static final int WINDOW_WIDTH = 960;
    private static final int WINDOW_HEIGHT = 720;

    public static void main(String[] args) {
        Window win = new Window(WINDOW_WIDTH, WINDOW_HEIGHT);
        win.createWindow("Bridge-Board Game");

        GameSystem gameSystem = new GameSystem(scanner, printer);
        gameSystem.initGame();
        gameSystem.start();

        Renderer renderer = new Renderer(WINDOW_WIDTH, WINDOW_HEIGHT);
        renderer.setCellList(gameSystem.getBoard().getCellList());
        renderer.setBridgeMap(gameSystem.getBoard().getBridgeMap());
        renderer.setPlayers(gameSystem.getPlayers());

        final double frame_cap = 1.0/60.0;
        double time = Timer.getTime();
        double unprocessed = 0;

        while(!win.shouldClose() || !gameSystem.isAlive()) {
            boolean can_render = false;

            double time_2 = Timer.getTime();
            double passed = time_2 - time;

            unprocessed += passed;

            time = time_2;

            while (unprocessed >= frame_cap) {
                unprocessed -= frame_cap;
                can_render = true;
                renderer.camUpdate();
                win.update();
            }
            if (can_render) {
                win.clear();
                renderer.render();
                win.swapBuffers();
            }
        }
        win.terminateWindow();
    }
}
