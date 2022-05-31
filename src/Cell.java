public class Cell {
    private final char CELLTYPE;
    private final char[] NEARCELLS;

    Cell(char cellType, char[] nearCells) {
        CELLTYPE = cellType;
        NEARCELLS = nearCells;
    }


}
