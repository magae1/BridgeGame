package playerrole;

import playerrole.cells.Cell;
import playerrole.cells.CellTypes;
import playerrole.cards.EquipmentCardIndex;

public class Piece {
    private Cell currentCell;
    private Board board;
    private Player player;
    protected Piece(Board board, Player player) {
        currentCell = null;
        this.board = board;
        this.player = player;
        putOnStartCell();
    }
    protected boolean checkMovement(String movements) {
        return board.movementCheck(currentCell, movements);
    }
    protected void move(String movements) {
        for (int i = 0; i < movements.length(); i++)
            moveOneStep(movements.charAt(i));
        occurEventByCellType();
    }
    protected void crossBridge() {
        currentCell.deletePiece(this);
        currentCell = board.getBridgeMap().get(currentCell);
        player.getNewBridgeCard();
        currentCell.addPiece(this);
    }

    protected boolean isPieceOnBridgeCell() {
        return currentCell.getCELL_TYPE() == CellTypes.BRIDGE_START;
    }
    protected void moveOneStep(char step){
        currentCell.deletePiece(this);
        if (currentCell.isNextDirection(step))
            currentCell = board.getForwardCell(currentCell.getCELL_INDEX());
        else if(currentCell.isPreDirection(step))
            currentCell = board.getBackwardCell(currentCell.getCELL_INDEX());
        currentCell.addPiece(this);
    }
    protected void occurEventByCellType() {
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
    protected void putOnStartCell() {
        try{
            currentCell = board.getCellList().get(0);
            currentCell.addPiece(this);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }
    public Cell getCurrentCell() {
        return currentCell;
    };
    public Player getPlayer() {
        return player;
    }
    public String toString() {
        return String.format("Your Piece is on a..%s", currentCell);
    }
}
