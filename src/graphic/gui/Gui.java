package graphic.gui;

import graphic.Camera;
import graphic.Shader;
import graphic.TileSheet;
import org.joml.Vector2f;
import windows.KeyMouseHandler;
import windows.Window;

public class Gui {
    private Shader shader;
    private Camera camera;
    private TileSheet sheet;

    private Button temporary;

    public Gui(Window window) {
        shader = new Shader("gui");
        camera = new Camera(window.getWidth(), window.getHeight());
        sheet = new TileSheet("gui", 9);
        temporary = new Button(new Vector2f(-32, -32), new Vector2f(96, 96));
    }

    public void resizeCamera(Window window) {
        camera.setProjection(window.getWidth(), window.getHeight());
    }

    public void update(KeyMouseHandler keyMouseHandler) {
        temporary.update(keyMouseHandler);
    }

    public void render() {
        shader.bind();
        temporary.render(camera, sheet, shader);
    }
}