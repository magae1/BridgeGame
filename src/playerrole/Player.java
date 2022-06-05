package playerrole;

import map.Board;
import playerrole.cards.*;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Player {
    static int totalNumberOfPlayers = 0;
    private final int indexOfPlayer;
    private ArrayList<BridgeCard> bridgeCards;
    private ArrayList<EquipmentCard> equipmentCards;
    private Piece piece;
    private Scanner scanner;
    private PrintStream printer;
    private int currentScore = 0;
    public Player(Board board, PrintStream printer) {
        this.printer = printer;
        piece = new Piece(board, this, printer);
        bridgeCards = new ArrayList<>(3);
        equipmentCards = new ArrayList<>(3);
        scanner = new Scanner(System.in);
        ++totalNumberOfPlayers;
        indexOfPlayer = totalNumberOfPlayers;
    }
    public void playOneTurn() {
        printer.println(this);
        for (String input = ""; ;) {
            printer.print("Stay[S] or Roll[R]. if stay, can discard one bridge card...>");
            input = scanner.nextLine();
            input = input.toUpperCase();
            if (input.equals("S")) {
                stayTurn();
                break;
            }
            else if (input.equals("R")) {
                rollTurn();
                break;
            }
            printer.println("Wrong input : " + input);
        }
        printer.println("End your turn.");
    }
    private void stayTurn() {
        deleteBridgeCard();
        printer.printf("Discard one bridge card. You have %d bridge card(s).\n", bridgeCards.size());
    }
    private void rollTurn() {
        printer.printf("Dice roll[MIN:%d, MAX:%d]...", Dice.getMinNumOfEye(), Dice.getMaxNumOfEye());
        int currentEyes = Dice.roll();
        printer.printf("You got \"%d\".\n", currentEyes);

        int movementsCount = currentEyes - bridgeCards.size();
        printer.printf("You can move %d time(s).\n", movementsCount);
        if (movementsCount > 0) {
            String moveWard;
            while((moveWard = inputLoopOnRoll()) != "") {

            }
        }
    }

    private String inputLoopOnRoll() {
        String input;
        printer.print("Decide movemets..>");
        input = scanner.nextLine();
        printer.println("What you just input: " + input);
        return input;
    }
    protected void endBoardGame() {

    }
    void getNewBridgeCard() {
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
    private void deleteBridgeCard() {
        if (!bridgeCards.isEmpty())
            bridgeCards.remove(bridgeCards.size()-1);
    }
    public String toString() {
        return String.format("P%-2d: bridge cards:%2d, equipment cards:%2d\n%s", indexOfPlayer, bridgeCards.size(), equipmentCards.size(), piece);
    }
    public static int getTotalNumberOfPlayers() {
        return totalNumberOfPlayers;
    }
}
