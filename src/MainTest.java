import graphic.Camera;
import graphic.Renderer;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import playerrole.Board;
import windows.Timer;
import windows.Window;

public class MainTest {
    private static final String MAP_1 = "default";
    private static final String MAP_2 = "another";
    //private static PrintStream printer = new PrintStream(System.out);
    public MainTest() {
        if (!glfwInit()) {
            System.err.println("GLFW failed to initialize.");
            System.exit(1);
        }
        Window win = new Window();
        win.createWindow("Bridge-Board Game");

        Board board = new Board();
        board.createMap();

        Camera camera = new Camera(win.getWidth(), win.getHeight());
        Matrix4f scale = new Matrix4f().scale(27);
        Renderer renderer = new Renderer();
        renderer.setCellList(board.getCellList());
        renderer.setBridgeMap(board.getBridgeMap());

        camera.setPosition(new Vector3f(-100, 0, 0));

        double frame_cap = 1.0/60.0;
        double frame_time = 0;
        int frames = 0;

        double time = Timer.getTime();
        double unprocessed = 0;

        while(!win.shouldClose()) {
            boolean can_render = false;

            double time_2 = Timer.getTime();
            double passed = time_2 - time;

            unprocessed += passed;
            frame_time += passed;

            time = time_2;

            while (unprocessed >= frame_cap) {
                unprocessed -= frame_cap;
                can_render = true;

                if (win.getKeyMouseHandler().isKeyPressed(GLFW_KEY_ESCAPE)) {
                    System.out.println("True!");
                    glfwSetWindowShouldClose(win.getWindow(), true);
                }

                win.update();

                if (frame_time >= 1.0) {
                    frame_time = 0;
                    System.out.printf("FPS: %d \n", frames);
                    frames = 0;
                }
            }

            if (can_render) {
                glClear(GL_COLOR_BUFFER_BIT);

                renderer.render(scale, camera);

                win.swapBuffer();
                frames++;
            }
        }
        glfwTerminate();
    }
    public static void main(String[] args) {
        new MainTest();
    }
}
