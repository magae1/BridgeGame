package graphic.board;

import graphic.Camera;
import graphic.models.BasicModel;
import graphic.rendertools.Shader;
import graphic.rendertools.Texture;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.HashMap;

public class CellsRenderer {
    private HashMap<String, Texture> cells_textures;
    private BasicModel basicModel;

    public CellsRenderer() {
        cells_textures = new HashMap<>();
        basicModel = new BasicModel();

        for (int i = 0; i < CellG.cellGs.length; i++) {
            if (CellG.cellGs[i] != null) {
                if (!cells_textures.containsKey(CellG.cellGs[i].getTexture())) {
                    String tex = CellG.cellGs[i].getTexture();
                    cells_textures.put(tex, new Texture(tex + ".png"));
                }
            }
        }
    }

    public void renderCell(byte id, int x, int y, Shader shader, Matrix4f world, Camera camera) {
        shader.bind();
        if (cells_textures.containsKey(CellG.cellGs[id].getTexture()))
            cells_textures.get(CellG.cellGs[id].getTexture()).bind(0);

        Matrix4f cellPosition = new Matrix4f().translate(new Vector3f(x*2, y*2, 0));
        Matrix4f target = new Matrix4f();

        camera.getProjection().mul(world, target);
        target.mul(cellPosition);

        shader.setUniform("sampler", 0);
        shader.setUniform("projection", target);

        basicModel.render();
    }
}
