package playerrole;

import playerrole.cards.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Player {
    static int totalNumberOfPlayers = 0;
    private ArrayList<BridgeCard> bridgeCards;
    private ArrayList<EquipmentCard> equipmentCards;
    private Scanner scanner;
    public Player() {
        bridgeCards = new ArrayList<>(3);
        equipmentCards = new ArrayList<>(3);
        scanner = new Scanner(System.in);
        totalNumberOfPlayers++;
    }
    public void playOneTurn() {
        int currentEyes = Dice.roll();
        System.out.printf("You got \"%d\".\n", currentEyes);

        int movementsCount = currentEyes - bridgeCards.size();
        if (movementsCount <= 0) {
            System.out.print("You can't move.");
        }

        String moveward = "";
        do {
            System.out.printf("You can move %d times. Decide movements..>", movementsCount);
            moveward = scanner.nextLine();
            System.out.println("What you just input: " + moveward);
        } while(true);

    }

    private void getNewBridgeCard() {
        bridgeCards.add(new BridgeCard());
    }
    private void getNewEquipmentCard(EquipmentCardIndex e) {
        equipmentCards.add(new EquipmentCard(e.getName(), e.getScore()));
    }
    private int calcCurrentScoreByEquipmentCards() {
        int currentScore = 0;
        for (EquipmentCard e : equipmentCards) {
            currentScore += e.getScore();
        }
        return currentScore;
    }
    private boolean CanPieceMove(int diceEyes) {
        return (diceEyes - bridgeCards.size() > 0);
    }
    private void deleteBridgeCard() {
        if (!bridgeCards.isEmpty())
            bridgeCards.remove(bridgeCards.size()-1);
    }
    public static int getTotalNumberOfPlayers() {
        return totalNumberOfPlayers;
    }
}
