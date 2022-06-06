import graphic.Camera;
import graphic.rendertools.Shader;
import graphic.models.BasicModel;
import graphic.rendertools.Texture;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.io.PrintStream;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import windows.Timer;
import windows.Window;

public class MainTest {
    private static PrintStream printer = new PrintStream(System.out);
    public static void main(String[] args) {
        Window win = new Window();
        win.createWindow("Bridge-Board Game");

        BasicModel basicmodel = new BasicModel();
        Shader shader = new Shader("shader");
        Texture texture = new Texture("End.png");
        Camera camera = new Camera(640, 480);
        Matrix4f projection = new Matrix4f()
                .setOrtho2D(-640/2, 640/2, -480/2, 480/2);
        Matrix4f scale = new Matrix4f().scale(32);
        Matrix4f target = new Matrix4f();


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
                if (glfwGetKey(win.getWindow(), GLFW_KEY_ESCAPE) == GLFW_TRUE) {
                    glfwSetWindowShouldClose(win.getWindow(), true);
                }
                glfwPollEvents();
                if (frame_time >= 1.0) {
                    frame_time = 0;
                    System.out.printf("FPS: %d \n", frames);
                    frames = 0;
                }
            }

            if (can_render) {
                glClear(GL_COLOR_BUFFER_BIT);

                shader.bind();
                shader.setUniform("sampler", 0);
                shader.setUniform("projection", camera.projection().mul(target));
                basicmodel.render();
                texture.bind(0);

                win.swapBuffer();
                frames++;
            }
        }

        glfwTerminate();


        /*
        Board board = new Board(printer);
        board.createMap();
        Player player = new Player(board, printer);
        while(player.isPlaying()) {
            board.printBoard();
            player.playOneTurn();
        }
         */
    }

}
