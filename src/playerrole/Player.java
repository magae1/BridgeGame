package playerrole;

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
    private boolean isPlaying;
    public Player(Board board, PrintStream printer) {
        this.printer = printer;
        piece = new Piece(board, this);
        bridgeCards = new ArrayList<>(3);
        equipmentCards = new ArrayList<>(3);
        isPlaying = true;
        scanner = new Scanner(System.in);
        ++totalNumberOfPlayers;
        indexOfPlayer = totalNumberOfPlayers;
    }
    public void playOneTurn() {
        printer.println(this);
        for (String input = ""; ;) {
            printer.print("Stay[S] or Move[M]. if stay, can discard one bridge card..>");
            input = scanner.nextLine().toUpperCase();
            if (input.equals("S")) {
                printer.println("You choose Stay.");
                stayTurn();
                break;
            }
            else if (input.equals("M")) {
                printer.println("You choose Move.");
                moveTurn();
                break;
            }
            printer.println("Wrong input : " + input);
        }
        printer.println("End your turn.");
    }
    protected void endBoardGame() {
        calcCurrentScoreByEquipmentCards();
        this.printer = null;
        piece = null;
        bridgeCards = null;
        equipmentCards = null;
        isPlaying = false;
        scanner = null;
        --totalNumberOfPlayers;
    }
    protected void getNewBridgeCard() {
        bridgeCards.add(new BridgeCard());
    }
    protected void getNewEquipmentCard(EquipmentCardIndex e) {
        equipmentCards.add(new EquipmentCard(e.getName(), e.getScore()));
    }
    private void stayTurn() {
        deleteBridgeCard();
        printer.printf("Discard one bridge card. You have %d bridge card(s).\n", bridgeCards.size());
    }
    private void moveTurn() {
        if (piece.isPieceOnBridgeCell()) {
            printer.println("Your piece is on a bridge cell. If you cross a bridge, you get a single bridge card.");
            while(true) {
                printer.print("Do you want to cross the bridge?[Y/N]..>");
                String answer = scanner.nextLine().toUpperCase();
                if (answer.equals("Y")) {
                    printer.println("You choose Yes.");
                    piece.crossBridge();
                    return;
                }
                else if (answer.equals("N"))
                    printer.println("You choose No.");
                    break;
            }
        }

        printer.printf("Dice roll[MIN:%d, MAX:%d]...", Dice.getMinNumOfEye(), Dice.getMaxNumOfEye());
        int currentEyes = Dice.roll();
        printer.printf("You got \"%d\".\n", currentEyes);

        int movementsCount = currentEyes - bridgeCards.size();
        printer.printf("You can move %d time(s).\n", movementsCount);
        if (movementsCount > 0) {
            for (String movements; ;) {
                movements = inputLoopOnRoll();
                if ((movements.length() == movementsCount) && (piece.checkMovement(movements))) {
                    piece.move(movements);
                    break;
                }
            }
        }
        printer.println(piece);
    }
    private String inputLoopOnRoll() {
        String input;
        printer.print("Decide movemets..>");
        input = scanner.nextLine().toUpperCase();
        printer.println("What you just input: " + input);
        return input;
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
    public boolean isPlaying() {
        return isPlaying;
    }
    public int getCurrentScore() {
        return currentScore;
    }
    public int getIndexOfPlayer() {
        return indexOfPlayer;
    }
    public static int getTotalNumberOfPlayers() {
        return totalNumberOfPlayers;
    }

}
