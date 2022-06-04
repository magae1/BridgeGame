package playerrole;

import map.cells.Cell;
import map.Board;
import playerrole.cards.EquipmentCardIndex;

import java.io.PrintStream;

public class Piece {
    private Cell currentCell;
    private Board board;
    private Player player;
    private PrintStream printer;
    Piece(Board board, Player player, PrintStream printer) {
        currentCell = null;
        this.printer = printer;
        this.board = board;
        this.player = player;
        putOnStartCell();
    }
    boolean checkMovement(String movements) {
        return board.movementCheck(currentCell, movements);
    }
    private void putOnStartCell() {
        try{
            currentCell = board.getCellList().get(0);
            currentCell.addPiece(this);
        } catch (IndexOutOfBoundsException e) {
            printer.println("Map isn't created.");
            e.printStackTrace();
        }
    }
    private void arrive() {
        currentCell.addPiece(this);
        occurEventByCellType();

    }
    private void move() {
        currentCell.deletePiece(this);

    }
    private void occurEventByCellType() {
        switch (currentCell.getCELL_TYPE()) {
            case HAMMER -> {
                player.getNewEquipmentCard(EquipmentCardIndex.H);
            }
            case SAW -> {
                player.getNewEquipmentCard(EquipmentCardIndex.S);
            }
            case PHILIPS_DRIVER -> {
                player.getNewEquipmentCard(EquipmentCardIndex.P);
            }
            case END -> {
                player.endBoardGame();
            }
        }
    }
    public String toString() {
        return String.format("This Piece is on a cell..Info: %s", currentCell);
    }
}
