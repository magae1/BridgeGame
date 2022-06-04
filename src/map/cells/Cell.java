package map.cells;

import map.Position;

import java.util.ArrayList;

public class Cell {
    private final CellTypes CELL_TYPE;
    private final ArrayList<CellDirection> DIRECTIONS;
    private final int CELL_INDEX;
    private static int XPosition = 0;
    private static int YPosition = 0;
    private static int countCells = 0;
    private final Position POSITION;

    public Cell(CellTypes cellType, ArrayList<CellDirection> directionArrayList) {
        CELL_TYPE = cellType;
        DIRECTIONS = directionArrayList;
        CELL_INDEX = countCells++;
        POSITION = new Position(XPosition, YPosition);
        try {
            switch(directionArrayList.get(directionArrayList.size()-1)) {
                case UP ->
                    YPosition++;
                case DOWN ->
                    YPosition--;
                case LEFT ->
                    XPosition--;
                case RIGHT ->
                    XPosition++;
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("END OF MAP");
        }
    }
    public Cell(CellTypes cellType) {
        this(cellType, null);
    }
    private Cell() {
        this(null, null);
    }
    public boolean isNextDirection(CellDirection cellDirection) {
        try{
            return (DIRECTIONS.get(DIRECTIONS.size() - 1) == cellDirection) && (CELL_TYPE != CellTypes.END);
        } catch(IndexOutOfBoundsException e) {
            return false;
        }
    }
    public boolean isPreDirection(CellDirection cellDirection) {
        try {
            return (DIRECTIONS.get(1) == cellDirection) && (CELL_TYPE != CellTypes.START);
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }
    public CellTypes getCELL_TYPE() {
        return CELL_TYPE;
    }
    public ArrayList<CellDirection> getDIRECTIONS() { return DIRECTIONS; }
    public int getCELL_INDEX() {
        return CELL_INDEX;
    }
    public Position getPOSITION() {
        return POSITION;
    }
    public static void setCountCells(int countCells) {
        Cell.countCells = countCells;
    }
    public static void setXPosition(int xposition) { Cell.XPosition = xposition; }
    public static void setYPosition(int yposition) { Cell.YPosition = yposition; }
    public String toString() {
        return String.format("Index:%-3d, Type:%-14s, Direction:%-13s, Position:%s", this.getCELL_INDEX(), this.CELL_TYPE.toString(), this.DIRECTIONS.toString(), this.POSITION.toString());
    }
}
