package playerrole;

import com.sun.jdi.Value;

import java.io.PrintStream;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

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
    public void initGame()  {
        board.createMap();
        inputPlayerNumber();
    }
    public void initGame(String mapName)  {
        board.createMap(mapName);
        inputPlayerNumber();
    }
    public void run() {
        while (!isGameEnd()) {
            for (Player player : players) {
                playOneTurn(player);
                if (!player.isPlaying())
                    terminateAPlayer(player);
            }
        }
        for (Player player : players)
            terminateAPlayer(player);
        billBoard.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByKey()))
                .forEach(entry -> System.out.printf("Final Score : %d, Player : %s\n", entry.getKey(), entry.getValue()));
    }
    private void inputPlayerNumber() {
        int numOfPlayers = 0;
        do {
            try {
                printer.print("Input a number of players..>");
                numOfPlayers = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                printer.println(e.getMessage() + " = Wrong input!");
            }
        } while((numOfPlayers < 2 || numOfPlayers > 4));
        players = new CopyOnWriteArrayList<>();
        billBoard = new HashMap<>(numOfPlayers);
        setPlayers(numOfPlayers);
    }
    protected void setPlayers(int numOfPlayers) {
        for (int i = 0; i < numOfPlayers; i++)
            players.add(new Player(board));
    }
    private void playOneTurn(Player player) {
        board.printBoard();
        printer.println(player);
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
    protected void terminateAPlayer(Player player) {
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
    private boolean isGameEnd() {
        return Player.getTotalNumberOfPlayingPlayers() == 1;
    }
    public Board getBoard() {
        return board;
    }
    public List<Player> getPlayers() {
        return players;
    }
}
