import map.Map;
import playerrole.Player;

import java.io.PrintStream;

public class MainTest {
    private static PrintStream printer = new PrintStream(System.out);
    private static final String MAP_1 = "default";
    private static final String MAP_2 = "another";
    public static void main(String[] args) {
        Map map = new Map(printer);
        map.createMap(MAP_1);
        map.printBridgesInfo();
        Player player = new Player(map, printer);
        player.playOneTurn();
    }
}
