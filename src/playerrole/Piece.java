package playerrole;

import map.Cell;

public class Piece {
    private Cell currentCell;
    Piece(Cell cell) {
        currentCell = cell;
    }

    void move(Cell cell) {
        currentCell = cell;
    }


    void occurEvent(Cell cell) {

    }

}
