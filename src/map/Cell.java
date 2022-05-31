package map;

public class Cell {
    private final char CELLTYPE;
    private final char[] NEARCELLS;

    Cell(char cellType, char[] nearCells) {
        CELLTYPE = cellType;
        NEARCELLS = new char[nearCells.length];
        for (int i = 0; i < nearCells.length; i++) {
            NEARCELLS[i] = nearCells[i];
        }
    }
    Cell(char cellType) {
        CELLTYPE = cellType;
        NEARCELLS = null;
    }

}
