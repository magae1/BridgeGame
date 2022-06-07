package playerrole;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class GameSystem extends Thread {
    private Board board;
    private List<Player> players;
    private HashMap<Integer, Player> billBoard;
    private PrintStream printer;
    private Scanner scanner;

    public GameSystem(PrintStream printer) {
        this.printer = printer;
        board = new Board(printer);
        scanner = new Scanner(System.in);
        players = null;
        billBoard = null;
    }
    public void startGame() {
        board.createMap();
        int numOfPlayers;
        do {
            printer.print("Input a number of players..>");
            numOfPlayers = scanner.nextInt();
        } while((numOfPlayers < 2 || numOfPlayers > 4));
        players = new ArrayList<>(numOfPlayers);
        billBoard = new HashMap<>(numOfPlayers);
        setPlayers(numOfPlayers);
    }
    public void does() {

        while (!isGameEnd()) {
            for (Player player : players)
                playOneTurn(player);
        }
    }
    protected void setPlayers(int numOfPlayers) {
        for (int i = 0; i < numOfPlayers; i++)
            players.add(new Player(board));
    }
    private void playOneTurn(Player player) {
        printer.println(this);
        for (String input; ;) {
            printer.print("Stay[S] or Move[M]. if stay, can discard one bridge card..>");
            input = scanner.nextLine().toUpperCase();
            if (input.equals("S")) {
                printer.println("You choose Stay.");
                stayTurn(player);
                break;
            }
            else if (input.equals("M")) {
                printer.println("You choose Move.");
                moveTurn(player);
                break;
            }
            printer.println("Wrong input : " + input);
        }
        printer.println("End your turn.");
    }
    private void stayTurn(Player player) {
        player.deleteBridgeCard();
        printer.printf("Discard one bridge card. You have %d bridge card(s).\n", player.getBridgeCards().size());
    }
    private void moveTurn(Player player) {
        if (player.getPiece().isPieceOnBridgeCell()) {
            printer.println("Your piece is on a bridge cell. If you cross a bridge, you get a single bridge card.");
            while(true) {
                printer.print("Do you want to cross the bridge?[Y/N]..>");
                String answer = scanner.nextLine().toUpperCase();
                if (answer.equals("Y")) {
                    printer.println("You choose Yes.");
                    player.getPiece().crossBridge();
                    return;
                }
                else if (answer.equals("N")) {
                    printer.println("You choose No.");
                    break;
                }
            }
        }

        printer.printf("Dice roll[MIN:%d, MAX:%d]...", Dice.getMinNumOfEye(), Dice.getMaxNumOfEye());
        int currentEyes = Dice.roll();
        printer.printf("You got \"%d\".\n", currentEyes);

        int movementsCount = currentEyes - player.getBridgeCards().size();
        printer.printf("You can move %d time(s).\n", movementsCount);
        if (movementsCount > 0) {
            for (String movements; ;) {
                movements = inputLoopOnRoll();
                if ((movements.length() == movementsCount) && (player.getPiece().checkMovement(movements))) {
                    player.getPiece().move(movements);
                    break;
                }
            }
        }
        printer.println(player.getPiece());
    }
    private String inputLoopOnRoll() {
        String input;
        printer.print("Decide movemets..>");
        input = scanner.nextLine().toUpperCase();
        printer.println("What you just input: " + input);
        return input;
    }

    void terminateAPlayer(Player player) {
        int bonusScore = 0;
        switch (billBoard.size()) {
            case 0 -> bonusScore = 7;
            case 1 -> bonusScore = 3;
            case 2 -> bonusScore = 1;
        }
        int totalScore = player.getCurrentScore() + bonusScore;
        billBoard.put(totalScore, player);
        players.remove(player);
    }
    boolean isGameEnd() {
        return players.size() == 1;
    }
}
