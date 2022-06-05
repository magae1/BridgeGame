import playerrole.Board;
import playerrole.Player;

import java.io.PrintStream;

public class MainTest {
    private static PrintStream printer = new PrintStream(System.out);
    public static void main(String[] args) {
        Board board = new Board(printer);
        board.createMap();
        Player player = new Player(board, printer);
        while(player.isPlaying()) {
            board.printBoard();
            player.playOneTurn();
        }
    }
}
