package graphic.gui;

import collision.AABB;
import collision.Collision;
import graphic.Assets;
import graphic.Camera;
import graphic.Shader;
import graphic.TileSheet;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import windows.KeyMouseHandler;

public class Button {
    public static final int STATE_IDLE = 0;
    public static final int STATE_SELECTED = 1;
    public static final int STATE_CLICKED = 2;

    private AABB boundingBox;

    private int selectedState;

    private static Matrix4f transform = new Matrix4f();

    public Button(Vector2f position, Vector2f scale) {
        this.boundingBox = new AABB(position, scale);
        selectedState = STATE_IDLE;
    }

    public void update(KeyMouseHandler keyMouseHandler) {
        Collision data = boundingBox.getCollision(keyMouseHandler.getMousePosition());

        if (data.isIntersecting) {
            selectedState = STATE_SELECTED;

            if (keyMouseHandler.isMouseButtonDown(0)) {
                selectedState = STATE_CLICKED;
            }
        }
        else selectedState = STATE_IDLE;
    }

    public void render(Camera camera, TileSheet sheet, Shader shader) {
        Vector2f position = boundingBox.getCenter(), scale = boundingBox.getHalfExtent();

        transform.identity().translate(position.x, position.y, 0).scale(scale.x, scale.y, 1); // Middle/Fill

        shader.setUniform("projection", camera.getProjection().mul(transform));
        switch (selectedState) {
            case STATE_SELECTED -> sheet.bindTile(shader, 4, 1);
            case STATE_CLICKED -> sheet.bindTile(shader, 7, 1);
            default -> sheet.bindTile(shader, 1, 1);
        }
        Assets.getModel().render();

        renderSides(position, scale, camera, sheet, shader);
        renderCorners(position, scale, camera, sheet, shader);
    }

    private void renderSides(Vector2f position, Vector2f scale, Camera camera, TileSheet sheet, Shader shader) {
        transform.identity().translate(position.x, position.y + scale.y - 16, 0).scale(scale.x, 16, 1); // Top

        shader.setUniform("projection", camera.getProjection().mul(transform));
        switch (selectedState) {
            case STATE_SELECTED -> sheet.bindTile(shader, 4, 0);
            case STATE_CLICKED -> sheet.bindTile(shader, 7, 0);
            default -> sheet.bindTile(shader, 1, 0);
        }
        Assets.getModel().render();

        transform.identity().translate(position.x, position.y - scale.y + 16, 0).scale(scale.x, 16, 1); // Bottom

        shader.setUniform("projection", camera.getProjection().mul(transform));
        switch (selectedState) {
            case STATE_SELECTED -> sheet.bindTile(shader, 4, 2);
            case STATE_CLICKED -> sheet.bindTile(shader, 7, 2);
            default -> sheet.bindTile(shader, 1, 2);
        }
        Assets.getModel().render();

        transform.identity().translate(position.x - scale.x + 16, position.y, 0).scale(16, scale.y, 1); // Left

        shader.setUniform("projection", camera.getProjection().mul(transform));
        switch (selectedState) {
            case STATE_SELECTED -> sheet.bindTile(shader, 3, 1);
            case STATE_CLICKED -> sheet.bindTile(shader, 6, 1);
            default -> sheet.bindTile(shader, 0, 1);
        }
        Assets.getModel().render();

        transform.identity().translate(position.x + scale.x - 16, position.y, 0).scale(16, scale.y, 1); // Right

        shader.setUniform("projection", camera.getProjection().mul(transform));
        switch (selectedState) {
            case STATE_SELECTED -> sheet.bindTile(shader, 5, 1);
            case STATE_CLICKED -> sheet.bindTile(shader, 8, 1);
            default -> sheet.bindTile(shader, 2, 1);
        }
        Assets.getModel().render();
    }

    private void renderCorners(Vector2f position, Vector2f scale, Camera camera, TileSheet sheet, Shader shader) {
        transform.identity().translate(position.x - scale.x + 16, position.y + scale.y - 16, 0).scale(16, 16, 1); // Top
        // Left

        shader.setUniform("projection", camera.getProjection().mul(transform));
        switch (selectedState) {
            case STATE_SELECTED -> sheet.bindTile(shader, 3, 0);
            case STATE_CLICKED -> sheet.bindTile(shader, 6, 0);
            default -> sheet.bindTile(shader, 0, 0);
        }
        Assets.getModel().render();

        transform.identity().translate(position.x + scale.x - 16, position.y + scale.y - 16, 0).scale(16, 16, 1); // Top
        // Right

        shader.setUniform("projection", camera.getProjection().mul(transform));
        switch (selectedState) {
            case STATE_SELECTED -> sheet.bindTile(shader, 5, 0);
            case STATE_CLICKED -> sheet.bindTile(shader, 8, 0);
            default -> sheet.bindTile(shader, 2, 0);
        }
        Assets.getModel().render();

        transform.identity().translate(position.x - scale.x + 16, position.y - scale.y + 16, 0).scale(16, 16, 1); // Bottom
        // Left

        shader.setUniform("projection", camera.getProjection().mul(transform));
        switch (selectedState) {
            case STATE_SELECTED -> sheet.bindTile(shader, 3, 2);
            case STATE_CLICKED -> sheet.bindTile(shader, 6, 2);
            default -> sheet.bindTile(shader, 0, 2);
        }
        Assets.getModel().render();

        transform.identity().translate(position.x + scale.x - 16, position.y - scale.y + 16, 0).scale(16, 16, 1); // Bottom
        // Right

        shader.setUniform("projection", camera.getProjection().mul(transform));
        switch (selectedState) {
            case STATE_SELECTED -> sheet.bindTile(shader, 5, 2);
            case STATE_CLICKED -> sheet.bindTile(shader, 8, 2);
            default -> sheet.bindTile(shader, 2, 2);
        }
        Assets.getModel().render();
    }
}