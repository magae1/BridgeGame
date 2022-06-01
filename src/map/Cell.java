package map;

class Cell {
    private final CellTypes CELL_TYPE;
    private final CellDirection PRE_DIRECTION;
    private final CellDirection NEXT_DIRECTION;
    private final int CELL_INDEX;
    private static int countCells = 0;
    Cell(CellTypes cellType, CellDirection preDirection, CellDirection nextDirection) {
        CELL_TYPE = cellType;
        PRE_DIRECTION = preDirection;
        NEXT_DIRECTION = nextDirection;
        CELL_INDEX = ++countCells;
    }
    Cell(CellTypes cellType, CellDirection nextDirection) {
        this(cellType, null, nextDirection);
    }
    Cell(CellTypes cellType) {
        this(cellType, null, null);
    }
    private Cell() {
        this(null, null, null);
    }

    public CellTypes getCELL_TYPE() {
        return CELL_TYPE;
    }

    public CellDirection getPRE_DIRECTION() {
        return PRE_DIRECTION;
    }

    public CellDirection getNEXT_DIRECTION() {
        return NEXT_DIRECTION;
    }
    public int getCELL_INDEX() {
        return CELL_INDEX;
    }
    public static void setCountCells(int countCells) {
        Cell.countCells = countCells;
    }
}
