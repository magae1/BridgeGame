import map.Board;
import playerrole.Player;

import java.io.PrintStream;

public class MainTest {
    private static PrintStream printer = new PrintStream(System.out);
    private static final String MAP_1 = "default";
    private static final String MAP_2 = "another";
    public static void main(String[] args) {
        Board board = new Board(printer);
        board.createMap(MAP_1);
        board.printBridgesInfo();
        Player[] players = new Player[2];
        for (int i = 0; i < 2; i++) {
             players[i] = new Player(board, printer);
        }
        for (Player player : players)
            player.playOneTurn();
    }
}
