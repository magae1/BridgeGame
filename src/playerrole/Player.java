package playerrole;

import playerrole.cards.*;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Player {
    private static int totalNumberOfPlayingPlayers = 0;
    private final int indexOfPlayer;
    private ArrayList<BridgeCard> bridgeCards;
    private ArrayList<EquipmentCard> equipmentCards;
    private Piece piece;
    private int currentScore = 0;
    private boolean isPlaying;
    public Player(Board board) {
        piece = new Piece(board, this);
        bridgeCards = new ArrayList<>(3);
        equipmentCards = new ArrayList<>(3);
        isPlaying = true;
        indexOfPlayer = ++totalNumberOfPlayingPlayers;
    }

    protected void endBoardGame() {
        calcCurrentScoreByEquipmentCards();
        isPlaying = false;
        --totalNumberOfPlayingPlayers;
    }
    protected void getNewBridgeCard() {
        bridgeCards.add(new BridgeCard());
    }
    protected void getNewEquipmentCard(EquipmentCardIndex e) {
        equipmentCards.add(new EquipmentCard(e.getName(), e.getScore()));
    }
    private void calcCurrentScoreByEquipmentCards() {
        currentScore = 0;
        for (EquipmentCard e : equipmentCards)
            currentScore += e.getScore();
    }
    protected void deleteBridgeCard() {
        if (!bridgeCards.isEmpty())
            bridgeCards.remove(bridgeCards.size()-1);
    }
    public String toString() {
        return String.format("P%-2d: bridge cards:%2d, equipment cards:%2d\n%s", indexOfPlayer, bridgeCards.size(), equipmentCards.size(), piece);
    }
    protected boolean isPlaying() {
        return isPlaying;
    }
    public ArrayList<BridgeCard> getBridgeCards() {
        return bridgeCards;
    }
    public Piece getPiece() {
        return piece;
    }
    public int getCurrentScore() {
        return currentScore;
    }
    public int getIndexOfPlayer() {
        return indexOfPlayer;
    }
    public static int getTotalNumberOfPlayingPlayers() {
        return totalNumberOfPlayingPlayers;
    }

}
