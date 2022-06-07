package graphic;

import graphic.models.BasicModel;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import playerrole.cells.Cell;
import playerrole.cells.CellTypes;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Renderer {
    private HashMap<CellTypes, Texture> cells_textures;
    private Texture bridge_texture;
    private BasicModel basicModel;
    private Shader shader;
    private List<Cell> cellList;
    private Map<Cell, Cell> bridgeMap;
    public Renderer() {
        cells_textures = new HashMap<>();
        basicModel = new BasicModel();
        shader = new Shader("shader");

        for (CellTypes cellType : CellTypes.values()) {
            try {
                cells_textures.put(cellType, new Texture(cellType.name() + ".png"));
            } catch (IOException e) {
                try {
                    cells_textures.put(cellType, new Texture( "CELL.png"));
                } catch (IOException ie) {
                    System.err.println("ERROR : Can't create cell texture.");
                }
            }
        }
        try {
            bridge_texture = new Texture("BRIDGE.png");
        } catch (IOException e) {
            System.err.println("ERROR : Can't create bridge texture.");
        }
    }
    public void setCellList(List<Cell> cellList) {
        this.cellList = cellList;
    }
    public void setBridgeMap(Map<Cell, Cell> bridgeMap) {
        this.bridgeMap = bridgeMap;
    }
    public void render(Matrix4f world, Camera camera) {
        renderCells(world, camera);
        renderBridges(world, camera);
    }
    public void renderCells(Matrix4f world, Camera camera) {
        for (Cell cell : cellList) {
            shader.bind();
            if (cells_textures.containsKey(cell.getCELL_TYPE()))
                cells_textures.get(cell.getCELL_TYPE()).bind(0);
            Matrix4f cellPosition = new Matrix4f().translate(new Vector3f(cell.getPOSITION().getXpos()*2, -cell.getPOSITION().getYpos()*2, 0));
            Matrix4f target = new Matrix4f();

            camera.getProjection().mul(world, target);
            target.mul(cellPosition);

            shader.setUniform("sampler", 0);
            shader.setUniform("projection", target);

            basicModel.render();
        }
    }
    private void renderBridges(Matrix4f world, Camera camera) {
        for (java.util.Map.Entry<Cell, Cell> bridge : bridgeMap.entrySet()) {
            Cell startCell = bridge.getKey();
            Cell endCell = bridge.getValue();
            for (int i = startCell.getPOSITION().getXpos()+1; i < endCell.getPOSITION().getXpos(); i++) {
                shader.bind();
                bridge_texture.bind(0);

                Matrix4f bridgePosition = new Matrix4f().translate(new Vector3f(i*2, -startCell.getPOSITION().getYpos()*2, 0));
                Matrix4f target = new Matrix4f();

                camera.getProjection().mul(world, target);
                target.mul(bridgePosition);

                shader.setUniform("sampler", 0);
                shader.setUniform("projection", target);

                basicModel.render();
            }
        }
    }
    private void renderPieces(Matrix4f world, Camera camera) {
        for (Cell cell : cellList) {


        }
    }
}
