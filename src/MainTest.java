import graphic.Camera;
import graphic.board.CellsRenderer;
import graphic.rendertools.Shader;
import graphic.models.BasicModel;
import graphic.rendertools.Texture;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.io.PrintStream;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import playerrole.Board;
import playerrole.cells.CellDirection;
import playerrole.cells.CellTypes;
import windows.Timer;
import windows.Window;

public class MainTest {
    //private static PrintStream printer = new PrintStream(System.out);
    public MainTest() {
        if (!glfwInit()) {
            System.err.println("GLFW failed to initialize.");
            System.exit(1);
        }
        Window win = new Window();
        win.createWindow("Bridge-Board Game");


        Shader shader = new Shader("shader");
        Camera camera = new Camera(win.getWidth(), win.getHeight());
        Matrix4f projection = new Matrix4f()
                .setOrtho2D(-win.getWidth()/2, win.getWidth()/2, -win.getHeight()/2, win.getHeight()/2);
        Matrix4f scale = new Matrix4f().scale(16);
        Matrix4f target = new Matrix4f();
        CellsRenderer cellsRenderer = new CellsRenderer();

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
                target = scale;

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

                for (int i = 0; i < 8; i++)
                    cellsRenderer.renderCell((byte) 0, i,i, shader, scale, camera);

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
