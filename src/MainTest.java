import map.Map;
import playerrole.Dice;


public class MainTest {
    public static void main(String[] args) {
        Dice dice = new Dice(1, 6);
        /*
        for (int i = 0; i < 100; i++)
            System.out.println(dice.roll());

         */
        Map map = new Map();
        map.createMap();
    }
}
