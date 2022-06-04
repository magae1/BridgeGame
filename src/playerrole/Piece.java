package playerrole;

import map.cells.Cell;
import map.Map;

import java.io.PrintStream;

public class Piece {
    private Cell currentCell;
    private Map map;
    private PrintStream printer;
    Piece(Map map, PrintStream printer) {
        currentCell = null;
        this.printer = printer;
        this.map = map;
        putOnStartCell();
    }
    boolean checkMovement(String movements) {
        return map.movementCheck(currentCell, movements);
    }
    private void putOnStartCell() {
        try{
            currentCell = map.getCellList().get(0);
        } catch (IndexOutOfBoundsException e) {
            printer.println("Map isn't created.");
            e.printStackTrace();
        }
    }
}
