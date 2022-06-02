import map.Map;
import playerrole.Dice;
import playerrole.Player;

public class MainTest {
    private static final String MAP_1 = "default";
    private static final String MAP_2 = "another";
    public static void main(String[] args) {
        Map map = new Map();
        Player player = new Player();
        map.createMap(MAP_1);
        map.printMap();

        player.playOneTurn();
    }
}
