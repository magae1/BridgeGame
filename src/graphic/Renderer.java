package graphic;

import graphic.models.BasicModel;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import playerrole.Piece;
import playerrole.Player;
import playerrole.cells.Cell;
import playerrole.cells.CellTypes;

import java.io.IOException;
import java.util.*;

public class Renderer {
    private final int SCALE = 27;
    private HashMap<CellTypes, Texture> cells_textures;
    private Texture bridge_textures;
    private List<Texture> piece_textures;
    private final BasicModel basicModel;
    private final Shader shader;
    private List<Cell> cellList;
    private Map<Cell, Cell> bridgeMap;
    private List<Player> players;
    private Camera camera;
    private final Matrix4f world;
    public Renderer(int windowWidth, int windowHeight) {
        camera = new Camera(windowWidth, windowHeight);
        cells_textures = new HashMap<>(8);
        piece_textures = new ArrayList<>(4);
        basicModel = new BasicModel();
        shader = new Shader("shader");
        world = new Matrix4f().scale(SCALE);
        for (CellTypes cellType : CellTypes.values()) {
            try {
                cells_textures.put(cellType, new Texture(cellType.name()));
            } catch (IOException e) {
                try {
                    cells_textures.put(cellType, new Texture( "CELL"));
                } catch (IOException ie) {
                    System.err.println("ERROR : Can't create cell texture.");
                }
            }
        }
        try {
            bridge_textures = new Texture("BRIDGE");
        } catch (IOException e) {
            System.err.println("ERROR : Can't create bridge texture.");
        }
        for (int i = 1; i <= 4; i++) {
            try {
                piece_textures.add(new Texture("P" + i));
            } catch (IOException e) {
                System.err.println("ERROR : Can't create piece texture.");
            }
        }
    }
    public void setCellList(List<Cell> cellList) {
        this.cellList = cellList;
    }
    public void setBridgeMap(Map<Cell, Cell> bridgeMap) {
        this.bridgeMap = bridgeMap;
    }
    public void setPlayers(List<Player> players) {
        this.players = players;
    }
    public void render() {
        renderCells();
        renderBridges();
        renderPieces();
    }
    public void camUpdate() {
        float xPos = 0;
        float yPos = 0;
        for (Player player : players) {
            xPos += player.getPiece().getCurrentCell().getPOSITION().getXpos();
            yPos += player.getPiece().getCurrentCell().getPOSITION().getYpos();
        }
        xPos /= players.size();
        yPos /= players.size();
        camera.setPosition(new Vector3f(-xPos*SCALE*2, yPos*SCALE*2,0));
    }
    public void renderCells() {
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
    private void renderBridges() {
        for (java.util.Map.Entry<Cell, Cell> bridge : bridgeMap.entrySet()) {
            Cell startCell = bridge.getKey();
            Cell endCell = bridge.getValue();
            for (int i = startCell.getPOSITION().getXpos()+1; i < endCell.getPOSITION().getXpos(); i++) {
                shader.bind();
                bridge_textures.bind(0);

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
    private void renderPieces() {
        for (Cell cell : cellList) {
            if (!cell.getPieceSet().isEmpty()) {
                Set<Piece> pieces = cell.getPieceSet();
                List<Matrix4f> piecePositions = getPositionsByPiecesNumber(cell, pieces.size());
                int i = 0;
                for (Piece piece : pieces) {
                    shader.bind();
                    piece_textures.get(piece.getPlayer().indexOfPlayer-1).bind(0);

                    Matrix4f target = new Matrix4f();
                    camera.getProjection().mul(world,target);
                    target.mul(piecePositions.get(i));
                    shader.setUniform("sampler", 0);
                    shader.setUniform("projection", target);

                    basicModel.render();
                    i++;
                }
            }
        }
    }
    private List<Matrix4f> getPositionsByPiecesNumber(Cell cell, int numOfPieces) {
        List<Matrix4f> matrix4fSet = new ArrayList<>(numOfPieces);
        switch(numOfPieces) {
            case 1 -> matrix4fSet.add(new Matrix4f().translate(new Vector3f(cell.getPOSITION().getXpos()*2, -cell.getPOSITION().getYpos()*2, 0)));
            case 2 -> {
                matrix4fSet.add(new Matrix4f().translate(new Vector3f(cell.getPOSITION().getXpos()*2 - (float)1/3, -cell.getPOSITION().getYpos()*2, 0)));
                matrix4fSet.add(new Matrix4f().translate(new Vector3f(cell.getPOSITION().getXpos()*2 + (float)1/3, -cell.getPOSITION().getYpos()*2, 0)));
            }
            case 3 -> {
                matrix4fSet.add(new Matrix4f().translate(new Vector3f(cell.getPOSITION().getXpos()*2 - (float)1/2, -(cell.getPOSITION().getYpos()*2 + (float)1/2), 0)));
                matrix4fSet.add(new Matrix4f().translate(new Vector3f(cell.getPOSITION().getXpos()*2 + (float)1/2, -(cell.getPOSITION().getYpos()*2 + (float)1/2), 0)));
                matrix4fSet.add(new Matrix4f().translate(new Vector3f(cell.getPOSITION().getXpos()*2, -(cell.getPOSITION().getYpos()*2 - (float)1/2), 0)));
            }
            case 4 -> {
                matrix4fSet.add(new Matrix4f().translate(new Vector3f(cell.getPOSITION().getXpos()*2 - (float)2/3, -(cell.getPOSITION().getYpos()*2 + (float)2/3), 0)));
                matrix4fSet.add(new Matrix4f().translate(new Vector3f(cell.getPOSITION().getXpos()*2 + (float)2/3, -(cell.getPOSITION().getYpos()*2 + (float)2/3), 0)));
                matrix4fSet.add(new Matrix4f().translate(new Vector3f(cell.getPOSITION().getXpos()*2 - (float)2/3, -(cell.getPOSITION().getYpos()*2 - (float)2/3), 0)));
                matrix4fSet.add(new Matrix4f().translate(new Vector3f(cell.getPOSITION().getXpos()*2 + (float)2/3, -(cell.getPOSITION().getYpos()*2 - (float)2/3), 0)));
            }
        }
        return matrix4fSet;
    }
}
