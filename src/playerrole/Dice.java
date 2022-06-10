package playerrole;

import java.util.Random;

public class Dice {
    public static final int MAX_NUM_OF_EYE = 6;
    public static final int MIN_NUM_OF_EYE = 1;
    public Dice() {
    }
    public static int roll() {
        return new Random().ints(1, MIN_NUM_OF_EYE,MAX_NUM_OF_EYE+1).findFirst().getAsInt();
    }
}
