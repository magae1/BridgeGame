import map.Map;
import playerrole.Dice;

public class MainTest {
    private static final String MAP_1 = "default";
    private static final String MAP_2 = "another";
    public static void main(String[] args) {
        Dice dice = new Dice(1, 6);
        Map map = new Map();
        map.createMap(MAP_1);
        map.printMap();
        map.resetMap();
        map.createMap(MAP_2);
        map.printMap();
        map.resetMap();
    }
}
