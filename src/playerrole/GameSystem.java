package playerrole;

import java.io.PrintStream;
import java.util.*;

public class GameSystem extends Thread {
    public static final int MAX_NUM_OF_PLAYERS = 4;
    public static final int MIN_NUM_OF_PLAYERS = 2;
    private Board board;
    private List<Player> players;
    private HashMap<Player, Integer> billBoard;
    private PrintStream printer;
    private Scanner scanner;

    public GameSystem(Scanner scanner, PrintStream printer) {
        this.printer = printer;
        this.scanner = scanner;
        board = new Board(printer);
        players = null;
        billBoard = null;
    }
    public GameSystem(PrintStream printer) {
        this.printer = printer;
        scanner = new Scanner(System.in);
        board = new Board(printer);
        players = null;
        billBoard = null;
    }
    public void initGame()  {
        board.createMap();
        inputPlayerNumber(scanner);
    }
    public void initGame(Scanner scanner, String mapName)  {
        board.createMap(mapName);
        inputPlayerNumber(scanner);
    }
    public void run() {
        while (!isGameEnd()) {
            for (Player player : players) {
                playOneTurn(player);
                if (!player.isPlaying())
                    terminateAPlayer(player);
            }
        }
        for (Player player : players) {
            if (!billBoard.containsKey(player))
                terminateAPlayer(player);
        }
        billBoard.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .forEach(entry -> printer.printf("Final Score : %d, Player : %s\n", entry.getValue(), entry.getKey()));
    }
    private void inputPlayerNumber(Scanner scanner) {
        int numOfPlayers = 0;
        do {
            try {
                printer.print("Input a number of players..>");
                numOfPlayers = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                printer.println(e.getMessage() + " = Wrong input!");
            }
        } while((numOfPlayers < MIN_NUM_OF_PLAYERS|| numOfPlayers > MAX_NUM_OF_PLAYERS));
        players = new ArrayList<>();
        billBoard = new HashMap<>(numOfPlayers);
        setPlayers(numOfPlayers);
    }
    protected void setPlayers(int numOfPlayers) {
        for (int i = 0; i < numOfPlayers; i++)
            players.add(new Player(board));
    }
    private void playOneTurn(Player player) {
        board.printBoard(players);
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
        }//for..END
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
        printer.printf("Dice roll[MIN:%d, MAX:%d]...", Dice.MIN_NUM_OF_EYE, Dice.MAX_NUM_OF_EYE);
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
        if (player.isPlaying())
            player.endBoardGame();
        int bonusScore = 0;
        if (Player.getTotalNumberOfPlayingPlayers() >= MIN_NUM_OF_PLAYERS-1) {
            switch (billBoard.size()) {
                case 0 -> bonusScore = 7;
                case 1 -> bonusScore = 3;
                case 2 -> bonusScore = 1;
            }
        }
        int totalScore = player.getCurrentScore() + bonusScore;
        billBoard.put(player, totalScore);
    }
    private boolean isGameEnd() {
        return Player.getTotalNumberOfPlayingPlayers() <= 1;
    }
    public Board getBoard() {
        return board;
    }
    public List<Player> getPlayers() {
        return players;
    }
}
