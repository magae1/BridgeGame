/*
 * Copyright LWJGL. All rights reserved.
 * License terms: https://www.lwjgl.org/license
 */
package graphic.board;

public class CellG {
    public static CellG cellGs[] = new CellG[2];
    public static byte num_of_tile = 0;
    private byte id;
    private String texture;

    public static final CellG test_cell1 = new CellG("Cell");
    //public static final CellGraphic teste_cell2 = new CellGraphic("tile2");

    public CellG(String texture) {
        this.id = num_of_tile;
        this.texture = texture;
        num_of_tile++;
        if (cellGs[id] != null) {
            throw new IllegalStateException("Board id["+id+"] is already being used.");
        }
        cellGs[id] = this;
    }
    public String getTexture() {
        return texture;
    }
    public byte getId() {
        return id;
    }
}